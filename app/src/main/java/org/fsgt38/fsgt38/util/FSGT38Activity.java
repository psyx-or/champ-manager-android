package org.fsgt38.fsgt38.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.fsgt38.fsgt38.LoginActivity;
import org.fsgt38.fsgt38.R;

public abstract class FSGT38Activity extends AppCompatActivity {

	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

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

		return true;
	}

	/**
	 * Sélection d'un élément dans le menu de la barre d'action
	 * @param item Element sur lequel on a cliqué
	 * @return Vrai si le clic a été traité dans la fonction
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.action_login:
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				return true;

			default:
				return false;
		}
	}
}
