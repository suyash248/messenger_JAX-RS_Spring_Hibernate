package com.soni.messenger.service.comment;

import java.util.List;

import com.soni.messenger.model.Comment;

public interface CommentService {
	public List<Comment> getCommentsByMessage(int messageId);
	public Comment getComment(int messageId, int commentId);
	public Comment addComment(int messageId, Comment comment);
	public Comment updateComment(int messageId, int commentId, Comment updatedComment);
	public void removeComment(int messageId, int commentId);
}