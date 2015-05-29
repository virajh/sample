package reddit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import reddit.client.RedditClient;
import reddit.server.entities.RedditTopic;
import reddit.server.entities.RedditTopicComment;
import reddit.server.entities.RedditTopicList;

public class RedditApplicationTests {

	private final String TOPIC_URL = "http://localhost:8080/topics/";

	private final RedditClient redditClient = new RedditClient(TOPIC_URL);
	private final List<Integer> testTopics = new LinkedList<Integer>();


	@After
	public void cleanup() {
		for(int topicId: testTopics)
			if(redditClient.getTopic(topicId) != null)
				redditClient.deleteTopic(topicId);
	}


	@Test
	public void testCreateTopic() {

		RedditTopic newTopic = new RedditTopic("Create Topic", "testCreateTopic", "Topic created for testing create functionality");

		int newTopicId = redditClient.createTopic(newTopic);
		assertTrue(newTopicId > -1);
		testTopics.add(newTopicId);

		RedditTopic createdTopic = redditClient.getTopic(newTopicId);
		assertEquals(newTopic, createdTopic);

		RedditTopicList topicList = redditClient.getAllTopics();
		assertTrue(topicList.contains(newTopic));
	}

	@Test
	public void testDeleteTopic() {

		RedditTopic redditTopic = new RedditTopic("Delete Topic", "testDeleteTopic", "Topic created for testing delete functionality");

		int topicId = redditClient.createTopic(redditTopic);

		boolean deleted = redditClient.deleteTopic(topicId);
		assertTrue(deleted);

		redditTopic = redditClient.getTopic(topicId);
		assertEquals(null, redditTopic);
	}

	@Test
	public void testPostCommentOnTopic() {

		RedditTopic redditTopic = new RedditTopic("Post Comment", "testPostCommentOnTopic", "Topic created for testing posting of comments");

		int topicId = redditClient.createTopic(redditTopic);
		testTopics.add(topicId);

		RedditTopicComment commentOne = new RedditTopicComment("UserOne", "First Comment");
		RedditTopicComment commentTwo = new RedditTopicComment("UserTwo", "Second Comment");

		redditClient.postComment(topicId, commentOne);
		redditClient.postComment(topicId, commentTwo);

		redditTopic = redditClient.getTopic(topicId);
		assertEquals(redditTopic.getTopicComments(), Arrays.asList(commentOne, commentTwo));
	}

	@Test
	public void testGetAllTopics() {

		RedditTopic topicOne = new RedditTopic("First Topic", "testGetAllTopics", "First topic for testing get all topics");
		RedditTopic topicTwo = new RedditTopic("Second Topic", "testGetAllTopics", "Second topic for testing get all topics");
		RedditTopic topicThree = new RedditTopic("Third Topic", "testGetAllTopics", "Third topic for testing get all topics");

		RedditTopicList createdTopicList = new RedditTopicList();
		createdTopicList.addAll(Arrays.asList(topicOne, topicTwo, topicThree));

		testTopics.add(redditClient.createTopic(topicOne));
		testTopics.add(redditClient.createTopic(topicTwo));
		testTopics.add(redditClient.createTopic(topicThree));

		RedditTopicList currentTopicList = redditClient.getAllTopics();
		assertTrue(currentTopicList.containsAll(createdTopicList));
	}

	@Test
	public void testGetNonExistentTopic() {

		RedditTopicList topicList = redditClient.getAllTopics();

		int noTopicId = topicList.size();
		noTopicId = noTopicId * 10;

		RedditTopic noTopic = redditClient.getTopic(noTopicId);
		assertEquals(null, noTopic);
	}

	@Test
	public void testFailToCreateTopic() {

		RedditTopic failTopic = new RedditTopic("Fail topic", "", "Topic for testing validation before saving");

		int failTopicId = redditClient.createTopic(failTopic);
		assertEquals(-1, failTopicId);

		failTopic = redditClient.getTopic(failTopicId);
		assertEquals(null, failTopic);
	}

	@Test
	public void testDeleteNonExistentTopic() {

		RedditTopicList topicList = redditClient.getAllTopics();

		int noTopicId = topicList.size();
		noTopicId = noTopicId * 10;

		boolean deleted = redditClient.deleteTopic(noTopicId);
		assertFalse(deleted);
	}

	@Test
	public void testPostCommentOnNonExistentTopic() {

		RedditTopicList topicList = redditClient.getAllTopics();

		int noTopicId = topicList.size();
		noTopicId = noTopicId * 10;

		RedditTopicComment commentOne = new RedditTopicComment("UserOne", "First Comment");

		boolean posted = redditClient.postComment(noTopicId, commentOne);
		assertFalse(posted);
	}
}
