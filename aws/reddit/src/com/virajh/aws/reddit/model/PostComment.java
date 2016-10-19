package com.virajh.aws.reddit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = RedditConstants.PostCommentTable)
public class PostComment {

	private String postId;
	private String commentId;
	private String text;
	private String author;

	@DynamoDBHashKey(attributeName = RedditConstants.PostCommentId)
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.RedditPostId)
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.PostCommentText)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.PostCommentAuthor)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		List<String> attrs = new ArrayList<String>();

		attrs.add("commentId: " + commentId);
		attrs.add("postId: " +postId);
		attrs.add("text: " + text);
		attrs.add("author: " + author);

		return "{" + String.join(", ", attrs) + "}";
	}

	@Override
	public boolean equals(Object obj) {

		if(obj == null) { return false; }
		if(this == obj) { return true; }

		if(getClass() != obj.getClass()) {
			return false;
		}

		PostComment comment = (PostComment) obj;

		return Objects.equals(		postId, 	comment.getPostId())
				&& Objects.equals(	commentId, 	comment.getCommentId())
				&& Objects.equals(	text, 		comment.getText())
				&& Objects.equals(	author, 	comment.getAuthor());
	}
}
