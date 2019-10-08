package org.fsgt38.fsgt38;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;

import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.rest.AuthentService;
import org.fsgt38.fsgt38.util.ApiUtils;

import java.util.Set;

import androidx.annotation.Nullable;
import retrofit2.Retrofit;

/**
 * Activité de démarrage qui aiguille vers la bonne activité
 */
public class StartActivity extends Activity {

	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (FSGT38Application.getRememberMe() != null) {
			// On affiche l'écran et on s'authentifie
			setContentView(R.layout.activity_start);

			Retrofit retrofit = ApiUtils.getApi(this);
			ApiUtils.appel(
					this,
					retrofit.create(AuthentService.class).authentifie(null, null, null),
					new ApiUtils.Action<Equipe>() {
						@Override
						public void action(Equipe equipe) {
							FSGT38Application.setEquipe(equipe);
							termine();
						}
					},
					false
			);
		}
		else {
			termine();
		}
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Ferme l'écran et affiche l'écran suivant
	 */
	private void termine() {
		Set<Equipe> equipesPreferees = FSGT38Application.getEquipesPreferees();
		if (equipesPreferees.size() == 1) {
			Intent intent = new Intent(this, EquipeActivity.class);
			intent.putExtra(EquipeActivity.KEY_EQUIPE, equipesPreferees.iterator().next());
			TaskStackBuilder.create(this)
				.addNextIntentWithParentStack(intent)
				.startActivities();
		}
		else {
			Intent intent = new Intent(this, RechercheActivity.class);
			startActivity(intent);
		}

		finish();
	}
}
