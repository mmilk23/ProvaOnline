package com.milkabs.provaonline;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkabs.provaonline.to.Question;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

@ApplicationScoped
public final class ProvaOnlineApp {

	@Getter
	Map<UUID, Question> mapQuestions;

	ProvaOnlineApp() {
		init();
	}

	private void init() {
		Log.debug("[ProvaOnlineApp] init");
		try {
			List<Question> questions = new ObjectMapper().readValue(readJSONQuestions(), new TypeReference<List<Question>>() {});
			mapQuestions = questions.stream().collect(Collectors.toMap(Question::get_id, Function.identity()));
			Log.info("[ProvaOnlineApp] mapQuestions: " + mapQuestions);
		} catch (IOException | URISyntaxException e) {
			Log.fatal("[ExamTestController] IOException | URISyntaxException", e);
		}
	}

	private File readJSONQuestions() throws URISyntaxException {
		Log.debug("[ProvaOnlineApp] readJSONQuestions call");
		URL resource = getClass().getClassLoader().getResource("examtests_questions.json");
		if (resource == null) {
			Log.fatal("[ExamTestController] resource null");
			throw new IllegalArgumentException("file not found!");
		} else {
			return new File(resource.toURI());
		}
	}
}
