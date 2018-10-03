package org.fsgt38.fsgt38.activity.championnat;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.activity.commun.EquipeClickListener;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;

public class MatchesChampionnatViewHolder extends TableauViewHolder<Championnat, Journee> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public MatchesChampionnatViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les matches
	 * @param championnat Championnat
	 * @param journee Journee
	 */
	public void affiche(Championnat championnat, Journee journee) {
		init(getString(R.string.journee_i, journee.getNumero()));
		tableau.setShrinkAllColumns(false);
		tableau.setColumnShrinkable(0, true);
		tableau.setColumnShrinkable(1, false);
		tableau.setColumnShrinkable(0, true);

		int i = 0;
		EquipeClickListener clickListener = new EquipeClickListener(R.id.navigation_matches);
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
				// Score
				TableRow ligne = addLigne(style);

				TextView cellule1 = addCelluleEquipe(ligne, null, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
				cellule1.setTag(match.getEquipe1());
				cellule1.setOnClickListener(clickListener);

				addCellule(ligne, getDispScore(match));

				TextView cellule2 = addCelluleEquipe(ligne, null, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());
				cellule2.setTag(match.getEquipe2());
				cellule2.setOnClickListener(clickListener);
			}

			i++;
		}
	}
}
