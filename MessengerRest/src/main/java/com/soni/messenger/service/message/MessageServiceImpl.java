package com.soni.messenger.service.message;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soni.messenger.model.Message;
import com.soni.messenger.service.AbstractService;

@Repository
@Transactional(propagation=Propagation.REQUIRED, timeout=300)
public class MessageServiceImpl extends AbstractService implements MessageService {
		
	public List<Message> getMessages() {
		String hql = "select msg from Message msg "
				+ " left join fetch msg.author auth "
				+ " left join fetch msg.comments "
				+ " order by msg.createdDate desc ";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		List<Message> messages = getAll(query);
		return messages;
	}
	
	public Message getMessageById(int messageId) {
		String hql = "select msg from Message msg "
				+ " left join fetch msg.author auth "
				+ " left join fetch msg.comments "
				+ " where msg.messageId=:messageId "
				+ " order by msg.createdDate desc ";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("messageId", messageId);
		Message msg = getUniqueResult(query);
		return msg;
	}
	
	public Message createMessage(Message message) {
		Session session = getCurrentSession();
		session.saveOrUpdate(message);
		return message;
	}
	
	public Message updateMessage(Message updatedMessage, int messageId) {
		Message message = updateEntityByPK(updatedMessage, messageId);
		return message;
	}
	
	public Message removeMessage(int messageId) {
		Session session = getCurrentSession();
		Message message = (Message) session.get(Message.class, messageId);
		session.delete(message);
		return message;
	}
	
	/******************************************************************************/
	
	/**
	 *  For filtering.
	 */
	public List<Message> getMessagesByAuthor(String authorName) {
		String hql = "select msg from Message msg "
				+ " left join fetch msg.author auth "
				+ " left join fetch msg.comments "
				+ " where auth.authorName=:authorName "
				+ " order by msg.createdDate desc ";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("authorName", authorName.toLowerCase());
		List<Message> msgs = getAll(query);
		return msgs;
	}
	
	/**
	 *  For pagination.
	 */
	public List<Message> getMessagesPaginated(int start, int size) {
		String hql = "select msg from Message msg "
				+ " left join fetch msg.author auth "
				+ " left join fetch msg.comments "
				+ " order by msg.createdDate desc ";
		Session session = getCurrentSession();
		Query query = session.createQuery(hql)
				.setFirstResult(start-1)
				.setMaxResults(size-1);
		List<Message> msgs = getAll(query);
		return msgs;
	}
}
