package reddit.client;

import java.util.HashMap;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import reddit.server.entities.RedditTopic;
import reddit.server.entities.RedditTopicComment;
import reddit.server.entities.RedditTopicList;


public class RedditClient {

	private final String TOPIC_URL;
	private final RestTemplate restTemplate;

	public RedditClient(String url) {
		TOPIC_URL = url;
		restTemplate = new RestTemplate();
	}

	public RedditTopicList getAllTopics() {

		try {
			RedditTopicList topicList = restTemplate.getForObject(TOPIC_URL, RedditTopicList.class);
			return topicList;
		}
		catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public RedditTopic getTopic(int topicId) {

		String url = TOPIC_URL + "/" + topicId;

		try {
			RedditTopic redditTopic = restTemplate.getForObject(url, RedditTopic.class);
			return redditTopic;
		}
		catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public int createTopic(RedditTopic newTopic) {

		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> topicId = restTemplate.postForObject(TOPIC_URL, newTopic, HashMap.class);

			if(topicId.containsKey("topicId"))
				return topicId.get("topicId");

			else return -1;
		}
		catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			return -1;
		}
	}

	public boolean deleteTopic(int topicId) {

		String url = TOPIC_URL + "/" + topicId;
		try {
			restTemplate.delete(url);
			return true;
		}
		catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public boolean postComment(int topicId, RedditTopicComment comment) {

		String url = TOPIC_URL + "/" + topicId + "/comments";

		try {
			restTemplate.postForEntity(url, comment, String.class);
			return true;
		}
		catch (HttpClientErrorException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
}
