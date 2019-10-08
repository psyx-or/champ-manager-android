package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FPFeuille {
	private Integer id;
	private Equipe equipeRedactrice;
	private Equipe equipeEvaluee;
	private String commentaire;
	private Integer ratio;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Boolean alerte;
}
