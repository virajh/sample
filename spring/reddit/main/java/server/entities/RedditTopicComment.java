package reddit.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedditTopicComment {

	@JsonProperty("author")
	private String commentAuthorName;

	@JsonProperty("comment")
	private String commentMessage;

	public RedditTopicComment(String commentAuthorName, String commentMessage)
	{
		super();
		this.commentAuthorName = commentAuthorName;
		this.commentMessage = commentMessage;
	}

	public RedditTopicComment() {

	}

	public String getCommentAuthorName() {
		return commentAuthorName;
	}

	public String getCommentMessage() {
		return commentMessage;
	}

	@Override
	public boolean equals(Object otherObject)
	{
		if(!otherObject.getClass().equals(this.getClass()))
			return false;

		RedditTopicComment topicComment = (RedditTopicComment) otherObject;

		if(this.commentAuthorName.equals(topicComment.getCommentAuthorName())
				&& this.commentMessage.equals(topicComment.getCommentMessage()))
			return true;

		else return false;

	}

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = 31 * hash + this.commentAuthorName.hashCode();
		hash = 31 * hash + this.commentMessage.hashCode();

		return hash;
	}
}
