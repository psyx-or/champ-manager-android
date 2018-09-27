package org.fsgt38.fsgt38.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Equipe implements Serializable
{
	private int id;
	private String nom;
	private Responsable[] responsables = new Responsable[0];
	private String terrain;
	private Creneau[] creneaux = new Creneau[0];
	private String position;
	private Sport sport;
}
