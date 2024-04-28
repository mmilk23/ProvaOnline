package com.milkabs.provaonline.to;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Result {
	
	private UUID questionId;
	
	private UUID optionIdChoice;
	
	private Boolean correct;
	

}
