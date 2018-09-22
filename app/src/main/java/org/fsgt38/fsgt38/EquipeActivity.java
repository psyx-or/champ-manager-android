package org.fsgt38.fsgt38;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.fsgt38.fsgt38.activity.equipe.ClassementEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.HistoriqueEquipeFragment;
import org.fsgt38.fsgt38.activity.equipe.ListeEquipeFragment;
import org.fsgt38.fsgt38.model.Equipe;

import butterknife.ButterKnife;

/**
 * Ecran affichant une équipe
 */
public class EquipeActivity extends AppCompatActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_EQUIPE = EquipeActivity.class.getName() + ".equipe";


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

		equipe = (Equipe) getIntent().getSerializableExtra(KEY_EQUIPE);

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

//		navigate(R.id.navigation_classement);
		navigation.setSelectedItemId(R.id.navigation_historique);
//		navigate(R.id.navigation_historique);
	}

	/**
	 * Initialisation du menu de la barre d'action
	 * @param menu La zone de menu de la barre d'action
	 * @return Vrai si le menu doit être affiché
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_equipe, menu);
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
			case R.id.action_fav_on:
				Toast.makeText(this,"On", Toast.LENGTH_LONG).show();
				return true;
			case R.id.action_fav_off:
				Toast.makeText(this,"Off", Toast.LENGTH_LONG).show();
				return true;
			default:
				return false;
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
				fragment = ListeEquipeFragment.newInstance(ClassementEquipeFragment.class, equipe);
				break;
			case R.id.navigation_coupes:
				break;
			case R.id.navigation_resultats:
				break;
			case R.id.navigation_historique:
				fragment = HistoriqueEquipeFragment.newInstance(HistoriqueEquipeFragment.class, equipe);
				break;
			case R.id.navigation_contact:
				break;
		}

		if (fragment == null)
			return false;

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, fragment);
		transaction.addToBackStack(null);
		transaction.commit();

		return true;
	}
}
