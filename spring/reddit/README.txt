This is a RESTful webservice API built using Java and Spring framework.

It is based on a school project "Reddit-like RESTful Web service" that we had for our Enterprise Application Development class.

The main functionality that the web service offer is the ability to view, create and post comments on topics.

A topic is post by a user which has a title and a description.
A comment only has an author and the message itself. 

The following actions are available on this API:
Get list of all topics - GET - /topics
Get a particular topic - GET - /topics/{topicId}
Create a new topic - POST - /topics
Delete a topic - DELETE - /topics/{topicId}
Post a comment on a topic - POST - /topics/{topicId}/comments
