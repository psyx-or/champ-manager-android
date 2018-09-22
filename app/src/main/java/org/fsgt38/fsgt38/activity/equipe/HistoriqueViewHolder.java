package org.fsgt38.fsgt38.activity.equipe;

import android.view.View;
import android.widget.TableRow;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Classement;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.util.TableauViewHolder;

/**
 * L'historique d'une équipe
 */
public class HistoriqueViewHolder extends TableauViewHolder<Equipe, Championnat[]> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public HistoriqueViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche l'historique
	 * @param equipe Equipe
	 * @param championnats Les championnats
	 */
	public void affiche(Equipe equipe, Championnat[] championnats) {
		init(null);

		// Gestion de la taille de l'écran
		boolean grandEcran = false; // TODO

		// Entête
		TableRow ligne = addLigneEntete();
		addCelluleEntete(ligne, R.string.head_saison);
		addCelluleEntete(ligne, R.string.head_champ);
		addCelluleEntete(ligne, R.string.head_pos);

		if (grandEcran) {
			addCelluleEntete(ligne, R.string.head_pts);
			addCelluleEntete(ligne, R.string.head_joues);
			addCelluleEntete(ligne, R.string.head_gagnes);
			addCelluleEntete(ligne, R.string.head_nuls);
			addCelluleEntete(ligne, R.string.head_perdus);
			addCelluleEntete(ligne, R.string.head_forfait);
			addCelluleEntete(ligne, R.string.head_pour);
			addCelluleEntete(ligne, R.string.head_contre);
			addCelluleEntete(ligne, R.string.head_diff);
			addCelluleEntete(ligne, R.string.head_pen);
		}

		// On remplit les lignes
		int i = 0;
		for (Championnat championnat : championnats) {
			for (Classement classement : championnat.getClassements()) {
				int style = (i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;

				ligne = addLigne(style);
//			ligne.setTag(ligneClassement.opt("nom") + ";" + ligneClassement.opt("idEquipe"));

				addCellule(ligne, championnat.getSaison());
				addCellule(ligne, championnat.getNom());
				addCellule(ligne, classement.getPosition(), R.layout.tableau_cellule_gras);
				if (grandEcran) {
					addCellule(ligne, classement.getPts());
					addCellule(ligne, classement.getMTotal());
					addCellule(ligne, classement.getMVict());
					if (championnat.getPtnul() != null)
						addCellule(ligne, classement.getMNul());
					addCellule(ligne, classement.getMDef());
					addCellule(ligne, classement.getMFo());
					addCellule(ligne, classement.getPour());
					addCellule(ligne, classement.getContre());
					addCellule(ligne, classement.getPour() - classement.getContre());
					int pen = classement.getPenalite();
					addCellule(ligne, pen, pen == 0 ? R.layout.tableau_cellule : R.layout.tableau_cellule_rouge);
				}

				i++;
			}
		}
	}
}
