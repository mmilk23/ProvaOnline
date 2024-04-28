package com.milklabs.provaonline;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;;

@QuarkusTest
public class QuestionResourceTest {

	@Test
	public void testQuestionEndpoint() {
		given().when().get("/api/rest/question").then().statusCode(200)
				.body(containsString("6fea6118-e701-4ce1-b367-bd7d44475d57"));

	}

	@Test
	public void testQuestionEndpointWithParameter() {
		given().when().get("/api/rest/question/{_id}", "1ec67702-7151-4d44-9ac6-f9df4ff47fe1").then().statusCode(200)
				.body(containsString("Your organization needs a large amount"));

	}

}
