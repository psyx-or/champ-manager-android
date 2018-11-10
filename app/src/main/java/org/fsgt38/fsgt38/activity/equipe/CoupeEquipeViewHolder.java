package org.fsgt38.fsgt38.activity.equipe;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;
import org.fsgt38.fsgt38.util.Utils;

public class CoupeEquipeViewHolder extends TableauViewHolder<Equipe, Journee> {

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
		init(journee.getChampionnat().getNom());

		Compteur compteur = new Compteur();
		for (Match match: journee.getMatches())
			affiche(equipe, match, 1, compteur);
	}

	/**
	 * Affiche récursivement les matches
	 * @param equipe Equipe
	 * @param match Match
	 * @param compteur Index du prochain match (modifié dans la fonction)
	 */
	private void affiche(Equipe equipe, Match match, int niveau, Compteur compteur) {

		if (match == null) return;

		affiche(equipe, match.getMatch2(), niveau+1, compteur);
		int i2 = compteur.i;
		affiche(equipe, match.getMatch1(), niveau+1, compteur);
		int i1 = compteur.i;

		// Ajout de la phase
		if (niveau != compteur.lastNiveau) {
			TableRow ligne = addLigneEntete();
			ligne.setClickable(false);
			if (compteur.i > 0)
				((TableLayout.LayoutParams) ligne.getLayoutParams()).setMargins(0, Utils.convertDpToPixel(16, itemView.getContext()),0,0);
			TextView cellule = addCelluleEntete(ligne, getTxtJournee(niveau));
			((TableRow.LayoutParams) cellule.getLayoutParams()).span = 3;
			compteur.lastNiveau = niveau;
			compteur.iStyle = 1;
		}

		compteur.i++;
		compteur.iStyle++;

		// Ajout du match
		int style = (compteur.iStyle % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;
		if (match.getEquipe1() != null && match.getEquipe1().getId() == equipe.getId() ||
			match.getEquipe2() != null && match.getEquipe2().getId() == equipe.getId())
			style = R.layout.tableau_ligne_contenu_selection;

		TableRow ligne = addLigne(style);
		ligne.setClickable(false);
		addCellule(ligne, compteur.i);
		addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2(), i1);
		addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1(), i2);
	}

	/**
	 * Renvoie le texte associé à une journée
	 * @param niveau La journée
	 * @return Le texte
	 */
	private String getTxtJournee(int niveau) {
		return niveau == 1 ? getString(R.string.finale) : getString(R.string.niveau, (int)Math.pow(2, (niveau-1)));
	}


	// ----------------------------------------------------------------------------------------
	//    Classe interne
	// ----------------------------------------------------------------------------------------

	/**
	 * Entier modifiable
	 */
	private class Compteur {
		int i = 0;
		int lastNiveau = 0;
		int iStyle = 0;
	}
}
