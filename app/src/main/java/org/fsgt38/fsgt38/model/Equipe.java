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
	private String terrain;
	private Creneau[] creneaux;
	private String position;
	private Sport sport;
}
