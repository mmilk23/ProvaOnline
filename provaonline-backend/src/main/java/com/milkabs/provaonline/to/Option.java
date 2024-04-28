package com.milkabs.provaonline.to;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class Option {
	
	public Option() {
		if (null == _id) {
			_id = UUID.randomUUID(); 
		}
	}

	private UUID _id;
	
	private String option_txt;
	
	private String option_value;
	
	private Boolean correct;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("_id: [").append(_id.toString()).append("]\n");
		sb.append("option_txt: [").append(option_txt).append("]\n")
		  .append("option_value: [").append(option_value).append("]\n")
		  .append("correct: [").append(correct).append("]\n");
		return sb.toString();
	}
}
