package org.fsgt38.fsgt38.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Classement {
	private Equipe equipe;
	private int position;
	private int pts;
	private int penalite;
	private int pour;
	private int contre;
	private int mTotal;
	private int mVict;
	private Integer mNul;
	private int mDef;
	private int mFo;
	private String nomJournee;
}
