package com.milkabs.provaonline.to;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class GuessAnswer {
	
	public GuessAnswer() {
		if (null == _id) {
			_id = UUID.randomUUID(); 
		}
	}

	private UUID _id;
	
	private UUID questionId;
	
	private UUID guessOptionId;
	
	
}
