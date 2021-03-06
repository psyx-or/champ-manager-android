package org.fsgt38.fsgt38.activity.equipe;

import android.view.View;

import org.fsgt38.fsgt38.activity.commun.CoupeViewHolder;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;

public class CoupeEquipeViewHolder extends CoupeViewHolder<Equipe> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public CoupeEquipeViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les matches
	 * @param equipe Equipe
	 * @param journee Journee
	 */
	public void affiche(Equipe equipe, Journee journee) {
		affiche(journee.getChampionnat(), journee, equipe);
	}
}
