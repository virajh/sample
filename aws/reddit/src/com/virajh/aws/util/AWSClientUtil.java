package com.virajh.aws.util;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class AWSClientUtil {

	private static final AWSClientUtil instance = new AWSClientUtil();

	private volatile AmazonDynamoDB dynamoDBClient = null;

	public static AWSClientUtil getInstance() {
		return instance;
	}

	public AmazonDynamoDB createDynamoDBClient() {

		if( dynamoDBClient == null) {
			synchronized (this) {
				if(dynamoDBClient == null) {
					dynamoDBClient = initializeClient(new AmazonDynamoDBClient());
				}
			}
		}

		return dynamoDBClient;
	}

	private AmazonDynamoDB initializeClient(AmazonDynamoDBClient amazonDynamoDBClient) {

		setRegion(amazonDynamoDBClient);
		return amazonDynamoDBClient;
	}

	private void setRegion(AmazonDynamoDBClient amazonDynamoDBClient) {

		String defaultRegionCode = System.getenv("AWS_DEFAULT_REGION");

		if(defaultRegionCode != null) {
			amazonDynamoDBClient.setRegion(RegionUtils.getRegion(defaultRegionCode));
		}

	}
}
