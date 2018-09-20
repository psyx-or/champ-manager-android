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
	private Sport sport;
	private String saison;
	private Integer ptnul;
}
