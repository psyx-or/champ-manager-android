package org.fsgt38.fsgt38.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FPForm implements Serializable {
	private int id;
	private String libelle;
//	private categories: FPCategorie[] = [];
//	private champModeles: ChampModele[] = [];
}
