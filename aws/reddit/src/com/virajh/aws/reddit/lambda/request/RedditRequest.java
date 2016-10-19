package com.virajh.aws.reddit.lambda.request;

import java.util.ArrayList;
import java.util.List;

import com.virajh.aws.reddit.lambda.LambdaConstants.RequestType;

public class RedditRequest {

	private RequestType requestType;
	private RedditPostParameters postParams;
	private CommentParameters commentParams;

	public RedditRequest() {}

	public RedditPostParameters getPostParams() {
		return postParams;
	}

	public void setPostParams(RedditPostParameters postParams) {
		this.postParams = postParams;
	}

	public CommentParameters getCommentParams() {
		return commentParams;
	}

	public void setCommentParams(CommentParameters commentParams) {
		this.commentParams = commentParams;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {

		List<String> attrs = new ArrayList<String>();
		attrs.add("requestType: " + requestType);

		if(postParams != null) {
			attrs.add("postParams: {" + postParams.toString() + "}");
		}
		else {
			attrs.add("postParams: {}");
		}

		if(commentParams != null) {
			attrs.add("commentParams: {" + commentParams.toString() + "}");
		}
		else {
			attrs.add("commentParams: {}");
		}

		return "{" + String.join(", ", attrs) + "}";
	}
}
