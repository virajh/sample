package com.virajh.aws.reddit.lambda;

import com.virajh.aws.reddit.lambda.LambdaConstants.ErrorType;
import com.virajh.aws.reddit.lambda.response.CommentResponse;
import com.virajh.aws.reddit.lambda.response.PostResponse;
import com.virajh.aws.reddit.lambda.response.RedditResponse;
import com.virajh.aws.reddit.model.PostComment;
import com.virajh.aws.reddit.model.RedditPost;

public class LambdaRequestProcessor {

	public void generateErrorResponse(ErrorType errorType, String message) {
		throw new RuntimeException("[" + errorType + "] " + message);
	}

	public void generateErrorResponse(ErrorType errorType, String message, Exception e) {
		generateErrorResponse(errorType, message + ": " + e.getMessage());
	}

	public RedditResponse generateSuccessResponse(String message) {
		return new RedditResponse(message);
	}

	public RedditResponse generateSuccessResponse(String message, RedditPost redditPost) {
		return new PostResponse(message, redditPost);
	}

	public RedditResponse generateSuccessResponse(String message, PostComment postComment) {
		return new CommentResponse(message, postComment);
	}

}
