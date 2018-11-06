package org.fsgt38.fsgt38.activity.fairplay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.FPCategorie;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import butterknife.BindView;

/**
 * Une liste de question
 */
public class FPCategorieViewHolder extends SimpleAdapter.ViewHolder<FairplayActivity, FPCategorie> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.titre)	    TextView txtTitre;
	@BindView(R.id.liste)   	RecyclerView liste;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public FPCategorieViewHolder(View itemView) {
		super(itemView);
	}

	/**
	 * Affiche le classement
	 * @param activity Equipe
	 * @param categorie Classement
	 */
	public void affiche(FairplayActivity activity, FPCategorie categorie) {
		txtTitre.setText(categorie.getLibelle());
		liste.removeAllViews();
		liste.setAdapter(new SimpleAdapter<>(activity, categorie.getQuestions(), FPQuestionViewHolder.class, R.layout.layout_question_eval));
	}
}
