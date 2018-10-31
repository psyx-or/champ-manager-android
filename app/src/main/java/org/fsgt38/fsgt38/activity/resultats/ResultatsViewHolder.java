package org.fsgt38.fsgt38.activity.resultats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import org.fsgt38.fsgt38.FSGT38Application;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.util.TableauViewHolder;
import org.joda.time.LocalDate;

import java.util.Date;

public class ResultatsViewHolder extends TableauViewHolder<Equipe, Championnat> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public ResultatsViewHolder(View itemView) {
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
//		EquipeClickListener clickListenerMatch = new EquipeClickListener(R.id.navigation_matches);
		for (Journee journee: championnat.getJournees()) {
			for (Match match: journee.getMatches()) {

				if (match.getExempt() != null) {
					continue;
				}

				int style = (i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;

				// Statut de la rencontre
				ImageView bouton = new ImageView(itemView.getContext());
				if (match.getEquipe1() == null || match.getEquipe2() == null) {
					style = R.layout.tableau_ligne_grisee;
				}
				else {
					if (match.getValide() == Boolean.TRUE) {
						// Match validé
						style = R.layout.tableau_ligne_grisee;
					}
					else if (isDateKO(journee.getFin())) {
						// Match en retard
						style = R.layout.tableau_ligne_grisee;
					}
					else if (match.getValide() == null) {
						// Match à jouer
						bouton.setImageResource(R.drawable.ic_pencil);
						bouton.setColorFilter(getColor(R.color.vert));
					}
					else {
						// Match joué
						bouton.setImageResource(R.drawable.ic_pencil);
						bouton.setColorFilter(getColor(R.color.noir));
					}
				}
				/*
				calculeStatut(match: MatchExt): void {

					else {
						if (!this.avecFP ||
								this.equipe.id == match.equipe1.id && !match.hasFpFeuille1 ||
								this.equipe.id == match.equipe2.id && !match.hasFpFeuille2)
							match.statut = StatutMatch.JOUE;
						else
							match.statut = StatutMatch.JOUE_FP;
					}
				}

				 */

				// Score
				TableRow ligne = addLigne(style);
//					ligne.setTag(getAutreEquipe(equipe, match));
//					ligne.setOnClickListener(clickListenerMatch);

				addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
				addCellule(ligne, getDispScore(match));
				addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());

				// Feuille de match
				ImageView image = new ImageView(itemView.getContext());
				if (match.getFeuille() != null)
				{
					image.setImageResource(R.drawable.ic_clipboard_outline);
					image.setClickable(true);
					image.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View view)
						{
//								navigation.selectionMenu(
//										(MainActivity) getActivity(),
//										MenuContextuelDialog.MenuItem.FEUILLE_MATCH,
//										new MenuContextuelDialog.Options(null, null, null, null, null, null, null, match.optString("feuille")));
						}
					});
				}
				ligne.addView(image);

				// Bouton d'édition
				ligne.addView(bouton);

				i++;
			}
		}
	}

	/**
	 * Indique si la saisie est toujours autorisée pour une journée
	 * @param date Date de fin de la journée
	 * @return Vrai si la saisie est interdite
	 */
	private boolean isDateKO(Date date) {
		if (date == null) return false;

		return new LocalDate(date.getTime()).plusDays(FSGT38Application.getDureeSaisie()).isBefore(new LocalDate());
	}
}
