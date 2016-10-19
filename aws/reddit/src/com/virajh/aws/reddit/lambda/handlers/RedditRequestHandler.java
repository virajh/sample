package com.virajh.aws.reddit.lambda.handlers;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.virajh.aws.reddit.lambda.LambdaConstants.RequestType;
import com.virajh.aws.reddit.lambda.LambdaConstants.ErrorType;
import com.virajh.aws.reddit.lambda.LambdaRequestProcessor;
import com.virajh.aws.reddit.lambda.request.CommentParameters;
import com.virajh.aws.reddit.lambda.request.RedditPostParameters;
import com.virajh.aws.reddit.lambda.request.RedditRequest;
import com.virajh.aws.reddit.lambda.response.RedditResponse;
import com.virajh.aws.reddit.model.PostComment;
import com.virajh.aws.reddit.model.RedditConstants;
import com.virajh.aws.reddit.model.RedditPost;
import com.virajh.aws.reddit.util.RedditPostCommentUtil;
import com.virajh.aws.util.AWSClientUtil;

public class RedditRequestHandler implements RequestHandler<RedditRequest, RedditResponse> {

	private final LambdaRequestProcessor requestProcessor = new LambdaRequestProcessor();
	private RedditPostCommentUtil rpcUtil;
	private RedditResponse response;

	public RedditRequestHandler() {
		this(AWSClientUtil.getInstance().createDynamoDBClient());
	}

	public RedditRequestHandler(AmazonDynamoDB dynamoDBClient) {
		rpcUtil = new RedditPostCommentUtil(dynamoDBClient);
	}

	@Override
	public RedditResponse handleRequest(RedditRequest request, Context context) {

		LambdaLogger logger = context.getLogger();
		logger.log("request " + request);

		RequestType requestType = request.getRequestType();

		switch(requestType) {

		case CREATE_POST:
			response = createPost(request);
			break;

		case GET_POST:
			response = getPost(request);
			break;

		case DELETE_POST:
			response = deletePost(request);
			break;

		case ADD_COMMENT:
			response = addComment(request);
			break;

		case GET_COMMENT:
			response = getComment(request);
			break;

		case DELETE_COMMENT:
			response = deleteComment(request);
			break;

		default:
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, "Unrecognized request type");
			break;
		}

		return response;
	}

	private RedditResponse createPost(RedditRequest request) {

		RedditPostParameters postParams = request.getPostParams();
		try {
			postParams.validate();
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, "Parameter validation failed", e);
		}

		String uuid = UUID.randomUUID().toString();
		RedditPost newPost = new RedditPost();

		newPost.setId(uuid);
		newPost.setAuthor( postParams.getAuthor() );
		newPost.setName( postParams.getName() );
		newPost.setText( postParams.getText() );

		try {
			rpcUtil.createRedditPost(newPost);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error creating RedditPost object", e);
		}

		return requestProcessor.generateSuccessResponse("RedditPost successfully created", newPost);
	}

	private RedditResponse getPost(RedditRequest request) {

		String postId = request.getPostParams().getPostId();

		if( StringUtils.isNullOrEmpty(postId) ) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, RedditConstants.RedditPostId + " parameter has a null value");
		}

		boolean postExists = false;

		try {
			postExists = rpcUtil.checkRedditPostExists(postId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error accessing RedditPost object", e);
		}

		if( !postExists ) {
			requestProcessor.generateErrorResponse(ErrorType.ENTITY_NOT_FOUND, "RedditPost not found with Id " + postId);
		}

		RedditPost redditPost = null;
		try {
			redditPost = rpcUtil.getRedditPost(postId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error accessing RedditPost object", e);
		}

		return requestProcessor.generateSuccessResponse("RedditPost successfully retrieved", redditPost);
	}

	private RedditResponse deletePost(RedditRequest request) {

		String postId = request.getPostParams().getPostId();

		if( StringUtils.isNullOrEmpty(postId) ) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, RedditConstants.RedditPostId + " parameter has a null value");
		}

		boolean delete_flag = false;

		try {
			delete_flag = rpcUtil.deleteRedditPost(postId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error deleting RedditPost object", e);
		}

		if ( !delete_flag ) {
			requestProcessor.generateErrorResponse(ErrorType.ENTITY_NOT_FOUND, "RedditPost not found with Id " + postId);
		}

		return requestProcessor.generateSuccessResponse("RedditPost successfully deleted");
	}

	private RedditResponse addComment(RedditRequest request) {

		String postId = request.getCommentParams().getPostId();
		CommentParameters commentParams = request.getCommentParams();

		if( StringUtils.isNullOrEmpty(postId) ) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, RedditConstants.RedditPostId + " parameter has a null value");
		}

		try {
			commentParams.validate();
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, "Parameter validation failed", e);
		}

		boolean postExists = false;

		try {
			postExists = rpcUtil.checkRedditPostExists(postId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error accessing RedditPost object", e);
		}

		if( !postExists ) {
			requestProcessor.generateErrorResponse(ErrorType.ENTITY_NOT_FOUND, "RedditPost not found with Id " + postId);
		}

		RedditPost redditPost = null;
		try {
			redditPost = rpcUtil.getRedditPost(postId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error accessing RedditPost object", e);
		}

		String uuid = UUID.randomUUID().toString();
		PostComment newComment = new PostComment();

		newComment.setCommentId(uuid);
		newComment.setPostId(postId);
		newComment.setAuthor( commentParams.getAuthor() );
		newComment.setText( commentParams.getText() );

		redditPost.addComment(newComment);

		try {
			rpcUtil.updateRedditPost(redditPost);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error updating RedditPost object", e);
		}

		return requestProcessor.generateSuccessResponse("PostComment successfully added", newComment);
	}

	private RedditResponse getComment(RedditRequest request) {

		String commentId = request.getCommentParams().getCommentId();

		if( StringUtils.isNullOrEmpty(commentId) ) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, RedditConstants.PostCommentId + " parameter has a null value");
		}

		PostComment postComment = null;
		try {
			postComment = rpcUtil.getPostComment(commentId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error accessing PostComment object", e);
		}

		if( postComment == null ) {
			requestProcessor.generateErrorResponse(ErrorType.ENTITY_NOT_FOUND, "PostComment not found with Id " + commentId);
		}

		return requestProcessor.generateSuccessResponse("PostComment successfully retrieved", postComment);
	}

	private RedditResponse deleteComment(RedditRequest request) {

		String commentId = request.getCommentParams().getCommentId();

		if( StringUtils.isNullOrEmpty(commentId) ) {
			requestProcessor.generateErrorResponse(ErrorType.INVALID_PARAMETERS, RedditConstants.PostCommentId + " parameter has a null value");
		}

		boolean delete_flag = false;

		try {
			delete_flag = rpcUtil.deletePostComment(commentId);
		}
		catch (Exception e) {
			requestProcessor.generateErrorResponse(ErrorType.INTERNAL_ERROR, "Error deleting PostComment object", e);
		}

		if ( !delete_flag ) {
			requestProcessor.generateErrorResponse(ErrorType.ENTITY_NOT_FOUND, "PostComment not found with Id " + commentId);
		}

		return requestProcessor.generateSuccessResponse("PostComment successfully deleted");
	}

}
