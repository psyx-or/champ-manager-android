package org.fsgt38.fsgt38.activity.commun;

import android.content.Intent;
import android.view.View;

import org.fsgt38.fsgt38.ChampionnatActivity;
import org.fsgt38.fsgt38.model.Championnat;

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

		Intent intent = new Intent(v.getContext(), ChampionnatActivity.class);
		intent.putExtra(ChampionnatActivity.KEY_CHAMP, championnat);
		intent.putExtra(ChampionnatActivity.KEY_ECRAN, ecran);
		v.getContext().startActivity(intent);
	}
}