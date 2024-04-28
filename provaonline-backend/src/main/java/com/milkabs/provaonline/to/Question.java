package com.milkabs.provaonline.to;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Question {

	public Question() {
		if (null == _id) {
			_id = UUID.randomUUID();
		}
	}

	private UUID _id;

	private String question_text;

	private List<Option> options;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("_id: [").append(_id.toString()).append("]\n");
		sb.append("question_text: [").append(question_text).append("]\n");

		for (Option o : options) {
			sb.append("	Option: [").append(o.toString()).append("]");
		}
		return sb.toString();

	}

}
