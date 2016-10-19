package com.virajh.aws.reddit.lambda.request;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.StringUtils;
import com.virajh.aws.reddit.model.RedditPost;

public class RedditPostParameters {

	private String postId;
	private String name;
	private String text;
	private String author;

	public RedditPostParameters() {}

	public RedditPostParameters(RedditPost redditPost) {

		postId = redditPost.getId();
		name = redditPost.getName();
		text = redditPost.getText();
		author = redditPost.getAuthor();
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if( StringUtils.isNullOrEmpty(name) ) {
			throw new Exception("name parameter has a null value");
		}

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

		attrs.add("name: " + name);
		attrs.add("text: " + text);
		attrs.add("author: " + author);

		return String.join(", ", attrs);
	}
}
