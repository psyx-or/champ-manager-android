package org.fsgt38.fsgt38.activity.fairplay;

import android.view.View;
import android.widget.RadioButton;

import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.databinding.LayoutQuestionEvalBinding;
import org.fsgt38.fsgt38.model.FPQuestion;
import org.fsgt38.fsgt38.util.SimpleAdapter;

/**
 * Une question
 */
public class FPQuestionViewHolder extends SimpleAdapter.ViewHolder<FairplayActivity, FPQuestion> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private LayoutQuestionEvalBinding binding;
	private FairplayActivity activity;
	private FPQuestion question;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param itemView Vue
	 */
	public FPQuestionViewHolder(View itemView) {
		super(itemView);
		binding = LayoutQuestionEvalBinding.bind(itemView);
		binding.setHolder(this);
	}

	/**
	 * Affiche les coordonnées
	 * @param activity Equipe
	 * @param question Détail de l'équipe
	 */
	@Override
	public void affiche(FairplayActivity activity, FPQuestion question) {
		this.activity = activity;
		this.question = question;

		binding.txtQuestion.setText(question.getTitre());
		binding.txtDescription.setText(question.getLibelle());
		binding.txtDescription.setVisibility(question.getLibelle()==null ? View.GONE : View.VISIBLE);

		switch (question.getType()) {
			case EVAL:
				binding.btnKO.setText(R.string.eval_ko);
				binding.btnNormal.setVisibility(View.VISIBLE);
				binding.btnOK.setText(R.string.eval_ok);
				break;

			case BOOLEEN:
				binding.btnKO.setText(R.string.eval_non);
				binding.btnNormal.setVisibility(View.GONE);
				binding.btnOK.setText(R.string.eval_oui);
				break;
		}

		Integer reponse = activity.getReponse(question.getId());
		if (reponse == null) {
			binding.grpEval.clearCheck();
		}
		else {
			switch (reponse) {
				case -1:binding.grpEval.check(R.id.btnKO);break;
				case  0:binding.grpEval.check(R.id.btnNormal);break;
				case  1:binding.grpEval.check(R.id.btnOK);break;
			}
		}
	}


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Appui sur un bouton de réponse
	 * @param radioButton Bouton appuyé
	 */
	public void onCheckedChanged(RadioButton radioButton) {
		activity.setReponse(question.getId(), Integer.parseInt((String)radioButton.getTag()));
	}
}
