package org.fsgt38.fsgt38;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.fsgt38.fsgt38.activity.championnat.ClassementChampionnatFragment;
import org.fsgt38.fsgt38.activity.championnat.MatchesChampionnatFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.util.FSGT38Activity;

import static org.fsgt38.fsgt38.util.IntentUtils.KEY_CHAMP;
import static org.fsgt38.fsgt38.util.IntentUtils.KEY_ECRAN;

/**
 * Ecran affichant un championnat
 */
public class ChampionnatActivity extends FSGT38Activity {

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
		int ecran = getIntent().getIntExtra(KEY_ECRAN, R.id.navigation_classement);

		// Mise en place de l'écran
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(championnat.getNom());

		setContentView(R.layout.activity_championnat);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(item -> navigate(item.getItemId()));

		// Navigation vers le bon fragment
		if (savedInstanceState == null)
			navigation.setSelectedItemId(ecran);
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
		if (id == R.id.navigation_classement) {
			fragment = ClassementChampionnatFragment.newInstance(ClassementChampionnatFragment.class, championnat);
		}
		else if (id == R.id.navigation_matches) {
			fragment = MatchesChampionnatFragment.newInstance(MatchesChampionnatFragment.class, championnat);
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
