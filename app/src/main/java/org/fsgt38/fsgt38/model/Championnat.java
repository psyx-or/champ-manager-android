package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Championnat implements Serializable {

	public enum ChampType { ALLER, ALLER_RETOUR, COUPE }

	private Integer id;
	private Sport sport;
	private String nom;
	private String saison;
	private int ptvict;
	private Integer ptnul;
	private int ptdef;
	private ChampType type;

	private Classement[] classements;

	public Championnat() {
	}

	public Championnat(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
