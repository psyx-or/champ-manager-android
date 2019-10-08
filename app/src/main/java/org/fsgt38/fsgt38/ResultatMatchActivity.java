package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;
import org.fsgt38.fsgt38.util.IntentUtils;
import org.fsgt38.fsgt38.util.Utils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static org.fsgt38.fsgt38.model.Championnat.ChampType;

/**
 * Activité de saisie du résultat d'un match
 */
public class ResultatMatchActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_MATCH = ResultatMatchActivity.class.getName() + ".match";
	public static final String KEY_CHAMP_TYPE = ResultatMatchActivity.class.getName() + ".champType";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private Match match;
	private File fic;

	@BindView(R.id.equipe1)	        public TextView txtEquipe1;
	@BindView(R.id.score1)	        public EditText txtScore1;
	@BindView(R.id.forfait1)        public CheckBox chkForfait1;
	@BindView(R.id.equipe2)	        public TextView txtEquipe2;
	@BindView(R.id.score2)	        public EditText txtScore2;
	@BindView(R.id.forfait2)        public CheckBox chkForfait2;
	@BindView(R.id.feuilleMatch)	public ImageView imgFeuilleMatch;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Mise en place de l'écran
		setContentView(R.layout.activity_resultat_match);
		ButterKnife.bind(this);

		// Initialisation
		match = (Match) getIntent().getSerializableExtra(KEY_MATCH);

		txtEquipe1.setText(match.getEquipe1().getNom());
		if (match.getScore1() != null) {
			String score1 = "" + match.getScore1();
			txtScore1.setText(score1);
			txtScore1.setSelection(score1.length());
		}
		chkForfait1.setChecked(match.isForfait1());
		txtEquipe2.setText(match.getEquipe2().getNom());
		if (match.getScore2() != null) {
			String score2 = "" + match.getScore2();
			txtScore2.setText(score2);
			txtScore2.setSelection(score2.length());
		}
		chkForfait2.setChecked(match.isForfait2());

		// Réinitialisation de la feuille de match
		fic = new File(getCacheDir(),"capture.jpg");
		if (fic.exists() && savedInstanceState == null)
			fic.delete();

		// On affiche le clavier
		Utils.montreClavier(this, txtScore1);
	}

	/**
	 * Redémarrage de l'activité
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (fic.exists())
			afficheBitmap();
	}

	@OnCheckedChanged(R.id.forfait1)
	public void forfait1() {
		txtScore1.setEnabled(!chkForfait1.isChecked());
	}

	@OnCheckedChanged(R.id.forfait2)
	public void forfait2() {
		txtScore2.setEnabled(!chkForfait2.isChecked());
	}

	/**
	 * Lancement de la capture d'écran
	 */
	@OnClick(R.id.btnCapture)
	protected void captureFeuille()	{

		// On ouvre l'appareil photo
		if (!IntentUtils.ouvreAppareilPhoto(this, fic)) {
			new AlertDialog.Builder(this)
					.setTitle(R.string.erreur)
					.setMessage(R.string.erreur_appareil_photo)
					.setPositiveButton(android.R.string.yes, null)
					.show();
		}
	}

	/**
	 * Envoi du résultat
	 */
	@OnClick(R.id.btnEnvoi)
	protected void envoyer() {

		// Gestion des scores
		Integer score1 = null;
		if (!chkForfait1.isChecked() && txtScore1.getText().length() > 0)
			score1 = Integer.valueOf(txtScore1.getText().toString());
		Integer score2 = null;
		if (!chkForfait2.isChecked() && txtScore2.getText().length() > 0)
			score2 = Integer.valueOf(txtScore2.getText().toString());

		// Gestion de la feuille de match
		MultipartBody.Part multipartBody = null;
		if (fic.exists()) {
			RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fic);
			multipartBody = MultipartBody.Part.createFormData("feuille",fic.getName(),requestFile);
		}

		// On envoie au serveur
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(MatchesService.class).majMatch(match.getId(), score1, score2, chkForfait1.isChecked(), chkForfait2.isChecked(), multipartBody),
				ignore -> onMatchModifie()
		);
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Affiche une miniature de la feuille de match
	 */
	private void afficheBitmap()
	{
		imgFeuilleMatch.setVisibility(View.VISIBLE);
		Utils.setBitmap(imgFeuilleMatch, fic);
	}

	/**
	 * Appelée quand le match a été modifié sur le serveur
	 */
	private void onMatchModifie() {
		ApiUtils.videCache();

		ChampType champType = (ChampType) getIntent().getSerializableExtra(KEY_CHAMP_TYPE);
		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, FSGT38Application.getEquipe());
		intent.putExtra(EquipeActivity.KEY_ECRAN, champType == ChampType.COUPE ? R.id.navigation_coupes : R.id.navigation_classement);
		startActivity(intent);
		finish();
	}
}
