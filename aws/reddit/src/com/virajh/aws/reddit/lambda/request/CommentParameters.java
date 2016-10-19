package com.virajh.aws.reddit.lambda.request;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.StringUtils;
import com.virajh.aws.reddit.model.PostComment;

public class CommentParameters {

	private String postId;
	private String commentId;
	private String text;
	private String author;

	public CommentParameters() {}

	public CommentParameters(PostComment postComment) {

		postId = postComment.getPostId();
		commentId = postComment.getCommentId();
		text = postComment.getText();
		author = postComment.getAuthor();
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void validate() throws Exception {

		if( StringUtils.isNullOrEmpty(text) ) {
			throw new Exception("text parameter has a null value");
		}

		if( StringUtils.isNullOrEmpty(author) ) {
			throw new Exception("author parameter has a null value");
		}
	}

	@Override
	public String toString() {

		List<String> attrs = new ArrayList<String>();

		if(postId != null) {
			attrs.add("postId: " + postId);
		}

		if(commentId != null) {
			attrs.add("commentId: " + commentId);
		}

		attrs.add("text: " + text);
		attrs.add("author: " + author);

		return String.join(", ", attrs);
	}
}
