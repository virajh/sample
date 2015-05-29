package reddit.server.controller;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reddit.server.entities.RedditTopic;
import reddit.server.entities.RedditTopicComment;
import reddit.server.entities.RedditTopicList;
import reddit.server.exceptions.TopicCreationFailedException;
import reddit.server.exceptions.TopicNotFoundException;

@RestController
public class RedditTopicController {

	private final HashMap <Integer, RedditTopic> topicMap = new HashMap<Integer, RedditTopic>();
	private final AtomicInteger topicCounter = new AtomicInteger();

	public RedditTopicController() {
		topicMap.put(topicCounter.incrementAndGet(), new RedditTopic("Conquest", "Alexander", "How I got your world."));
		topicMap.put(topicCounter.incrementAndGet(), new RedditTopic("Cricket", "Sachin", "A hundred of hundreds."));
		topicMap.put(topicCounter.incrementAndGet(), new RedditTopic("General Knowledge", "Jon Know", "I know everything!"));
	}


	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public @ResponseBody RedditTopicList getTopics()
	{
		RedditTopicList topicList = new RedditTopicList();

		if(topicMap.size() > 0)
			topicList.addAll(topicMap.values());

		return topicList;
	}

	@RequestMapping(value = "/topics/{topicId}", method = RequestMethod.GET)
	public RedditTopic getTopic(@PathVariable int topicId)
	{
		this.validateRedditTopic(topicId);
		return topicMap.get(topicId);
	}

	@RequestMapping(value = "/topics", method = RequestMethod.POST)
	public HashMap<String, Integer> createTopic(@RequestBody RedditTopic newRedditTopic)
	{
		this.validateRedditTopic(newRedditTopic);

		topicMap.put(topicCounter.incrementAndGet(), newRedditTopic);

		HashMap<String, Integer> result = new HashMap<String, Integer>();

		result.put("topicId", topicCounter.get());

		return result;
	}

	@RequestMapping(value = "/topics/{topicId}", method = RequestMethod.DELETE)
	public String deleteTopic(@PathVariable int topicId)
	{
		this.validateRedditTopic(topicId);

		topicMap.remove(topicId);

		return "Success: deleted";
	}

	@RequestMapping(value = "/topics/{topicId}/comments", method = RequestMethod.POST)
	public String postComment(@PathVariable int topicId, @RequestBody RedditTopicComment topicComment)
	{
		this.validateRedditTopic(topicId);

		RedditTopic redditTopic = topicMap.get(topicId);

		redditTopic.addComment(topicComment);

		return "Success: posted";
	}


	private void validateRedditTopic(int topicId)
	{
		if(!topicMap.containsKey(topicId))
			throw new TopicNotFoundException(topicId);
	}

	private void validateRedditTopic(RedditTopic redditTopic)
	{
		if(!redditTopic.validate())
			throw new TopicCreationFailedException(redditTopic);
	}
}
