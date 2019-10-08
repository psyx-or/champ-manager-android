package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.fsgt38.fsgt38.activity.fairplay.FPCategorieViewHolder;
import org.fsgt38.fsgt38.model.FPCategorie;
import org.fsgt38.fsgt38.model.FPQuestion;
import org.fsgt38.fsgt38.model.Match;
import org.fsgt38.fsgt38.model.dto.FPFeuilleAfficheDTO;
import org.fsgt38.fsgt38.rest.FairplayService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Ecran permettant de saisir la feuille de fair-play
 */
public class FairplayActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_MATCH = FairplayActivity.class.getName() + ".match";
	public static final String KEY_EQUIPE_NUM = FairplayActivity.class.getName() + ".equipe";
	public static final String KEY_CHAMP_TYPE = ResultatMatchActivity.class.getName() + ".champType";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.liste)	RecyclerView liste;

	private Match match;
	private FPFeuilleAfficheDTO dto;


	// ----------------------------------------------------------------------------------------
	//    Getters & Setters
	// ----------------------------------------------------------------------------------------

	public Integer getReponse(int questionId) {
		return dto.getReponses().get(questionId);
	}

	public void setReponse(int questionId, int reponse) {
		dto.getReponses().put(questionId, reponse);
	}

	public String getCommentaire() {
		return dto.getFpFeuille().getCommentaire();
	}

	public void setCommentaire(String commentaire) {
		dto.getFpFeuille().setCommentaire(commentaire);
	}


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
		setContentView(R.layout.fragment_liste);
		ButterKnife.bind(this);

		// Récupération des données
		match = (Match) getIntent().getSerializableExtra(KEY_MATCH);
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(FairplayService.class).getFeuille(
						match.getId(),
						getIntent().getIntExtra(KEY_EQUIPE_NUM, -1)),
				this::afficheFeuille
		);
	}

	/**
	 * Initialisation du menu de la barre d'action
	 * @param menu La zone de menu de la barre d'action
	 * @return Vrai si le menu doit être affiché
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_fairplay, menu);
		return true;
	}

	/**
	 * Sélection d'un élément dans le menu de la barre d'action
	 * @param item Element sur lequel on a cliqué
	 * @return Vrai si le clic a été traité dans la fonction
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Petit contrôle
		if (item.getItemId() != R.id.action_envoyer)
			return false;

		// On vérifie que tout est rempli
		for (FPCategorie cat: dto.getFpForm().getCategories()) {
			for (FPQuestion question : cat.getQuestions()) {
				if (getReponse(question.getId()) == null) {
					new AlertDialog.Builder(this)
							.setTitle(R.string.erreur)
							.setMessage(R.string.erreur_incomplet)
							.setPositiveButton(android.R.string.yes, null)
							.show();

					return true;
				}
			}
		}

		// On effectue la modification
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(FairplayService.class).majFeuille(dto.getMatchId(),	dto),
				dto -> onFeuilleModifiee()
		);

		return true;
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Affiche la feuille de fair-play
	 * @param dto Feuille de fair-play
	 */
	private void afficheFeuille(FPFeuilleAfficheDTO dto) {
		this.dto = dto;

		if (dto.getFpFeuille().getId() == null) {
			new AlertDialog.Builder(this)
					.setMessage(R.string.fp_forfait)
					.setPositiveButton(R.string.fp_joue, null)
					.setNegativeButton(R.string.lbl_forfait, (dialog, which) -> onFeuilleModifiee())
					.show();
		}

		List<FPCategorie> categories = new ArrayList<>(dto.getFpForm().getCategories());
		FPCategorie catCommentaire = new FPCategorie();
		catCommentaire.setLibelle(getString(R.string.lbl_commentaire));
		categories.add(catCommentaire);

		liste.setAdapter(new SimpleAdapter<>(this, categories.toArray(new FPCategorie[0]), FPCategorieViewHolder.class, R.layout.layout_categorie));
	}

	/**
	 * Fonction appelée une fois que la modification a été acceptée sur le serveur
	 */
	private void onFeuilleModifiee() {
		ApiUtils.videCache();

		Intent intent = new Intent(this, ResultatMatchActivity.class);
		intent.putExtra(ResultatMatchActivity.KEY_MATCH, match);
		intent.putExtra(ResultatMatchActivity.KEY_CHAMP_TYPE, getIntent().getSerializableExtra(KEY_CHAMP_TYPE));
		startActivity(intent);

		finish();
	}
}
