package org.fsgt38.fsgt38;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.fsgt38.fsgt38.activity.resultats.ResultatsViewHolder;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.rest.ParametreService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38Activity;
import org.fsgt38.fsgt38.util.SimpleAdapter;
import org.fsgt38.fsgt38.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

import static org.fsgt38.fsgt38.FSGT38Application.PARAM_DUREE_SAISIE;

/**
 * Ecran affichant l'écran de saisie des résultats
 */
public class ResultatsActivity extends FSGT38Activity {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.liste)	RecyclerView liste;
	@BindView(R.id.vide)	TextView txtVide;


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

		setContentView(R.layout.fragment_liste);
		ButterKnife.bind(this);

		final Retrofit retrofit = ApiUtils.getApi(this);

		if (FSGT38Application.getDureeSaisie() == null) {
			ApiUtils.appel(
					this,
					retrofit.create(ParametreService.class).get(PARAM_DUREE_SAISIE),
					new ApiUtils.Action<String>() {
						@Override
						public void action(String valeur) {
							FSGT38Application.setDureeSaisie(Integer.valueOf(valeur));
							chargeMatches(retrofit);
						}
					}
			);
		}
		else {
			chargeMatches(retrofit);
		}
	}

	/**
	 * Initialisation du menu de la barre d'action
	 * @param menu La zone de menu de la barre d'action
	 * @return Vrai si le menu doit être affiché
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.findItem(R.id.action_saisie).setVisible(false);
		menu.findItem(R.id.action_logout).setVisible(true);

		return true;
	}

	/**
	 * Sélection d'un élément dans le menu de la barre d'action
	 * @param item Element sur lequel on a cliqué
	 * @return Vrai si le clic a été traité dans la fonction
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_logout)
			finish();

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Lance le chargement des matches
	 * @param retrofit API
	 */
	private void chargeMatches(Retrofit retrofit) {
		ApiUtils.appel(
				this,
				retrofit.create(MatchesService.class).listeEquipe(FSGT38Application.getEquipe().getId(), Utils.getSaison()),
				new ApiUtils.Action<Championnat[]>() {
					@Override
					public void action(Championnat[] data) {
						RecyclerView.Adapter adapter = new SimpleAdapter<>(FSGT38Application.getEquipe(), data, ResultatsViewHolder.class);
						if (adapter.getItemCount() == 0) {
							liste.setVisibility(View.GONE);
							txtVide.setVisibility(View.VISIBLE);
						}
						else {
							liste.setAdapter(adapter);
						}
					}
				}
		);
	}
}
