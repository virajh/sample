package com.virajh.aws.reddit.util;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.virajh.aws.reddit.model.PostComment;
import com.virajh.aws.reddit.model.RedditConstants;
import com.virajh.aws.reddit.model.RedditPost;

public class RedditPostCommentUtil {

	private DynamoDB dbManager;
	private DynamoDBMapper dbMapper;

	public RedditPostCommentUtil(AmazonDynamoDB dynamoDB) {
		dbManager = new DynamoDB(dynamoDB);
		dbMapper = new DynamoDBMapper(dynamoDB);
	}

	public void createRedditPost(RedditPost redditPost) {
		dbMapper.save(redditPost);
	}

	public RedditPost getRedditPost(String postId) {

		RedditPost queryPost = new RedditPost();
		queryPost.setId(postId);

		DynamoDBQueryExpression<RedditPost> queryExpression = new DynamoDBQueryExpression<RedditPost>().withHashKeyValues(queryPost);

		List<RedditPost> itemList;
		RedditPost redditPost;

		itemList = dbMapper.query(RedditPost.class, queryExpression);

		if(itemList.size() == 0) {
			return null;
		}
		else {
			redditPost = itemList.get(0);
		}

		List<PostComment> postComments = getPostComments(postId);

		redditPost.setComments(postComments);

		return redditPost;
	}

	public void updateRedditPost(RedditPost redditPost) {

		List<PostComment> postComments = redditPost.getComments();

		for(PostComment comment: postComments) {
			dbMapper.save(comment);
		}

		dbMapper.save(redditPost);
	}

	public boolean deleteRedditPost(String postId) {

		RedditPost deletePost = getRedditPost(postId);

		if( deletePost == null ) {
			return false;
		}

		List<PostComment> postComments = deletePost.getComments();

		for(PostComment comment: postComments) {
			dbMapper.delete(comment);
		}

		dbMapper.delete(deletePost);

		return true;
	}

	public boolean checkRedditPostExists(String postId) {

		if( getRedditPost(postId) != null ) {
			return true;
		}

		else return false;
	}

	public List<PostComment> getPostComments(String postId) {

		Table table = getTableIfExists(RedditConstants.PostCommentTable);
		Index index = table.getIndex(RedditConstants.PostCommentGSI);

		QuerySpec querySpec = new QuerySpec();
		querySpec.withKeyConditionExpression(RedditConstants.RedditPostId + " = :v_postId").withValueMap(new ValueMap().withString(":v_postId", postId));

		ItemCollection<QueryOutcome> items = index.query(querySpec);

		IteratorSupport <Item, QueryOutcome> iterator = items.iterator();

		List<PostComment> commentList = new ArrayList<PostComment>();

		while(iterator.hasNext()) {
			commentList.add( parseCommentFromItem( iterator.next() ) );
		}

		return commentList;
	}

	public PostComment getPostComment(String commentId) {

		PostComment queryComment = new PostComment();
		queryComment.setCommentId(commentId);

		DynamoDBQueryExpression<PostComment> queryExpression = new DynamoDBQueryExpression<PostComment>().withHashKeyValues(queryComment);

		List<PostComment> itemList;

		itemList = dbMapper.query(PostComment.class, queryExpression);

		if(itemList.size() == 0) {
			return null;
		}
		else {
			return itemList.get(0);
		}
	}

	public boolean deletePostComment(String commentId) {

		PostComment deleteComment = getPostComment(commentId);

		if( deleteComment == null) {
			return false;
		}

		dbMapper.delete(deleteComment);

		return true;
	}

	private PostComment parseCommentFromItem(Item item) {

		PostComment postComment = new PostComment();

		postComment.setAuthor( 		(String) 	item.get( RedditConstants.PostCommentAuthor ) 	);
		postComment.setCommentId( 	(String) 	item.get( RedditConstants.PostCommentId ) 		);
		postComment.setText( 		(String) 	item.get( RedditConstants.PostCommentText ) 	);
		postComment.setPostId( 	(String) 	item.get( RedditConstants.RedditPostId ) 		);

		return postComment;
	}

	private Table getTableIfExists(String tableName) {

		Table table = dbManager.getTable(tableName);
		table.describe();
		return table;
	}

}
