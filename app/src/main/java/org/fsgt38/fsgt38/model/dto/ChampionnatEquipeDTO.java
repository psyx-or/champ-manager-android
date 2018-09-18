package org.fsgt38.fsgt38.model.dto;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChampionnatEquipeDTO {
	private Equipe equipe;
	private Championnat[] championnats;
}
