package org.fsgt38.fsgt38;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.fsgt38.fsgt38.model.Equipe;

import java.util.Set;

/**
 * Activité de démarrage qui aiguille vers la bonne activité
 */
public class StartActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		Retrofit retrofit = ApiUtils.getApi(this);
//		ApiUtils.appel(
//				this,
//				retrofit.create(AuthentService.class).authentifie("acjelectronic", "toto", "on"),
//				new ApiUtils.Action<Equipe>() {
//					@Override
//					public void action(Equipe equipe) {
//						equipe.getId();
//					}
//				},
//				false
//		);

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
