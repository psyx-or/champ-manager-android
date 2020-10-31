package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match implements Serializable {
	private int id;
	private Equipe equipe1;
	private Equipe equipe2;
	private Integer score1;
	private Integer score2;
	private String feuille;
	private Date dateSaisie;
	private Boolean valide;
	private boolean forfait1;
	private boolean forfait2;
	private Match match1;
	private Match match2;
	private Equipe exempt;
	private boolean hasFpFeuille1;
	private boolean hasFpFeuille2;
	private Date dateReport;
}
