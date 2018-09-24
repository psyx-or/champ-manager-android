package org.fsgt38.fsgt38.activity.equipe;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;

public class MatchesViewHolder extends TableauViewHolder<Equipe, Championnat> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public MatchesViewHolder(View itemView) {
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
		tableau.setColumnShrinkable(0, true);

		int i = 0;
		for (Journee journee: championnat.getJournees()) {
			for (Match match: journee.getMatches()) {

				int style = (i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;

				TableRow ligne = addLigne(style);

				if (match.getExempt() != null) {
					TextView cellule = addCellule(ligne, itemView.getContext().getString(R.string.exempt, match.getExempt().getNom()));
					cellule.setTypeface(cellule.getTypeface(), Typeface.ITALIC);
				}
				else {
					// TOCO: date du match
					addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
					addCellule(ligne, getDispScore(match));
					addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());
				}

				i++;
			}
		}
	}
}
