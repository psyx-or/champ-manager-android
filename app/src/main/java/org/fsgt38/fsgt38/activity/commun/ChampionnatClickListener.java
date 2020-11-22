package org.fsgt38.fsgt38.activity.commun;

import android.view.View;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.util.IntentUtils;

/**
 * Affiche un écran pour une équipe suite à un clic sur un élément
 */
public class ChampionnatClickListener implements View.OnClickListener {

	/** L'écran à afficher */
	private final int ecran;

	/**
	 * Constructeur
	 * @param ecran L'écran à afficher
	 */
	public ChampionnatClickListener(int ecran) {
		this.ecran = ecran;
	}

	/**
	 * Lancement de l'activité
	 * @param v Elément sur lequel on a cliqué
	 */
	@Override
	public void onClick(View v) {
		Championnat championnat = (Championnat) v.getTag();
		if (championnat == null)
			return;

		IntentUtils.ouvreChampionnat(v.getContext(), championnat, ecran);
	}
}