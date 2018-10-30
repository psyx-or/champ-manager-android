package org.fsgt38.fsgt38;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.fsgt38.fsgt38.activity.resultats.ResultatsFragment;
import org.fsgt38.fsgt38.util.FSGT38Activity;

import butterknife.ButterKnife;

/**
 * Ecran affichant l'écran de saisie des résultats
 */
public class ResultatsActivity extends FSGT38Activity {

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

		setContentView(R.layout.activity_resultats);
		ButterKnife.bind(this);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frame, new ResultatsFragment());
			transaction.commit();
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
}
