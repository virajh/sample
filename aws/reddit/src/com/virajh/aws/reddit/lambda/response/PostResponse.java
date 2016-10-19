package com.virajh.aws.reddit.lambda.response;

import com.virajh.aws.reddit.lambda.request.RedditPostParameters;
import com.virajh.aws.reddit.model.RedditPost;

public class PostResponse extends RedditResponse {

	private RedditPostParameters postParams;

	public PostResponse() { super(); }

	public PostResponse(String message, RedditPost redditPost) {

		super(message);
		postParams = new RedditPostParameters(redditPost);
	}

	public RedditPostParameters getPostParams() {
		return postParams;
	}

	public void setPostParams(RedditPostParameters postParams) {
		this.postParams = postParams;
	}
}
