package com.virajh.aws.reddit.lambda.response;

import com.virajh.aws.reddit.lambda.request.CommentParameters;
import com.virajh.aws.reddit.model.PostComment;

public class CommentResponse extends RedditResponse {

	private CommentParameters commentParams;

	public CommentResponse() { super(); }

	public CommentResponse(String message, PostComment postComment)
	{
		super(message);
		commentParams = new CommentParameters(postComment);
	}

	public CommentParameters getCommentParams() {
		return commentParams;
	}

	public void setCommentParams(CommentParameters commentParams) {
		this.commentParams = commentParams;
	}
}
