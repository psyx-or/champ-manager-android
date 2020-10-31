package org.fsgt38.fsgt38;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.recherche.EquipeAdapter;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Sport;
import org.fsgt38.fsgt38.rest.ChampionnatService;
import org.fsgt38.fsgt38.rest.EquipeService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38Activity;
import org.fsgt38.fsgt38.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Ecran de recherche d'une équipe ou d'un championnat
 */
public class RechercheActivity extends FSGT38Activity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String BAK_CHAMP = "bak_champ";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.favoris)         View zoneFavoris;
	@BindView(R.id.listeFavoris)    RecyclerView listeEquipes;
	@BindView(R.id.equipeSearchTxt) AutoCompleteTextView equipeSearchTxt;
	@BindView(R.id.spinSport)   	Spinner sports;
	@BindView(R.id.spinChampionnat)	Spinner championnats;

	private EquipeService equipeService;
	private Call<?> callRechercheEquipe;
	private HashMap<Sport, List<Championnat>> mapChampionnats = null;
	private boolean clic = false;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_recherche);
		ButterKnife.bind(this);

		// Init contrôles
		initFavoris();
		equipeSearchTxt.setThreshold(2);
		equipeSearchTxt.setOnItemClickListener((parent, view, position, id) -> selectionEquipe(parent, position));
		sports.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Collections.singletonList(getString(R.string.label_chargement))));
		this.championnats.setVisibility(View.INVISIBLE);

		// Init API
		Retrofit retrofit = ApiUtils.getApi(this);
		equipeService = retrofit.create(EquipeService.class);

		// Récupère les championnats
		if (savedInstanceState != null && savedInstanceState.getSerializable(BAK_CHAMP) != null) {
			mapChampionnats = (HashMap<Sport, List<Championnat>>) savedInstanceState.getSerializable(BAK_CHAMP);
			initChampionnatsSpinner();
		}
		else {
			ApiUtils.appel(
					this,
					retrofit.create(ChampionnatService.class).getChampionnats(Utils.getSaison()),
					this::initChampionnats,
					false
			);
		}
	}

	/**
	 * Redémarrage de l'activité
	 */
	@Override
	protected void onResume() {
		super.onResume();
		initFavoris();
		equipeSearchTxt.setText("");
		championnats.setSelection(0);
		clic = false;
	}

	/**
	 * Sauvegarde
	 * @param outState Etat
	 */
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BAK_CHAMP, mapChampionnats);
	}

	/**
	 * Sélection d'un sport
	 * @param position Index de l'élément sélectionné
	 */
	@OnItemSelected(R.id.spinSport)
	public void selectionSport(int position) {

		if (mapChampionnats == null) return;

		// On affiche les championnats du sport
		Sport sport = (Sport)sports.getItemAtPosition(position);
		ArrayAdapter<Championnat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapChampionnats.get(sport));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		championnats.setAdapter(adapter);
	}

	/**
	 * Sélection d'un championnat
	 * @param position Index de l'élément sélectionné
	 */
	@OnItemSelected(R.id.spinChampionnat)
	public void selectionChampionnat(int position) {

		Championnat championnat = (Championnat)championnats.getItemAtPosition(position);
		if (championnat.getId() == null) return;

		Intent intent = new Intent(this, ChampionnatActivity.class);
		intent.putExtra(ChampionnatActivity.KEY_CHAMP, championnat);
		startActivity(intent);
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialise la liste des favoris
	 */
	private void initFavoris() {
		// Init favoris
		Set<Equipe> favoris = FSGT38Application.getEquipesPreferees();
		if (favoris.isEmpty()) {
			zoneFavoris.setVisibility(View.GONE);
		}
		else {
			Equipe[] equipes = favoris.toArray(new Equipe[]{});
			Arrays.sort(equipes);

			zoneFavoris.setVisibility(View.VISIBLE);
			listeEquipes.setAdapter(new EquipeAdapter(equipes));
		}
	}

	/**
	 * Traitement de la liste des championnats
	 * @param championnats Liste des championnats
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
		ArrayAdapter<Sport> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(mapChampionnats.keySet()));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sports.setAdapter(adapter);
		championnats.setVisibility(View.VISIBLE);
	}

	/**
	 * Recherche d'équipe
	 * @param s Texte de la recherche
	 */
	@OnTextChanged(R.id.equipeSearchTxt)
	public void rechercheEquipe(CharSequence s) {

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
							if (equipe != null)
								view.setText(getString(R.string.equipe_nom, equipe.getNom(), equipe.getSport().getNom()));
							return view;
						}
					});

					try {
						equipeSearchTxt.showDropDown();
					}
					catch (WindowManager.BadTokenException ignore) {
					}
				}
			},
			false);
	}

	/**
	 * Sélection d'une équipe
	 * @param parent Liste des équipes
	 * @param position Index de l'élément sélectionné
	 */
	private void selectionEquipe(AdapterView<?> parent, int position) {
		clic = true;
		Equipe equipe = (Equipe) parent.getItemAtPosition(position);
		equipeSearchTxt.setText(equipe.getNom());

		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, equipe);
		startActivity(intent);
	}
}
