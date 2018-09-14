package org.fsgt38.fsgt38;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Sport;
import org.fsgt38.fsgt38.rest.ChampionnatService;
import org.fsgt38.fsgt38.rest.EquipeService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RechercheActivity extends AppCompatActivity {

	private static final String BAK_CHAMP = "bak_champ";

	@BindView(R.id.equipeSearchTxt) AutoCompleteTextView equipeSearchTxt;
	@BindView(R.id.spinSport)   	Spinner sports;
	@BindView(R.id.spinChampionnat)	Spinner championnats;

	private EquipeService equipeService;
	private Call<?> callRechercheEquipe;
	private HashMap<Sport, List<Championnat>> mapChampionnats = null;
	private boolean clic = false;


	/**
	 * Création
	 * @param savedInstanceState
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(R.string.titre_recherche);
		setContentView(R.layout.activity_recherche);
		ButterKnife.bind(this);

		// Init view
		equipeSearchTxt.setThreshold(2);
		equipeSearchTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectionEquipe(parent, view, position, id);
			}
		});
		sports.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(getString(R.string.label_chargement))));
		this.championnats.setVisibility(View.INVISIBLE);

		// Init API
		Retrofit retrofit = ApiUtils.getApi(this);
		equipeService = retrofit.create(EquipeService.class);

		// Récupère les championnats
		if (savedInstanceState == null) {
			ApiUtils.appel(
					this,
					retrofit.create(ChampionnatService.class).getChampionnats(Utils.getSaison()),
					new ApiUtils.Action<List<Championnat>>() {
						@Override
						public void action(List<Championnat> championnats) {
							initChampionnats(championnats);
						}
					}
			);
		}
		else {
			mapChampionnats = (HashMap<Sport, List<Championnat>>) savedInstanceState.getSerializable(BAK_CHAMP);
			initChampionnatsSpinner();
		}
	}

	/**
	 * Sauvegarde
	 * @param outState
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BAK_CHAMP, mapChampionnats);
	}

	/**
	 * Traitement de la liste des championnats
	 * @param championnats
	 */
	private void initChampionnats(List<Championnat> championnats) {
		// Regroupement des championnats par sport
		mapChampionnats = new HashMap<>();
		for (Championnat champ: championnats) {
			if (!mapChampionnats.containsKey(champ.getSport())) {
				ArrayList<Championnat> liste = new ArrayList<>();
				liste.add(new Championnat(getString(R.string.label_choix)));
				mapChampionnats.put(champ.getSport(), liste);
			}

			mapChampionnats.get(champ.getSport()).add(champ);
		}

		initChampionnatsSpinner();
	}

	/**
	 * Initialisation des combos sports et championnats
	 */
	private void initChampionnatsSpinner() {
		// Remplissage de la liste des sports
		ArrayAdapter<Sport> adapter = new ArrayAdapter<Sport>(this, android.R.layout.simple_spinner_item, new ArrayList<Sport>(mapChampionnats.keySet()));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sports.setAdapter(adapter);
		championnats.setVisibility(View.VISIBLE);
	}

	/**
	 * Sélection d'un sport
	 * @param position
	 */
	@OnItemSelected(R.id.spinSport)
	public void selectionSport(int position) {

		if (mapChampionnats == null) return;

		// On affiche les championnats du sport
		Sport sport = (Sport)sports.getItemAtPosition(position);
		ArrayAdapter<Championnat> adapter = new ArrayAdapter<Championnat>(this, android.R.layout.simple_spinner_item, mapChampionnats.get(sport));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		championnats.setAdapter(adapter);
	}

	/**
	 * Sélection d'un championnat
	 * @param position
	 */
	@OnItemSelected(R.id.spinChampionnat)
	public void selectionChampionnat(int position) {

		Championnat championnat = (Championnat)championnats.getItemAtPosition(position);
		if (championnat.getId() == null) return;

		// On affiche les championnats du sport
		Toast.makeText(this, championnat.toString(), Toast.LENGTH_LONG).show();
	}

	/**
	 * Recherche d'équipe
	 * @param s
	 * @param start
	 * @param before
	 * @param count
	 */
	@OnTextChanged(R.id.equipeSearchTxt)
	public void rechercheEquipe(CharSequence s, int start, int before, int count) {

		if (callRechercheEquipe != null) callRechercheEquipe.cancel();

		if (s.length()<2 || clic) return;

		callRechercheEquipe = ApiUtils.appel(
			this,
			equipeService.recherche(s.toString()),
			new ApiUtils.Action<List<Equipe>>() {
				@Override
				public void action(List<Equipe> equipes) {

					equipeSearchTxt.setAdapter(new ArrayAdapter<Equipe>(RechercheActivity.this,android.R.layout.simple_dropdown_item_1line, equipes) {
						@NonNull
						@Override
						public View getView(int position, View convertView, @NonNull ViewGroup parent) {
							TextView view = (TextView) super.getView(position, convertView, parent);
							Equipe equipe = getItem(position);
							view.setText(getString(R.string.equipe_nom, equipe.getNom(), equipe.getSport().getNom()));
							return view;
						}
					});
					equipeSearchTxt.showDropDown();
			}
		});
	}

	/**
	 * Sélection d'une équipe
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	public void selectionEquipe(AdapterView<?> parent, View view, int position, long id) {
		clic = true;
		Equipe equipe = (Equipe) parent.getItemAtPosition(position);
		equipeSearchTxt.setText(equipe.getNom());

		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, equipe);
		startActivity(intent);
	}
}
