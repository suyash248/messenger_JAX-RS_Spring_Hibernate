package com.soni.messenger.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soni.messenger.model.Comment;
import com.soni.messenger.model.Message;
import com.soni.messenger.service.AbstractService;

@Repository
@Transactional(propagation=Propagation.REQUIRED, timeout=300)
public class CommentServiceImpl extends AbstractService implements CommentService {
	
	public List<Comment> getCommentsByMessage(int messageId) {
		List<Comment> comments = new ArrayList<>();
		String hql = "select c from Comment c "
				+ " left join c.message msg "
				+ " where msg.messageId=:messageId "
				+ " order by c.createdDate desc";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("messageId", messageId);
		comments = getAll(query);
		return comments;
	}
	
	public Comment getComment(int messageId, int commentId) {
		String hql = "select c from Comment c "
				+ " left join c.message msg "
				+ " where c.commentId=:commentId "
				+ " and msg.messageId=:messageId ";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("messageId", messageId);
		query.setParameter("commentId", commentId);
		Comment comment = getUniqueResult(query);
		return comment;
	}
	
	public Comment addComment(int messageId, Comment comment) {
		Message message = getEntity(Message.class, messageId);
		comment.setMessage(message);
		Session session = getCurrentSession();
		session.persist(comment);
		return comment;
	}
	
	public Comment updateComment(int messageId, int commentId, Comment updatedComment) {
		Comment staleComment = getComment(messageId, commentId);
		Comment comment = updateEntity(updatedComment, staleComment);
		return comment;
	}
	
	public void removeComment(int messageId, int commentId) {
		Comment comment = getComment(messageId, commentId);
		Session session = getCurrentSession();
		session.delete(comment);
	}
}
