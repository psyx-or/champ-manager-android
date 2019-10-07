package org.fsgt38.fsgt38.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionnatEquipeDTO {
	private Equipe equipe;
	private Championnat[] championnats;
}
