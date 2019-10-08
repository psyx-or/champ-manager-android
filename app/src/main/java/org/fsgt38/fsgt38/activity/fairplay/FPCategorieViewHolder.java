package org.fsgt38.fsgt38.activity.fairplay;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.FPCategorie;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Une liste de question
 */
public class FPCategorieViewHolder extends SimpleAdapter.ViewHolder<FairplayActivity, FPCategorie> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.titre)	    TextView txtTitre;
	@BindView(R.id.liste)   	RecyclerView liste;
	@BindView(R.id.editText)	EditText editText;

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
	}

	/**
	 * Affiche le classement
	 * @param activity Equipe
	 * @param categorie Classement
	 */
	public void affiche(FairplayActivity activity, FPCategorie categorie) {
		this.activity = activity;

		txtTitre.setText(categorie.getLibelle());

		if (categorie.getQuestions() == null) {
			liste.setVisibility(View.GONE);
			editText.setVisibility(View.VISIBLE);
			editText.setText(activity.getCommentaire());
		}
		else {
			liste.setVisibility(View.VISIBLE);
			editText.setVisibility(View.GONE);
			liste.removeAllViews();
			liste.setAdapter(new SimpleAdapter<>(activity, categorie.getQuestions(), FPQuestionViewHolder.class, R.layout.layout_question_eval));
		}
	}


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Edition du commentaire
	 * @param s Nouveau commentaire
	 */
	@OnTextChanged(R.id.editText)
	protected void setCommentaire(CharSequence s) {
		activity.setCommentaire(s.toString());
	}
}
