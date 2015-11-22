package com.soni.messenger.service.author;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soni.messenger.model.Author;
import com.soni.messenger.service.AbstractService;

@Repository
@Transactional(propagation=Propagation.REQUIRED, timeout=300)
public class AuthorServiceImpl extends AbstractService implements AuthorService {
	
	public List<Author> getAuthors() {
		String hql = "select author from Author author "
				+ " order by author.createdDate desc";
		Query query = getCurrentSession().createQuery(hql);
		List<Author> authors = query.list();
		return authors;
	}

	public Author getAuthorByName(String authorName) {
		String hql = "select author from Author author "
				+ " where author.authorName=:authorName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("authorName", authorName.toLowerCase());
		Author author = getUniqueResult(query);
		return author;
	}
	
	public Author createAuthor(Author author) {
		Session session = getCurrentSession();
		session.saveOrUpdate(author);
		return author;
	}
	
	public Author updateAuthor(Author updatedAuthor, String authorName) {
		Author author = updateEntity(updatedAuthor, authorName);
		return author;
	}
		
	public Author removeAuthor(String authorName) {
		Session session = getCurrentSession();
		Author author = (Author) session.get(Author.class, authorName);
		session.delete(author);
		return author;
	}
}
