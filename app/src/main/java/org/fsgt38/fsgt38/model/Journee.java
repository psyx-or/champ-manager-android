package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Journee {
	private int numero;
	private String libelle;
	private Date debut;
	private Date fin;
	private Match[] matches;
	private Championnat championnat;
}
