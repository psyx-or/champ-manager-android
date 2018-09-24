package org.fsgt38.fsgt38.activity.equipe;

import android.graphics.Paint;
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
	 * Affiche le classement
	 * @param equipe Equipe
	 * @param championnat Classement
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
					addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
					addCellule(ligne, getDispScore(match));
					addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());
				}

				i++;
			}
		}
	}

	/**
	 * Affiche le nom d'une équipe dans le bon style (couleur/gras/barré/...)
	 * @param ligne La ligne du tableau
	 * @param equipeSel L'équipe sélectionnée
	 * @param equipe L'équipe à afficher
	 * @param score Le score
	 * @param scoreAdv Le score de l'adversaire
	 * @param forfait L'équipe a-t-elle fait forfait?
	 * @param forfaitAdv L'équipe adverse a-t-elle fait forfait?
	 */
	private void addCelluleEquipe(TableRow ligne, Equipe equipeSel, Equipe equipe, Integer score, Integer scoreAdv, boolean forfait, boolean forfaitAdv) {

		String nom = equipe == null ? itemView.getContext().getString(R.string.adecider) : equipe.getNom();
		TextView cellule = addCellule(ligne, nom);

		if (equipeSel != null) {
			if (equipe != null && equipe.getId() == equipeSel.getId()) {
				cellule.setTypeface(cellule.getTypeface(), Typeface.BOLD);
			}
			else if (forfait) {
				cellule.setPaintFlags(cellule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				return;
			}
			else {
				return;
			}
		}

		if (forfait) {
			cellule.setPaintFlags(cellule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			return;
		}
		if (forfaitAdv) {
			cellule.setTextColor(getColor(R.color.text_success));
			return;
		}

		if (score == null || scoreAdv == null)
			return;

		if (score > scoreAdv)
			cellule.setTextColor(getColor(R.color.text_success));

		if (score < scoreAdv)
			cellule.setTextColor(getColor(R.color.text_danger));
	}

	/**
	 * Traduit le résultat du match en chaîne de caractère pour l'affichage en gérant les forfaits
	 * et les matches à jouer
	 * @param match Le match
	 * @return La chaîne à afficher
	 */
	private String getDispScore(Match match) {
		String dispScore1 = getDispScore(match.getScore1(), match.isForfait1());
		String dispScore2 = getDispScore(match.getScore2(), match.isForfait2());
		if (dispScore1 == null && dispScore2 == null)
			return itemView.getContext().getString(R.string.ajouer);
		else
			return itemView.getContext().getString(R.string.score, dispScore1, dispScore2);
	}

	/**
	 * Traduit un score
	 * @param score Score
	 * @param forfait Y a-t-il eu forfait?
	 * @return La chaîne à afficher
	 */
	private String getDispScore(Integer score, boolean forfait) {
		if (forfait)
			return itemView.getContext().getString(R.string.forfait);
		else if (score == null)
			return null;
		else
			return score.toString();
	}
}
