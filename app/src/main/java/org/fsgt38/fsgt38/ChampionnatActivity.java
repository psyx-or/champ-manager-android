package org.fsgt38.fsgt38;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.fsgt38.fsgt38.activity.championnat.ClassementChampionnatFragment;
import org.fsgt38.fsgt38.activity.championnat.MatchesChampionnatFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.util.FSGT38Activity;

import butterknife.ButterKnife;

/**
 * Ecran affichant un championnat
 */
public class ChampionnatActivity extends FSGT38Activity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_CHAMP = ChampionnatActivity.class.getName() + ".championnat";
	public static final String KEY_ECRAN = ChampionnatActivity.class.getName() + ".ecran";


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
		ButterKnife.bind(this);

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

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, fragment);
		transaction.commit();

		return true;
	}
}
