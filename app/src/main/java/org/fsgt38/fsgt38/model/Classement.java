package org.fsgt38.fsgt38.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
