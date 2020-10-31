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

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import lombok.AllArgsConstructor;

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
		Queue<MatchWrapper> matches = new ArrayDeque<>();
		matches.add(new MatchWrapper(journee.getMatches()[0], 1));
		affiche(equipe, matches, compteur);
	}

	/**
	 * Affiche récursivement les matches
	 * @param equipe Equipe
	 * @param matches File des matches à traiter (pour le parcours de l'arbre par niveau)
	 * @param compteur Index du prochain match (modifié dans la fonction)
	 */
	private void affiche(Equipe equipe, Queue<MatchWrapper> matches, Compteur compteur) {

		MatchWrapper wrapper = matches.poll();
		if (wrapper == null) return;

		// On explore les enfants
		if (wrapper.match.getMatch2() != null)
			matches.add(new MatchWrapper(wrapper.match.getMatch2(), wrapper.niveau + 1));
		if (wrapper.match.getMatch1() != null)
			matches.add(new MatchWrapper(wrapper.match.getMatch1(), wrapper.niveau + 1));

		affiche(equipe, matches, compteur);

		Integer i1 = compteur.getIndex(wrapper.match.getMatch1());
		Integer i2 = compteur.getIndex(wrapper.match.getMatch2());

		// Ajout de la phase
		if (wrapper.niveau != compteur.lastNiveau) {
			TableRow ligne = addLigneEntete();
			ligne.setClickable(false);
			if (compteur.i > 0)
				((TableLayout.LayoutParams) ligne.getLayoutParams()).setMargins(0, Utils.convertDpToPixel(16, itemView.getContext()),0,0);
			TextView cellule = addCelluleEntete(ligne, getTxtJournee(wrapper.niveau));
			((TableRow.LayoutParams) cellule.getLayoutParams()).span = 3;
			compteur.lastNiveau = wrapper.niveau;
			compteur.iStyle = 1;
		}

		compteur.i++;
		compteur.iStyle++;
		compteur.index.put(wrapper.match.getId(), compteur.i);

		// Ajout du wrapper
		int style = (compteur.iStyle % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;
		if (wrapper.match.getEquipe1() != null && wrapper.match.getEquipe1().getId() == equipe.getId() ||
			wrapper.match.getEquipe2() != null && wrapper.match.getEquipe2().getId() == equipe.getId())
			style = R.layout.tableau_ligne_contenu_selection;

		TableRow ligne = addLigne(style);
		ligne.setClickable(false);
		addCellule(ligne, compteur.i);
		addCelluleEquipe(ligne, equipe, wrapper.match.getEquipe1(), wrapper.match.getScore1(), wrapper.match.getScore2(), wrapper.match.isForfait1(), wrapper.match.isForfait2(), i1);
		addCellule(ligne, getDispScore(wrapper.match));
		addCelluleEquipe(ligne, equipe, wrapper.match.getEquipe2(), wrapper.match.getScore2(), wrapper.match.getScore1(), wrapper.match.isForfait2(), wrapper.match.isForfait1(), i2);
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
	//    Classes internes
	// ----------------------------------------------------------------------------------------

	/**
	 * Entier modifiable
	 */
	private static class Compteur {
		int i = 0;
		int lastNiveau = 0;
		int iStyle = 0;
		final Map<Integer, Integer> index = new HashMap<>();

		Integer getIndex(Match match) {
			return match == null ? null : index.get(match.getId());
		}
	}

	/**
	 * Ajoute des infos à un match
	 */
	@AllArgsConstructor
	private static class MatchWrapper {
		Match match;
		int niveau;
	}
}
