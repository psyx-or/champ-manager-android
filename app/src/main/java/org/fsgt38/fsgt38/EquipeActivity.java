package org.fsgt38.fsgt38;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.fsgt38.fsgt38.model.Equipe;

import butterknife.ButterKnife;

public class EquipeActivity extends AppCompatActivity {

	public static final String KEY_EQUIPE = EquipeActivity.class.getName() + ".equipe";

	private TextView mTextMessage;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener()
	{

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item)
		{
			switch (item.getItemId())
			{
				case R.id.navigation_accueil:
					mTextMessage.setText(R.string.title_accueil);
					return true;
				case R.id.navigation_favoris:
					mTextMessage.setText(R.string.title_favoris);
					return true;
				case R.id.navigation_resultats:
					mTextMessage.setText(R.string.title_resultats);
					return true;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Equipe equipe = (Equipe) getIntent().getSerializableExtra(KEY_EQUIPE);

		getSupportActionBar().setTitle(equipe.getNom());
		setContentView(R.layout.activity_equipe);
		ButterKnife.bind(this);

		mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		mTextMessage.setText("Id " + equipe.getId());
	}
}
