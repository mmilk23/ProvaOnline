package com.milklabs.provaonline;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkabs.provaonline.ProvaOnlineApp;
import com.milkabs.provaonline.to.GuessAnswer;
import com.milkabs.provaonline.to.Question;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;;

@QuarkusTest
class CheckResultsTest {

	@Inject
	ProvaOnlineApp provaOnline;

	public CheckResultsTest() {
		Log.info("[CheckResultsTest] call");
	}

	private List<GuessAnswer> mockDataAllCorrect() {
		Log.info("[mockDataAllCorrect] begin");
		List<GuessAnswer> guesses = new ArrayList<GuessAnswer>();
		for (Map.Entry<UUID, Question> entry : provaOnline.getMapQuestions().entrySet()) {
			GuessAnswer g = new GuessAnswer();
			g.set_id(UUID.randomUUID());
			g.setQuestionId(entry.getValue().get_id());
			g.setGuessOptionId(
					entry.getValue().getOptions().stream().filter(obj -> obj.getCorrect().equals(Boolean.TRUE)).findFirst().get().get_id()
			);
			guesses.add(g);
		}
		return guesses;
	}

	@Test
	void testCheckResultsAllCorrect() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(mockDataAllCorrect());
			Log.infov("[testcheckEndpointAllCorrect] json {0}", json);
			Response r = given().body(json).contentType(ContentType.JSON).when().post("/api/rest/checkResults");
			Log.infov("[testcheckEndpointAllCorrect] Response {0}", r.asPrettyString());
			r.then().statusCode(200);
		} catch (JsonProcessingException e) {
			fail(e);
		}
	}
}
