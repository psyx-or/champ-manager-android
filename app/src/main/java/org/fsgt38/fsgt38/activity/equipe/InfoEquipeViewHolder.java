package org.fsgt38.fsgt38.activity.equipe;

import android.graphics.Paint;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Creneau;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Responsable;
import org.fsgt38.fsgt38.util.IntentUtils;
import org.fsgt38.fsgt38.util.TableauViewHolder;

import java.util.Date;

/**
 * Coordonénes d'une équipe
 */
public class InfoEquipeViewHolder extends TableauViewHolder<Equipe, Equipe> {

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public InfoEquipeViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche les coordonnées
	 * @param equipe Equipe
	 * @param data Détail de l'équipe
	 */
	public void affiche(Equipe equipe, final Equipe data) {
		init(null);

		//--------------
		// Responsables
		//--------------
		int i = 1;
		for (final Responsable responsable: data.getResponsables()) {

			// Titre
			TableRow ligne = addLigneEntete();
			TextView cellule = addCelluleEntete(ligne, R.string.responsable, i);
			((TableRow.LayoutParams)cellule.getLayoutParams()).span = 2;

			ligne = addLigne(R.layout.tableau_ligne_contenu_paire);

			// Nom
			addCellule(ligne, getString(R.string.nom));
			addCellule(ligne, "ALVES Julien");

			// Téléphone
			if (responsable.getTel1() != null) {
				ligne = addLigne(R.layout.tableau_ligne_contenu_paire);
				addCellule(ligne, getString(R.string.tel));
				cellule = addCellule(ligne, responsable.getTel1());
				cellule.setPaintFlags(cellule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
				cellule.setTextColor(getColor(R.color.text_lien));
				cellule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						IntentUtils.ouvreTelephone(itemView.getContext(), responsable.getTel1());
					}
				});
			}

			// Mail
			if (responsable.getMail() != null) {
				ligne = addLigne(R.layout.tableau_ligne_contenu_paire);
				addCellule(ligne, getString(R.string.mail));
				cellule = addCellule(ligne, responsable.getMail());
				cellule.setPaintFlags(cellule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
				cellule.setTextColor(getColor(R.color.text_lien));
				cellule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						IntentUtils.ouvreMail(itemView.getContext(), responsable.getMail());
					}
				});
			}

			i++;
		}


		//--------------
		// Terrain
		//--------------

		// Titre
		TableRow ligne = addLigneEntete();
		TextView cellule = addCelluleEntete(ligne, R.string.terrain);
		((TableRow.LayoutParams)cellule.getLayoutParams()).span = 2;

		// Adresse
		ligne = addLigne(R.layout.tableau_ligne_contenu_paire);
		addCellule(ligne, getString(R.string.adresse));
		if (data.getTerrain() == null) {
			addCellule(ligne, getString(R.string.pasdeterrain));
		}
		else {
			cellule = addCellule(ligne, data.getTerrain());
			cellule.setPaintFlags(cellule.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
			cellule.setTextColor(getColor(R.color.text_lien));
			cellule.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					IntentUtils.ouvreGMaps(itemView.getContext(), data.getPosition() != null ? data.getPosition() : data.getTerrain());
				}
			});
		}

		// Créneaux
		i = 1;
		for (Creneau creneau: data.getCreneaux()) {
			ligne = addLigne(R.layout.tableau_ligne_contenu_paire);
			addCellule(ligne, getString(R.string.creneau, i));
			addCellule(ligne, getString(R.string.creneau_val, new Date((creneau.getJour()+4)*24*3600*1000), creneau.getHeure()));
			i++;
		}
	}
}
