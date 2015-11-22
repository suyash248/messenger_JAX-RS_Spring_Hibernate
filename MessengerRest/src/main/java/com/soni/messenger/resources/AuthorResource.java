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
import org.springframework.stereotype.Component;

import com.soni.messenger.model.Author;
import com.soni.messenger.service.author.AuthorService;

@Path(value="/authors")
@Component
@Produces(value=MediaType.APPLICATION_JSON)
@Consumes(value=MediaType.APPLICATION_JSON)
public class AuthorResource {
	
	@Autowired
	private AuthorService authorService;
	
	@GET
	public List<Author> getAuthors() {
		return authorService.getAuthors();
	}
	
	@GET
	@Path(value="/{authorName}")
	public Author getAuthor(@PathParam(value="authorName") String authorName) {
		return authorService.getAuthorByName(authorName);
	}
	
	@POST
	public Author createAuthor(Author author) {
		return authorService.createAuthor(author);
	}
	
	@PUT
	@Path(value="/{authorName}")
	public Author updateAuthor(Author author, @PathParam(value="authorName") String authorName) {
		return authorService.updateAuthor(author, authorName);
	}

	@DELETE
	@Path(value="/{authorName}")
	public Author removeAuthor(@PathParam(value="authorName") String authorName) {
		return authorService.removeAuthor(authorName);
	}
}
