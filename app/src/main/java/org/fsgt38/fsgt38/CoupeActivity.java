package org.fsgt38.fsgt38;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.fsgt38.fsgt38.activity.coupe.MatchesCoupeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.util.FSGT38Activity;

import static org.fsgt38.fsgt38.util.IntentUtils.KEY_CHAMP;

/**
 * Ecran affichant une coupe
 */
public class CoupeActivity extends FSGT38Activity {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private Championnat championnat;


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

		// Récupération des paramètres
		championnat = (Championnat) getIntent().getSerializableExtra(KEY_CHAMP);

		// Mise en place de l'écran
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(championnat.getNom());

		setContentView(R.layout.activity_coupe);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(item -> navigate(item.getItemId()));

		// Navigation vers le bon fragment
		if (savedInstanceState == null)
			navigation.setSelectedItemId(R.id.navigation_matches);
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Affiche le bon fragment
	 * @param id Element sur lequel on a cliqué
	 * @return Vrai si le clic a été traité dans la fonction
	 */
	private boolean navigate(int id) {

		Fragment fragment;
		if (id == R.id.navigation_matches) {
			fragment = MatchesCoupeFragment.newInstance(MatchesCoupeFragment.class, championnat);
		}
		else {
			return false;
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, fragment);
		transaction.commit();

		return true;
	}
}
