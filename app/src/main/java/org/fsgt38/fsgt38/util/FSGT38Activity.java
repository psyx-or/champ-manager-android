package org.fsgt38.fsgt38.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.fsgt38.fsgt38.FSGT38Application;
import org.fsgt38.fsgt38.LoginActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.ResultatsActivity;

/**
 * Activité gérant la barre d'action
 */
public abstract class FSGT38Activity extends AppCompatActivity {

	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FSGT38Application.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * Sauvegarde
	 * @param outState Etat
	 */
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		FSGT38Application.onSaveInstanceState(outState);
	}

	/**
	 * Redémarrage de l'activité
	 */
	@Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}

	/**
	 * Initialisation du menu de la barre d'action
	 * @param menu La zone de menu de la barre d'action
	 * @return Vrai si le menu doit être affiché
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_action, menu);

		menu.findItem(R.id.action_fav_on).setVisible(false);
		menu.findItem(R.id.action_fav_off).setVisible(false);

		if (FSGT38Application.getEquipe() == null)
			menu.findItem(R.id.action_saisie).setVisible(false);
		else
			menu.findItem(R.id.action_login).setVisible(false);

		menu.findItem(R.id.action_logout).setVisible(false);

		return true;
	}

	/**
	 * Sélection d'un élément dans le menu de la barre d'action
	 * @param item Element sur lequel on a cliqué
	 * @return Vrai si le clic a été traité dans la fonction
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_refresh) {
			ApiUtils.videCache();
			recreate();
			return true;
		}
		else if (item.getItemId() == R.id.action_login) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;
		}
		else if (item.getItemId() == R.id.action_saisie) {
			Intent intent = new Intent(this, ResultatsActivity.class);
			startActivity(intent);
			return true;
		}
		else if (item.getItemId() == R.id.action_logout) {
			FSGT38Application.setEquipe(null);
			FSGT38Application.setSessionId(null);
			FSGT38Application.setRememberMe(null);
			invalidateOptionsMenu();
			return true;
		}
		else {
			return false;
		}
	}
}
