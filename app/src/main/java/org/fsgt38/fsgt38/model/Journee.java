package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Journee {
	int numero;
	String libelle;
	Date debut;
	Date fin;
	Match[] matches;
	Championnat championnat;
}
