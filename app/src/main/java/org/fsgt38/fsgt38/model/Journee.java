package org.fsgt38.fsgt38.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Journee {
	int numero;
	String libelle;
	Date debut;
	Date fin;
	Match[] matches;
}
