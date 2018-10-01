package org.fsgt38.fsgt38.activity.equipe;

import android.content.Intent;
import android.view.View;

import org.fsgt38.fsgt38.EquipeActivity;
import org.fsgt38.fsgt38.model.Equipe;

/**
 * Affiche un écran pour une équipe suite à un clic sur un élément
 */
public class ClickListener implements View.OnClickListener {

	/** L'écran à afficher */
	private final int ecran;

	/**
	 * Constructeur
	 * @param ecran L'écran à afficher
	 */
	public ClickListener(int ecran) {
		this.ecran = ecran;
	}

	/**
	 * Lancement de l'activité
	 * @param v Elément sur lequel on a cliqué
	 */
	@Override
	public void onClick(View v) {
		Equipe equipe = (Equipe) v.getTag();
		if (equipe == null)
			return;

		Intent intent = new Intent(v.getContext(), EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, equipe);
		intent.putExtra(EquipeActivity.KEY_ECRAN, ecran);
		v.getContext().startActivity(intent);
	}
}