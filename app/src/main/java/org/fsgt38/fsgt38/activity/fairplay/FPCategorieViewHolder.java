package org.fsgt38.fsgt38.activity.fairplay;

import android.view.View;

import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.databinding.LayoutCategorieBinding;
import org.fsgt38.fsgt38.model.FPCategorie;
import org.fsgt38.fsgt38.util.SimpleAdapter;

/**
 * Une liste de question
 */
public class FPCategorieViewHolder extends SimpleAdapter.ViewHolder<FairplayActivity, FPCategorie> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private LayoutCategorieBinding binding;
	private FairplayActivity activity;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public FPCategorieViewHolder(View itemView) {
		super(itemView);
		binding = LayoutCategorieBinding.bind(itemView);
		binding.setHolder(this);
	}

	/**
	 * Affiche le classement
	 * @param activity Equipe
	 * @param categorie Classement
	 */
	public void affiche(FairplayActivity activity, FPCategorie categorie) {
		this.activity = activity;

		binding.txtTitre.setText(categorie.getLibelle());

		if (categorie.getQuestions() == null) {
			binding.liste.setVisibility(View.GONE);
			binding.editText.setVisibility(View.VISIBLE);
			binding.editText.setText(activity.getCommentaire());
		}
		else {
			binding.liste.setVisibility(View.VISIBLE);
			binding.editText.setVisibility(View.GONE);
			binding.liste.removeAllViews();
			binding.liste.setAdapter(new SimpleAdapter<>(activity, categorie.getQuestions(), FPQuestionViewHolder.class, R.layout.layout_question_eval));
		}
	}


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Edition du commentaire
	 * @param s Nouveau commentaire
	 */
	public void setCommentaire(CharSequence s) {
		activity.setCommentaire(s.toString());
	}
}
