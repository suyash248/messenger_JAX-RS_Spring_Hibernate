package com.soni.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.soni.messenger.model.Comment;
import com.soni.messenger.service.comment.CommentService;

@Path(value="/")
@Produces(value=MediaType.APPLICATION_JSON)
@Consumes(value=MediaType.APPLICATION_JSON)
public class CommentResource {

	@Autowired
	private CommentService commentService;
	
	@GET
	public List<Comment> getComments( @PathParam("messageId") int messageId){
		return commentService.getCommentsByMessage(messageId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") int messageId, Comment comment){
		return commentService.addComment(messageId, comment);
	}
	
	
	@GET
	@Path(value="/{commentId}")
	public Comment getComment(@PathParam("messageId") int messageId, @PathParam(value="commentId") int commentId) {
		return commentService.getComment(messageId, commentId);
	}
	
	
	@PUT
	@Path(value="/{commentId}")
	public Comment updateComment(@PathParam("messageId") int messageId, @PathParam(value="commentId") int commentId, Comment updatedComment) {
		return commentService.updateComment(messageId, commentId, updatedComment);
	}

	@DELETE
	@Path(value="/{commentId}")
	public void removeComment(@PathParam("messageId") int messageId, @PathParam(value="commentId") int commentId) {
		commentService.removeComment(messageId, commentId);
	}
}
