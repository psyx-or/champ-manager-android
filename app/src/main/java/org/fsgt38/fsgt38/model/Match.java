package org.fsgt38.fsgt38.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Match {
	int id;
	Equipe equipe1;
	Equipe equipe2;
	Integer score1;
	Integer score2;
	String feuille;
	Date dateSaisie;
	Boolean valide;
	boolean forfait1;
	boolean forfait2;
	Match match1;
	Match match2;
	Equipe exempt;
//	fpFeuille1?: FPFeuille;
//	fpFeuille2?: FPFeuille;
	boolean hasFpFeuille1;
	boolean hasFpFeuille2;
}
