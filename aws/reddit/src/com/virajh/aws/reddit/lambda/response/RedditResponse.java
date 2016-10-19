package com.virajh.aws.reddit.lambda.response;

public class RedditResponse {

	private String message;

	public RedditResponse() {}

	public RedditResponse(String message)
	{
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
