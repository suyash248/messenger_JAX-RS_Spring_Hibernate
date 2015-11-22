package com.soni.messenger.service.author;

import java.util.List;

import com.soni.messenger.model.Author;

public interface AuthorService {
	public Author getAuthorByName(String authorName);
	public List<Author> getAuthors();
	public Author createAuthor(Author author);
	public Author updateAuthor(Author updatedAuthor, String authorName);
	public Author removeAuthor(String authorName);
}
