package org.fsgt38.fsgt38.activity.equipe;

import android.view.View;
import android.widget.TableRow;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;

public class CoupeViewHolder extends TableauViewHolder<Equipe, Journee> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public CoupeViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les matches
	 * @param equipe Equipe
	 * @param journee Journee
	 */
	public void affiche(Equipe equipe, Journee journee) {
		init(journee.getChampionnat().getNom());

		Compteur compteur = new Compteur();
		for (Match match: journee.getMatches())
			affiche(equipe, match, compteur);
	}

	/**
	 * Affiche récursivement les matches
	 * @param equipe Equipe
	 * @param match Match
	 * @param compteur Index du prochain match (modifié dans la fonction)
	 */
	private void affiche(Equipe equipe, Match match, Compteur compteur) {

		if (match == null) return;

		// TODO: Phase du match

		affiche(equipe, match.getMatch1(), compteur);
		int i1 = compteur.i;
		affiche(equipe, match.getMatch2(), compteur);
		int i2 = compteur.i;

		int style = (compteur.i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;
		if (match.getEquipe1() != null && match.getEquipe1().getId() == equipe.getId() ||
			match.getEquipe2() != null && match.getEquipe2().getId() == equipe.getId())
			style = R.layout.tableau_ligne_contenu_selection;

		compteur.i++;

		TableRow ligne = addLigne(style);

		addCellule(ligne, compteur.i);
		addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2(), i1);
		addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1(), i2);
	}


	// ----------------------------------------------------------------------------------------
	//    Classe interne
	// ----------------------------------------------------------------------------------------

	/**
	 * Entier modifiable
	 */
	private class Compteur {
		int i = 0;
	}
}
