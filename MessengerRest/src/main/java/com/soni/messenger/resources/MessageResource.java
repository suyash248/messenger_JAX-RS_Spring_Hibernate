package com.soni.messenger.resources;

import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soni.messenger.json.views.Views;
import com.soni.messenger.model.Link;
import com.soni.messenger.model.Message;
import com.soni.messenger.service.message.MessageService;
import com.soni.messenger.util.SpringUtil;

@Path(value="/messages")
@Produces(value={MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Component
public class MessageResource {
	
	@Autowired
	private MessageService messageService;
	
	@GET
	public List<Message> getMessages( @QueryParam("authorName") String authorName,
																				@QueryParam("start") int start,
																				@QueryParam("size") int size) 
	{
		if(StringUtils.isNotBlank(authorName)) 
			return messageService.getMessagesByAuthor(authorName.toLowerCase());
		if(start>0 && size>0) 
			return messageService.getMessagesPaginated(start, size);
		return messageService.getMessages();
	}
	
	@GET
	@Path(value="/{msgId}")
	public Message getMessage(@PathParam(value="msgId") int messageId, @Context UriInfo uriInfo) {
		Message message = messageService.getMessageById(messageId);
		
		/*********** HATEOAS ***********/ 
		setUriForSelf(uriInfo, message);
		setUriForAuthor(uriInfo, message);
		// setUriForComments(uriInfo, message);
		
		return message;
	}

	@POST
	@JsonView(Views.Public.class)
	public Response createMessage(Message message, @Context UriInfo uriInfo) {
		Message newMsg = messageService.createMessage(message);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newMsg.getMessageId())).build();
		Response response = Response
				.created(uri)								// <--- This created(URI uri) method will send status code=201 & Location header = uri
				.entity(newMsg)
				.build();
		return response;
	}
	
	@PUT
	@Path(value="/{msgId}")
	@JsonView(Views.Public.class)
	public Message updateMessage(Message updatedMessage, @PathParam(value="msgId") int messageId) {
		return messageService.updateMessage(updatedMessage, messageId);
	}

	@DELETE
	@Path(value="/{msgId}")
	@JsonView(Views.Public.class)
	public Message removeMessage(@PathParam(value="msgId") int messageId) {
		return messageService.removeMessage(messageId);
	}
	
	/************** Sub resource :- CommentResource ***************/
	@Path(value="/{messageId}/comments")
	public CommentResource getCommentResource() {
		return SpringUtil.getBean(CommentResource.class);
	}
	
	/************** HATEOAS convenient methods **************/
	
	/**
	 * <code>
	 * "link": "http://localhost:8080/MessengerRest/v1/messages/1", <br/>
	 * "rel": "self"
	 * </code>
	 */
	private void setUriForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(String.valueOf(message.getMessageId()))
		.build()
		.toString();
		message.getLinks().add(new Link(url, "self"));
	}
	
	/**
	 * <code>
	 * "link": "http://localhost:8080/MessengerRest/v1/profiles/suyash248",
	 * "rel": "profile"
	 * </code>
	 */
	private void setUriForAuthor(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
		.path(AuthorResource.class)
		.path(message.getAuthor().getAuthorName())
		.build()
		.toString();
		message.getLinks().add(new Link(url, "author"));
	}
	
	/**
	 * <code>
	 * "link": "http://localhost:8080/MessengerRest/v1/messages/1/comments", <br/>
	 * "rel": "comments"
	 * </code>
	 */
	/*private void setUriForComments(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()		// http://localhost:8080/MessengerRest/v1
		.path(MessageResource.class)							// 																					/messages
		.path(MessageResource.class, "getCommentResource") //																			/{messageId}/comments
		.resolveTemplate("messageId", message.getId()) // {messageId} get resolved by actual id.
		.build()
		.toString();
		message.getLinks().add(new Link(url, "comments"));
	}*/
}
