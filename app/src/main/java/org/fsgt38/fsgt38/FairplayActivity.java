package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.fsgt38.fsgt38.activity.fairplay.FPCategorieViewHolder;
import org.fsgt38.fsgt38.model.FPCategorie;
import org.fsgt38.fsgt38.model.FPFeuille;
import org.fsgt38.fsgt38.model.FPQuestion;
import org.fsgt38.fsgt38.model.dto.FPFeuilleAfficheDTO;
import org.fsgt38.fsgt38.rest.FairplayService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import retrofit2.Retrofit;

/**
 * Ecran permettant de saisir la feuille de fair-play
 */
public class FairplayActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_MATCH = FairplayActivity.class.getName() + ".match";
	public static final String KEY_EQUIPE = FairplayActivity.class.getName() + ".equipe";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.liste)	RecyclerView liste;

	@Getter
	private Map<Integer, Integer> reponses;
	private FPFeuilleAfficheDTO dto;


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
		setContentView(R.layout.activity_fairplay);
		ButterKnife.bind(this);

		// Récupération des données
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(FairplayService.class).getFeuille(
						getIntent().getIntExtra(KEY_MATCH, -1),
						getIntent().getIntExtra(KEY_EQUIPE, -1)),
				new ApiUtils.Action<FPFeuilleAfficheDTO>() {
					@Override
					public void action(FPFeuilleAfficheDTO dto) {
						afficheFeuille(dto);
					}
				}
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
				if (reponses.get(question.getId()) == null) {
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
				new ApiUtils.Action<FPFeuille>() {
					@Override
					public void action(FPFeuille dto) {
						onFeuilleModifiee();
					}
				}
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
		reponses = dto.getReponses();
		liste.setAdapter(new SimpleAdapter<>(this, dto.getFpForm().getCategories(), FPCategorieViewHolder.class, R.layout.layout_liste_liste));
	}

	/**
	 * Fonction appelée une fois que la modification a été acceptée sur le serveur
	 */
	private void onFeuilleModifiee() {
		ApiUtils.videCache();

		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, FSGT38Application.getEquipe());
		startActivity(intent);
		finish();
	}
}
