package org.fsgt38.fsgt38.activity.equipe;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.activity.commun.EquipeClickListener;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Creneau;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;

import java.util.Date;

public class MatchesEquipeViewHolder extends TableauViewHolder<Equipe, Championnat> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public MatchesEquipeViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les matches
	 * @param equipe Equipe
	 * @param championnat Championnat
	 */
	public void affiche(Equipe equipe, Championnat championnat) {
		init(championnat.getNom());
		tableau.setShrinkAllColumns(false);
		tableau.setColumnShrinkable(0, true);
		tableau.setColumnShrinkable(1, false);
		tableau.setColumnShrinkable(2, true);

		int i = 0;
		EquipeClickListener clickListenerMatch = new EquipeClickListener(R.id.navigation_matches);
		EquipeClickListener clickListenerTerrain = new EquipeClickListener(R.id.navigation_contact);
		for (Journee journee: championnat.getJournees()) {
			for (Match match: journee.getMatches()) {

				int style = (i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;

				if (match.getExempt() != null) {
					// Exempt
					TableRow ligne = addLigne(style);
					TextView cellule = addCellule(ligne, itemView.getContext().getString(R.string.exempt, match.getExempt().getNom()));
					cellule.setTypeface(cellule.getTypeface(), Typeface.ITALIC);
					((TableRow.LayoutParams)cellule.getLayoutParams()).span = 3;
				}
				else {
					// Date de la rencontre
					if (match.getScore1() == null && match.getScore2() == null && !match.isForfait1() && !match.isForfait2() && journee.getDebut() != null) {
						TableRow ligne = addLigne(style);
						ligne.setTag(match.getEquipe1());
						ligne.setOnClickListener(clickListenerTerrain);

						TextView cellule = addCellule(ligne, getDateJournee(journee, match));
						cellule.setTypeface(cellule.getTypeface(), Typeface.ITALIC);
						cellule.setPadding(0, 0, 0, 0);
						((TableRow.LayoutParams)cellule.getLayoutParams()).span = 3;
						cellule.setTextColor(getColor(R.color.text_lien));
					}

					// Score
					TableRow ligne = addLigne(style);
					ligne.setTag(getAutreEquipe(equipe, match));
					ligne.setOnClickListener(clickListenerMatch);

					addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
					addCellule(ligne, getDispScore(match));
					addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());
				}

				i++;
			}
		}
	}

	/**
	 * Renvoie la date des rencontres
	 * @param journee La journée
	 * @param match Le match
	 * @return Une chaîne de caractères
	 */
	private String getDateJournee(Journee journee, Match match) {

		if (match.getEquipe1() == null) {
			Date debut = new Date(journee.getDebut().getTime() + 24*3600*1000);
			return itemView.getContext().getString(R.string.dt_semaine, debut, journee.getFin());
		}
		else if (match.getEquipe1().getCreneaux().length == 0) {
			Date debut = new Date(journee.getDebut().getTime() + 24*3600*1000);
			return itemView.getContext().getString(R.string.dt_sdf, debut, journee.getFin());
		}
		else {
			StringBuilder str = new StringBuilder();
			for (Creneau creneau: match.getEquipe1().getCreneaux()) {
				Date date = new Date(journee.getDebut().getTime() + 24 * 3600 * 1000 + creneau.getJour() * 24 * 3600 * 1000);
				if (str.length() > 0) str.append('\n');
				str.append(itemView.getContext().getString(R.string.dt_jour, date, creneau.getHeure()));
			}
			return str.toString();
		}
	}

	/**
	 * Renvoie l'autre équipe
	 * @param equipe Equipe courante
	 * @param match Match
	 * @return L'autre équipe
	 */
	private Equipe getAutreEquipe(Equipe equipe, Match match) {
		if (match.getEquipe1() != null && match.getEquipe1().getId() != equipe.getId())
			return match.getEquipe1();
		if (match.getEquipe2() != null && match.getEquipe2().getId() != equipe.getId())
			return match.getEquipe2();

		return null;
	}
}
