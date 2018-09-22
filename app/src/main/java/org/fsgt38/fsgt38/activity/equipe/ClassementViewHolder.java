package org.fsgt38.fsgt38.activity.equipe;

import android.view.View;
import android.widget.TableRow;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Classement;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.util.TableauViewHolder;

/**
 * Un classement d'une équipe
 */
public class ClassementViewHolder extends TableauViewHolder<Equipe, Championnat> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public ClassementViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche le classement
	 * @param equipe Equipe
	 * @param championnat Classement
	 */
	public void affiche(Equipe equipe, Championnat championnat) {
		init(championnat.getNom());

		// Gestion de la taille de l'écran
		boolean grandEcran = false; // TODO

		// Entête
		TableRow ligne = addLigneEntete();
		addCelluleEntete(ligne, R.string.head_pos);
		addCelluleEntete(ligne, R.string.head_equipe);
		addCelluleEntete(ligne, R.string.head_pts);
		addCelluleEntete(ligne, R.string.head_joues);
		if (grandEcran) {
			addCelluleEntete(ligne, R.string.head_gagnes);
			if (championnat.getPtnul() != null)
				addCelluleEntete(ligne, R.string.head_nuls);
			addCelluleEntete(ligne, R.string.head_perdus);
			addCelluleEntete(ligne, R.string.head_forfait);
			addCelluleEntete(ligne, R.string.head_pour);
			addCelluleEntete(ligne, R.string.head_contre);
		}
		addCelluleEntete(ligne, R.string.head_diff);
		addCelluleEntete(ligne, R.string.head_pen);

		// On remplit les lignes
		int i = 0;
		int lastPos = 0;
		for (Classement classement: championnat.getClassements())
		{
			int style = (i % 2 == 0) ? R.layout.tableau_ligne_contenu_paire : R.layout.tableau_ligne_contenu_impaire;
			if (classement.getEquipe().getId() == equipe.getId())
				style = R.layout.tableau_ligne_contenu_selection;

			ligne = addLigne(style);
//			ligne.setTag(ligneClassement.opt("nom") + ";" + ligneClassement.opt("idEquipe"));

			addCellule(ligne, classement.getPosition() == lastPos ? "-" : String.valueOf(classement.getPosition()));
			addCellule(ligne, classement.getEquipe().getNom());
			addCellule(ligne, classement.getPts(), R.layout.tableau_cellule_gras);
			addCellule(ligne, classement.getMTotal());
			if (grandEcran) {
				addCellule(ligne, classement.getMVict());
				if (championnat.getPtnul() != null)
					addCellule(ligne, classement.getMNul());
				addCellule(ligne, classement.getMDef());
				addCellule(ligne, classement.getMFo());
				addCellule(ligne, classement.getPour());
				addCellule(ligne, classement.getContre());
			}
			addCellule(ligne, classement.getPour() - classement.getContre());
			int pen = classement.getPenalite();
			addCellule(ligne, pen, pen == 0 ? R.layout.tableau_cellule : R.layout.tableau_cellule_rouge);

			i++;
			lastPos = classement.getPosition();
		}
	}
}
