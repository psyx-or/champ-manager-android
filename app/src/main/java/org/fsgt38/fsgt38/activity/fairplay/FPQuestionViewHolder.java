package org.fsgt38.fsgt38.activity.fairplay;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.fsgt38.fsgt38.FairplayActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.FPQuestion;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Une question
 */
public class FPQuestionViewHolder extends SimpleAdapter.ViewHolder<FairplayActivity, FPQuestion> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.question)	TextView txtQuestion;
	@BindView(R.id.description) TextView txtDescription;
	@BindView(R.id.grpEval) 	RadioGroup grpEval;
	@BindView(R.id.btnKO) 	    RadioButton btnKO;
	@BindView(R.id.btnNormal) 	RadioButton btnNormal;
	@BindView(R.id.btnOK)   	RadioButton btnOK;

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

		txtQuestion.setText(question.getTitre());
		txtDescription.setText(question.getLibelle());
		txtDescription.setVisibility(question.getLibelle()==null ? View.GONE : View.VISIBLE);

		switch (question.getType()) {
			case EVAL:
				btnKO.setText(R.string.eval_ko);
				btnNormal.setVisibility(View.VISIBLE);
				btnOK.setText(R.string.eval_ok);
				break;

			case BOOLEEN:
				btnKO.setText(R.string.eval_non);
				btnNormal.setVisibility(View.GONE);
				btnOK.setText(R.string.eval_oui);
				break;
		}

		Integer reponse = activity.getReponses().get(question.getId());
		if (reponse == null) {
			grpEval.clearCheck();
		}
		else {
			switch (reponse) {
				case -1:grpEval.check(R.id.btnKO);break;
				case  0:grpEval.check(R.id.btnNormal);break;
				case  1:grpEval.check(R.id.btnOK);break;
			}
		}
	}

	/**
	 * Appui sur un bouton de réponse
	 * @param radioButton Bouton appuyé
	 */
	@OnClick({R.id.btnKO, R.id.btnNormal, R.id.btnOK})
	protected void onCheckedChanged(RadioButton radioButton) {
		activity.getReponses().put(question.getId(), Integer.parseInt((String)radioButton.getTag()));
	}
}
