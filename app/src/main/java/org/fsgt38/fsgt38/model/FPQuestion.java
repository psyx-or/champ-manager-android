package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FPQuestion {
	public enum FPQuestionType { BOOLEEN, EVAL }

	private int id;
	private String titre;
	private String libelle;
	private FPQuestionType type;
}
