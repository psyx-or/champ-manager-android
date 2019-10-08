package org.fsgt38.fsgt38.util;

import android.os.Bundle;

import org.fsgt38.fsgt38.FSGT38Application;
import org.fsgt38.fsgt38.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité affichant une croix dans la barre d'action
 */
public abstract class FSGT38PopupActivity extends AppCompatActivity {

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

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
		}
	}

	/**
	 * Sauvegarde
	 * @param outState Etat
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		FSGT38Application.onSaveInstanceState(outState);
	}

	/**
	 * Appui sur la croix
	 * @return false;
	 */
	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return false;
	}
}
