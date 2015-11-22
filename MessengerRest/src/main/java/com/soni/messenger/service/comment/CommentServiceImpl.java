package com.soni.messenger.service.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soni.messenger.model.Comment;
import com.soni.messenger.model.Message;
import com.soni.messenger.service.AbstractService;

@Repository
@Transactional(propagation=Propagation.REQUIRED, timeout=300)
public class CommentServiceImpl extends AbstractService implements CommentService {
	
	/*public List<Comment> getComments(long messageId) {
		return new ArrayList<>(messages.get(messageId).getComments().values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		return message.getComments().get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		Map<Long, Comment>commentsMap = message.getComments();
		comment.setId(commentsMap.size()+1);
		commentsMap.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		Map<Long, Comment>commentsMap = message.getComments();
		commentsMap.put(comment.getId(), comment);
		return comment;
	}
	
	public void removeComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		Map<Long, Comment>commentsMap = message.getComments();
		commentsMap.remove(commentId);
	}*/
}
