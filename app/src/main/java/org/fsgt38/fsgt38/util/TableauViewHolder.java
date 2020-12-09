package org.fsgt38.fsgt38.util;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.databinding.TableauBinding;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Match;

/**
 * Viewholder pour le layout "tableau"
 */
public abstract class TableauViewHolder<K,T> extends SimpleAdapter.ViewHolder<K,T> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	protected TableauBinding binding;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView La vue
	 */
	protected TableauViewHolder(View itemView) {
		super(itemView);
		binding = TableauBinding.bind(itemView);
	}

	/**
	 * Initialise l'objet
	 * @param titre Titre du tableau
	 */
	protected void init(String titre) {
		if (titre == null) {
			binding.txtTitre.setVisibility(View.GONE);
			binding.separateur.setVisibility(View.GONE);
		}
		else {
			binding.txtTitre.setText(titre);
		}

		binding.tableau.removeAllViews();
	}

	/**
	 * @param id Id couleur
	 * @return La couleur
	 */
	protected int getColor(int id) {
		return itemView.getContext().getResources().getColor(id);
	}

	/**
	 * @param res String ressource
	 * @param params Paramètres optionnels
	 * @return La chaîne
	 */
	protected String getString(int res, Object... params) {
		return itemView.getContext().getString(res, params);
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
		binding.tableau.addView(ligne);
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
	protected TextView addCelluleEntete(TableRow ligne, int idString, Object... params)
	{
		return addCellule(ligne, itemView.getContext().getString(idString, params), R.layout.tableau_cellule_entete);
	}

	/**
	 * Ajoute une cellule d'entête à une ligne
	 * @param ligne La ligne
	 * @param string Contenu de la cellule
	 */
	protected TextView addCelluleEntete(TableRow ligne, String string)
	{
		return addCellule(ligne, string, R.layout.tableau_cellule_entete);
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


	// ----------------------------------------------------------------------------------------
	//    Méthodes liées à l'affichage des matches
	// ----------------------------------------------------------------------------------------

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
	protected TextView addCelluleEquipe(TableRow ligne, Equipe equipeSel, Equipe equipe, Integer score, Integer scoreAdv, boolean forfait, boolean forfaitAdv) {
		return addCelluleEquipe(ligne, equipeSel, equipe, score, scoreAdv, forfait, forfaitAdv, null);
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
	 * @param iMatch Index du match donnant l'équipe
	 */
	protected TextView addCelluleEquipe(TableRow ligne, Equipe equipeSel, Equipe equipe, Integer score, Integer scoreAdv, boolean forfait, boolean forfaitAdv, Integer iMatch) {

		TextView cellule = addCellule(ligne, getNomEquipe(equipe, iMatch));

		if (equipeSel != null) {
			if (equipe != null && equipe.getId() == equipeSel.getId()) {
				cellule.setTypeface(cellule.getTypeface(), Typeface.BOLD);
			}
			else if (forfait) {
				cellule.setPaintFlags(cellule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				return cellule;
			}
			else {
				return cellule;
			}
		}

		if (forfait) {
			cellule.setPaintFlags(cellule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			return cellule;
		}
		if (forfaitAdv) {
			cellule.setTextColor(getColor(R.color.text_success));
			return cellule;
		}

		if (score == null || scoreAdv == null)
			return cellule;

		if (score > scoreAdv)
			cellule.setTextColor(getColor(R.color.text_success));

		if (score < scoreAdv)
			cellule.setTextColor(getColor(R.color.text_danger));

		return cellule;
	}

	/**
	 * Traduit le résultat du match en chaîne de caractère pour l'affichage en gérant les forfaits
	 * et les matches à jouer
	 * @param match Le match
	 * @return La chaîne à afficher
	 */
	protected String getDispScore(Match match) {
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

	/**
	 * Gère les cas des matches de coupe
	 * @param equipe Equipe (peut être nulle)
	 * @param iMatch Numéro du match
	 * @return Le nom à afficher
	 */
	private String getNomEquipe(Equipe equipe, Integer iMatch) {
		if (equipe != null)
			return equipe.getNom();
		else if (iMatch != null)
			return itemView.getContext().getString(R.string.vainqueur, iMatch);
		else
			return itemView.getContext().getString(R.string.adecider);
	}
}
