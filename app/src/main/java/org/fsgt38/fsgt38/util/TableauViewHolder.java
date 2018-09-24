package org.fsgt38.fsgt38.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Viewholder pour le layout "tableau"
 */
public abstract class TableauViewHolder<K,T> extends RecyclerView.ViewHolder {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.titre)	    TextView txtTitre;
	@BindView(R.id.separateur)  View separateur;
	@BindView(R.id.tableau)	    protected TableLayout tableau;

	abstract public void affiche(K clef, T objet);

	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView La vue
	 */
	public TableauViewHolder(View itemView) {
		super(itemView);

		ButterKnife.bind(this, itemView);
	}

	/**
	 * Initialise l'objet
	 * @param titre Titre du tableau
	 */
	protected void init(String titre) {
		if (titre == null) {
			txtTitre.setVisibility(View.GONE);
			separateur.setVisibility(View.GONE);
		}
		else {
			txtTitre.setText(titre);
		}

		tableau.removeAllViews();
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes liées à la création du tableau
	// ----------------------------------------------------------------------------------------

	/**
	 * Ajoute au tableau une ligne réagissant au clic
	 * @param layout Le style de la ligne
	 * @return La ligne créée
	 */
	protected TableRow addLigne(int layout)
	{
		TableRow ligne = (TableRow) LayoutInflater.from(itemView.getContext()).inflate(layout, null);
//		ligne.setOnClickListener(this); // TODO
		tableau.addView(ligne);
		return ligne;
	}

	/**
	 * Ajoute une ligne d'entête
	 * @return La ligne créée
	 */
	protected TableRow addLigneEntete()
	{
		TableRow ligne = addLigne(R.layout.tableau_ligne_entete);
		ligne.setEnabled(false);
		return ligne;
	}

	/**
	 * Ajoute une cellule d'entête à une ligne
	 * @param ligne La ligne
	 * @param idString Identifiant du contenu de la cellule
	 */
	protected TextView addCelluleEntete(TableRow ligne, int idString)
	{
		return addCellule(ligne, itemView.getContext().getString(idString), R.layout.tableau_cellule_entete);
	}

	/**
	 * Ajoute une cellule à une ligne (en utilisant le style de base)
	 * @param ligne La ligne
	 * @param val Valeur à afficher
	 */
	protected TextView addCellule(TableRow ligne, int val)
	{
		return addCellule(ligne, String.valueOf(val));
	}

	/**
	 * Ajoute une cellule à une ligne
	 * @param ligne La ligne
	 * @param val Valeur à afficher
	 * @param layout Style de la cellule
	 */
	protected TextView addCellule(TableRow ligne, int val, int layout)
	{
		return addCellule(ligne, String.valueOf(val), layout);
	}

	/**
	 * Ajoute une cellule à une ligne (en utilisant le style de base)
	 * @param ligne La ligne
	 * @param texte Texte à afficher
	 */
	protected TextView addCellule(TableRow ligne, CharSequence texte)
	{
		return addCellule(ligne, texte, R.layout.tableau_cellule);
	}

	/**
	 * Ajoute une cellule à une ligne
	 * @param ligne La ligne
	 * @param texte Texte à afficher
	 * @param layout Style de la cellule
	 */
	protected TextView addCellule(TableRow ligne, CharSequence texte, int layout)
	{
		TextView cellule = (TextView) LayoutInflater.from(itemView.getContext()).inflate(layout, null);
		cellule.setText(texte);
		ligne.addView(cellule);
		return cellule;
	}
}
