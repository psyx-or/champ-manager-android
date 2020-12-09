package org.fsgt38.fsgt38.activity.resultats;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import org.fsgt38.fsgt38.FSGT38Application;
import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.ImageActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.ResultatMatchActivity;
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
	public void affiche(final Equipe equipe, final Championnat championnat) {
		init(championnat.getNom());
		binding.tableau.setShrinkAllColumns(false);
		binding.tableau.setColumnShrinkable(0, true);
		binding.tableau.setColumnShrinkable(1, false);
		binding.tableau.setColumnShrinkable(2, true);

		int i = 0;
		for (Journee journee: championnat.getJournees()) {
			for (final Match match: journee.getMatches()) {

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
						bouton.setColorFilter(getColor(R.color.text_success));
					}
					else {
						// Match joué
						bouton.setImageResource(R.drawable.ic_pencil);
						if (championnat.getFpForm() != null &&
						    !match.isForfait1() && !match.isForfait2() && (
								equipe.getId() == match.getEquipe1().getId() && !match.isHasFpFeuille1() ||
								equipe.getId() == match.getEquipe2().getId() && !match.isHasFpFeuille2())) {

							// Il faut saisir le fair-play
							bouton.setColorFilter(getColor(R.color.text_danger));
						}
						else {
							// Pas de fair-play ou fair-play déjà rempli ou forfait (=> pas besoin de feuille de fair-play)
							bouton.setColorFilter(getColor(R.color.noir));
						}
					}
				}

				// Score
				TableRow ligne = addLigne(style);
				addCelluleEquipe(ligne, equipe, match.getEquipe1(), match.getScore1(), match.getScore2(), match.isForfait1(), match.isForfait2());
				addCellule(ligne, getDispScore(match));
				addCelluleEquipe(ligne, equipe, match.getEquipe2(), match.getScore2(), match.getScore1(), match.isForfait2(), match.isForfait1());

				// Gestion du clic
				if (style != R.layout.tableau_ligne_grisee) {
					ligne.setOnClickListener(view -> {
						if (championnat.getFpForm() != null) {
							Intent intent = new Intent(itemView.getContext(), FairplayActivity.class);
							intent.putExtra(FairplayActivity.KEY_MATCH, match);
							intent.putExtra(FairplayActivity.KEY_EQUIPE_NUM, equipe.getId() == match.getEquipe1().getId() ? 1 : 2);
							intent.putExtra(FairplayActivity.KEY_CHAMP_TYPE, championnat.getType());
							itemView.getContext().startActivity(intent);
						}
						else {
							Intent intent = new Intent(itemView.getContext(), ResultatMatchActivity.class);
							intent.putExtra(ResultatMatchActivity.KEY_MATCH, match);
							intent.putExtra(ResultatMatchActivity.KEY_CHAMP_TYPE, championnat.getType());
							itemView.getContext().startActivity(intent);
						}
					});
				}

				// Feuille de match
				ImageView image = new ImageView(itemView.getContext());
				if (match.getFeuille() != null)
				{
					image.setImageResource(R.drawable.ic_clipboard_outline);
					image.setClickable(true);
					image.setOnClickListener(view -> {
						Intent intent = new Intent(itemView.getContext(), ImageActivity.class);
						intent.putExtra(ImageActivity.KEY_FICHIER, match.getFeuille());
						itemView.getContext().startActivity(intent);
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
