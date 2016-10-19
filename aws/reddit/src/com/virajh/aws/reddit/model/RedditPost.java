package com.virajh.aws.reddit.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = RedditConstants.RedditPostTable)
public class RedditPost {

	private String postId;
	private String name;
	private String text;
	private String author;
	private List<PostComment> comments;

	public RedditPost() {
		this.comments = new LinkedList<PostComment>();
	}

	@DynamoDBHashKey(attributeName = RedditConstants.RedditPostId)
	public String getId() {
		return postId;
	}

	public void setId(String postId) {
		this.postId = postId;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.RedditPostName)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.RedditPostText)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@DynamoDBAttribute(attributeName = RedditConstants.RedditPostAuthor)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@DynamoDBIgnore
	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	public void addComment(PostComment comment) {
		comments.add(comment);
	}

	@Override
	public String toString()
	{
		List<String> commList = new ArrayList<String>();

		for(PostComment comm: comments) {
			commList.add(comm.toString());
		}

		List<String> attrs = new ArrayList<String>();

		attrs.add("postId: " + postId);
		attrs.add("name: " + name);
		attrs.add("text: " + text);
		attrs.add("author: " + author);
		attrs.add("comments: [" + String.join(", ", commList) + "]");

		return "{" + String.join(", ", attrs) + "}";
	}

	@Override
	public boolean equals(Object obj) {

		if(obj == null) { return false; }
		if(this == obj) { return true; }

		if(getClass() != obj.getClass()) {
			return false;
		}

		RedditPost post = (RedditPost) obj;

		return Objects.equals(		postId, post.getId())
				&& Objects.equals(	name, 	post.getName())
				&& Objects.equals(	author, post.getAuthor())
				&& Objects.equals(	text, 	post.getText());
	}
}
