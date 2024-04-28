package com.milkabs.provaonline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milkabs.provaonline.to.GuessAnswer;
import com.milkabs.provaonline.to.Option;
import com.milkabs.provaonline.to.Question;
import com.milkabs.provaonline.to.Result;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Path("/checkResults")
public class CheckResults {

	@Inject
	private ProvaOnlineApp provaOnline;

	private Map<UUID, Question> mapQuestions;

	CheckResults() {
		Log.info("[CheckResults] call");
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response check(List<GuessAnswer> guesses) {
		Log.debug("[check] call");
		mapQuestions = provaOnline.getMapQuestions();
		Log.debug("[check] mapQuestions" + mapQuestions);
		
		List<Result> lstResultados = new ArrayList<>();
		for (GuessAnswer g : guesses) {
			Result r = new Result();
			r.setQuestionId(g.getQuestionId());
			r.setOptionIdChoice(g.getGuessOptionId());
			r.setCorrect(false);
			for (Option o : mapQuestions.get(g.getQuestionId()).getOptions()) {
				Log.debug("[check] Option: " + o.get_id() + " correct?" + o.getCorrect());
				if (o.get_id().equals(g.getGuessOptionId())) {
					r.setCorrect(o.getCorrect());
				}
			}
			lstResultados.add(r);
		}
		return Response.ok(lstResultados).build();
	}
}
