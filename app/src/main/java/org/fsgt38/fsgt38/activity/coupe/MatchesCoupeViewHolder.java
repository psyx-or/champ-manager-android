package org.fsgt38.fsgt38.activity.coupe;

import android.view.View;

import org.fsgt38.fsgt38.activity.commun.CoupeViewHolder;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Journee;

public class MatchesCoupeViewHolder extends CoupeViewHolder<Championnat> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public MatchesCoupeViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les matches
	 * @param championnat Championnat
	 * @param journee Journee
	 */
	public void affiche(Championnat championnat, Journee journee) {
		affiche(championnat, journee, null);
	}
}
