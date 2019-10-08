package org.fsgt38.fsgt38;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.fsgt38.fsgt38.activity.equipe.ClassementEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.CoupeEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.HistoriqueEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.InfoEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.MatchesEquipeFragment;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.util.FSGT38Activity;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;

/**
 * Ecran affichant une équipe
 */
public class EquipeActivity extends FSGT38Activity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_EQUIPE = EquipeActivity.class.getName() + ".equipe";
	public static final String KEY_ECRAN = EquipeActivity.class.getName() + ".ecran";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private Equipe equipe;


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
		equipe = (Equipe) getIntent().getSerializableExtra(KEY_EQUIPE);
		int ecran = getIntent().getIntExtra(KEY_ECRAN, R.id.navigation_classement);

		// Mise en place de l'écran
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(equipe.getNom());

		setContentView(R.layout.activity_equipe);
		ButterKnife.bind(this);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				return navigate(item.getItemId());
			}
		});

		// Navigation vers le bon fragment
		if (savedInstanceState == null)
			navigation.setSelectedItemId(ecran);
	}

	/**
	 * Initialisation du menu de la barre d'action
	 * @param menu La zone de menu de la barre d'action
	 * @return Vrai si le menu doit être affiché
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		if (FSGT38Application.getEquipesPreferees().contains(equipe))
			menu.findItem(R.id.action_fav_off).setVisible(true);
		else
			menu.findItem(R.id.action_fav_on).setVisible(true);

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
			case R.id.action_fav_on:
				FSGT38Application.ajouteEquipePreferee(equipe);
				invalidateOptionsMenu();
				return true;

			case R.id.action_fav_off:
				invalidateOptionsMenu();
				FSGT38Application.retireEquipePreferee(equipe);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
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

		Fragment fragment = null;
		switch (id)
		{
			case R.id.navigation_classement:
				fragment = ClassementEquipeFragment.newInstance(ClassementEquipeFragment.class, equipe);
				break;
			case R.id.navigation_coupes:
				fragment = CoupeEquipeFragment.newInstance(CoupeEquipeFragment.class, equipe);
				break;
			case R.id.navigation_matches:
				fragment = MatchesEquipeFragment.newInstance(MatchesEquipeFragment.class, equipe);
				break;
			case R.id.navigation_historique:
				fragment = HistoriqueEquipeFragment.newInstance(HistoriqueEquipeFragment.class, equipe);
				break;
			case R.id.navigation_contact:
				fragment = InfoEquipeFragment.newInstance(InfoEquipeFragment.class, equipe);
				break;
		}

		if (fragment == null)
			return false;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, fragment);
		transaction.commit();

		return true;
	}
}
