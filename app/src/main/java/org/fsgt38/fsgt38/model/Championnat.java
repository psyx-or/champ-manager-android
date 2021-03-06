package org.fsgt38.fsgt38.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Championnat implements Serializable, Comparable<Championnat> {

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
	private Journee[] journees;
	private FPForm fpForm;


	public Championnat() {
	}

	public Championnat(String nom) {
		this.nom = nom;
	}

	/**
	 * @return Un résumé du championnat pouvant être sérialisé
	 */
	public Championnat toSerializable() {
		Championnat obj = new Championnat();
		obj.id = this.id;
		obj.sport = this.sport;
		obj.nom = this.nom;
		obj.saison = this.saison;
		obj.type = this.type;
		return obj;
	}

	@Override
	public @NonNull String toString() {
		return nom;
	}

	@Override
	public int compareTo(Championnat o) {
		return this.nom.compareTo(o.nom);
	}
}
