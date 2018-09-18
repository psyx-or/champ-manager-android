package org.fsgt38.fsgt38;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.Utils;

import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class EquipeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

	public static final String KEY_EQUIPE = EquipeActivity.class.getName() + ".equipe";

	private TextView mTextMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Equipe equipe = (Equipe) getIntent().getSerializableExtra(KEY_EQUIPE);

		getSupportActionBar().setTitle(equipe.getNom());
		setContentView(R.layout.activity_equipe);
		ButterKnife.bind(this);

		mTextMessage = findViewById(R.id.message);
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(this);

		mTextMessage.setText("Id " + equipe.getId());

		// Init API
		Retrofit retrofit = ApiUtils.getApi(this);

		ApiUtils.appel(
				this,
				retrofit.create(ClassementService.class).getEquipe(equipe.getId(), Utils.getSaison()),
				new ApiUtils.Action<ChampionnatEquipeDTO>() {
					@Override
					public void action(ChampionnatEquipeDTO dto) {
//						initChampionnats(championnats);
					}
				}
		);
	}

	public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.navigation_classement:
				mTextMessage.setText(R.string.titre_classement);
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
}
