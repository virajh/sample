package com.virajh.aws.reddit.lambda;

public class LambdaConstants {

	public enum RequestType {

		CREATE_POST,
		GET_POST,
		DELETE_POST,
		ADD_COMMENT,
		GET_COMMENT,
		DELETE_COMMENT
	}

	public enum ErrorType {

		INVALID_PARAMETERS,		//400
		ENTITY_NOT_FOUND,		//404
		INTERNAL_ERROR			//500
	}
}
