package org.fsgt38.fsgt38.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FPFeuille {
	private Integer id;
	private Equipe equipeRedactrice;
	private Equipe equipeEvaluee;
	private String commentaire;
	private Integer ratio;
}
