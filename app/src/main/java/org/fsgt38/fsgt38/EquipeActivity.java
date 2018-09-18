package org.fsgt38.fsgt38;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.fsgt38.fsgt38.activity.equipe.ClassementEquipeFragment;
import org.fsgt38.fsgt38.model.Equipe;

import butterknife.ButterKnife;

public class EquipeActivity extends AppCompatActivity {

	public static final String KEY_EQUIPE = EquipeActivity.class.getName() + ".equipe";

	private Equipe equipe;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		equipe = (Equipe) getIntent().getSerializableExtra(KEY_EQUIPE);

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

		navigate(R.id.navigation_classement);
	}

	private boolean navigate(int id)
	{
		Fragment fragment = null;
		switch (id)
		{
			case R.id.navigation_classement:
				fragment = ClassementEquipeFragment.newInstance(equipe);
				break;
			case R.id.navigation_coupes:
				break;
			case R.id.navigation_resultats:
				break;
			case R.id.navigation_historique:
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
