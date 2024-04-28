package com.milkabs.provaonline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.milkabs.provaonline.to.Question;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Path("/question")
public class QuestionResource {

	@Inject
	ProvaOnlineApp provaOnline;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		Map<UUID, Question> map = provaOnline.getMapQuestions();
		if (null != map) {
			List<Question> lst = new ArrayList<>(map.values());
			/*
			 for (Question q : map.values()) {
				Collections.shuffle(q.getOptions());
				lst.add(q);
			}
			*/
			Collections.shuffle(lst);			
			return Response.ok(lst).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("_id") UUID _id) {
		Question q = provaOnline.getMapQuestions().get(_id);

		if (null != q) {
			return Response.ok(q).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

		/*
		 * Optional<Question> q = provaOnline.getQuestions().stream().filter(obj ->
		 * obj.get_id().equals(_id)).findFirst(); if (q.isPresent()) { return
		 * Response.ok(q).build(); } else { return
		 * Response.status(Status.NOT_FOUND).build(); }
		 */

	}

	@PUT
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response put() {
		return Response.status(Status.METHOD_NOT_ALLOWED).build();
	}

	@POST
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response post() {
		return Response.status(Status.METHOD_NOT_ALLOWED).build();
	}

}
