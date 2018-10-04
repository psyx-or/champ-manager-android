package org.fsgt38.fsgt38.model;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipe implements Serializable, Comparable<Equipe>
{
	private static final String SEPARATEUR = ";";

	private int id;
	private String nom;
	private Responsable[] responsables = new Responsable[0];
	private String terrain;
	private Creneau[] creneaux = new Creneau[0];
	private String position;
	private Sport sport;

	/**
	 * Constructeur "normal"
	 */
	public Equipe() {
	}

	/**
	 * Constructeur de désérialisation
	 * @param str Sauvegarde
	 */
	public Equipe(String str) {
		String[] args = str.split(SEPARATEUR, 2);
		id = Integer.parseInt(args[0]);
		nom = args[1];
	}

	/**
	 * Sérialise l'objet
	 * @return Sauvegarde
	 */
	public String serialize() {
		return id + SEPARATEUR + nom;
	}

	/**
	 * Comparaison de deux équipes
	 * @param equipe Autre équipe
	 * @return -1/0/1
	 */
	@Override
	public int compareTo(@NonNull Equipe equipe) {
		return nom.compareTo(equipe.nom);
	}
}
