package com.soni.messenger.service.message;

import java.util.List;

import com.soni.messenger.model.Message;

public interface MessageService {
	public List<Message> getMessages();
	public Message getMessageById(int messageId);
	public Message createMessage(Message message);
	public Message updateMessage(Message updatedMessage, int messageId);
	public Message removeMessage(int messageId) ;
	
	public List<Message> getMessagesByAuthor(String authorName);
	public List<Message> getMessagesPaginated(int start, int size);
}
