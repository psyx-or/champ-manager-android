package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Activité de saisie du résultat d'un match
 */
public class ResultatMatchActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_MATCH = ResultatMatchActivity.class.getName() + ".match";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private Match match;
	private File fic;

	@BindView(R.id.equipe1)	        public TextView txtEquipe1;
	@BindView(R.id.score1)	        public EditText txtScore1;
	@BindView(R.id.equipe2)	        public TextView txtEquipe2;
	@BindView(R.id.score2)	        public EditText txtScore2;
	@BindView(R.id.feuilleMatch)	public ImageView imgFeuilleMatch;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Mise en place de l'écran
		setContentView(R.layout.activity_resultat_match);
		ButterKnife.bind(this);

		// Initialisation
		match = (Match) getIntent().getSerializableExtra(KEY_MATCH);

		String score1 = match.getScore1() == null ? "" : "" + match.getScore1();
		String score2 = match.getScore2() == null ? "" : "" + match.getScore2();
		txtEquipe1.setText(match.getEquipe1().getNom());
		txtScore1.setText(score1);
		txtScore1.setSelection(score1.length());
		txtEquipe2.setText(match.getEquipe2().getNom());
		txtScore2.setText(score2);
		txtScore2.setSelection(score2.length());

		// Gestion de la prévisualisation de la feuille de match
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

	/**
	 * Lancement de la capture d'écran
	 */
	@OnClick(R.id.btnCapture)
	protected void captureFeuille()
	{
		// On crée le fichier
		if (!fic.exists())
		{
			try
			{
				fic.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (!IntentUtils.ouvreAppareilPhoto(this, fic))
		{
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

		// TODO: un truc correct
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fic);

		MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("feuille",fic.getName(),requestFile);


		// On effectue la modification
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(MatchesService.class).majMatch(match.getId(), null, null, false, false, multipartBody),
//				retrofit.create(MatchesService.class).majMatch(match.getId(), Integer.valueOf(txtScore1.getText().toString()), Integer.valueOf(txtScore2.getText().toString()), false, false, fic),
				new ApiUtils.Action<Object>() {
					@Override
					public void action(Object ignore) {
						onMatchModifie();
					}
				}
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

		// TODO: redirection coupes
		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, FSGT38Application.getEquipe());
		startActivity(intent);
		finish();
	}
}
