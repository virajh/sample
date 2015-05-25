package reddit;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RedditTopic {

	@JsonProperty("title")
	private String topicName;

	@JsonProperty("author")
	private String topicAuthorName;

	@JsonProperty("description")
	private String topicDescription;

	@JsonProperty("comments")
	private List<RedditTopicComment> topicComments;


	public RedditTopic(String topicName, String topicAuthorName, String topicDescription)
	{
		super();
		this.topicName = topicName;
		this.topicAuthorName = topicAuthorName;
		this.topicDescription = topicDescription;
		this.topicComments = new LinkedList<RedditTopicComment>();
	}

	public RedditTopic() {
		this.topicComments = new LinkedList<RedditTopicComment>();
	}

	public String getAuthorName() {
		return topicAuthorName;
	}

	public String getTopicName() {
		return topicName;
	}

	public String getTopicDescription() {
		return topicDescription;
	}

	public List<RedditTopicComment> getTopicComments() {
		return topicComments;
	}

	public void addComment(RedditTopicComment topicComment)	{
		topicComments.add(topicComment);
	}

	public boolean validate()
	{
		if(topicName == null || topicAuthorName == null || topicDescription == null)
			return false;

		else if (topicName.length() < 3 || topicAuthorName.length() < 3 || topicDescription.length() < 3)
			return false;

		else return true;
	}

	@Override
	public String toString() {
		return topicName + ": " + topicDescription + " by " + topicAuthorName;
	}

	@Override
	public boolean equals(Object otherObject)
	{
		if(!otherObject.getClass().equals(this.getClass()))
			return false;

		RedditTopic redditTopic = (RedditTopic) otherObject;

		if(this.topicName.equals(redditTopic.getTopicName())
				&& this.topicAuthorName.equals(redditTopic.getAuthorName())
				&& this.topicDescription.equals(redditTopic.getTopicDescription()))
			return true;

		else return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 17;
		hash = hash * 31 + this.topicName.hashCode();
		hash = hash * 31 + this.topicAuthorName.hashCode();
		hash = hash * 31 + this.topicDescription.hashCode();

		return hash;
	}
}
