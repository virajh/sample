package reddit.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import reddit.server.entities.RedditTopic;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TopicCreationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TopicCreationFailedException(RedditTopic redditTopic) {
		super("cannot create: " + redditTopic);
	}
}
