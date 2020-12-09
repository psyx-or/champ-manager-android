package org.fsgt38.fsgt38;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.fsgt38.fsgt38.activity.recherche.EquipeAdapter;
import org.fsgt38.fsgt38.databinding.ActivityRechercheBinding;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.Sport;
import org.fsgt38.fsgt38.rest.ChampionnatService;
import org.fsgt38.fsgt38.rest.EquipeService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38Activity;
import org.fsgt38.fsgt38.util.IntentUtils;
import org.fsgt38.fsgt38.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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

	private ActivityRechercheBinding binding;

	private EquipeService equipeService;
	private Call<?> callRechercheEquipe;
	private TreeMap<Sport, List<Championnat>> mapChampionnats = null;
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

		binding = ActivityRechercheBinding.inflate(getLayoutInflater());
		binding.setActivity(this);
		setContentView(binding.getRoot());

		// Init contrôles
		initFavoris();
		binding.spinSport.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Collections.singletonList(getString(R.string.label_chargement))));
		binding.spinChampionnat.setVisibility(View.INVISIBLE);

		// Init API
		Retrofit retrofit = ApiUtils.getApi(this);
		equipeService = retrofit.create(EquipeService.class);

		// Récupère les championnats
		if (savedInstanceState != null && savedInstanceState.getSerializable(BAK_CHAMP) != null) {
			mapChampionnats = (TreeMap<Sport, List<Championnat>>) savedInstanceState.getSerializable(BAK_CHAMP);
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
		binding.equipeSearchTxt.setText("");
		binding.spinChampionnat.setSelection(0);
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
	 * @param sel Elément sélectionné
	 */
	public void selectionSport(Object sel) {

		if (sel instanceof String) return;

		// On affiche les championnats du sport
		Sport sport = (Sport) sel;
		ArrayAdapter<Championnat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapChampionnats.get(sport));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binding.spinChampionnat.setAdapter(adapter);
	}

	/**
	 * Sélection d'un championnat
	 * @param championnat Championnat sélectionné
	 */
	public void selectionChampionnat(Championnat championnat) {

		if (championnat.getId() == null) return;

		IntentUtils.ouvreChampionnat(this, championnat);
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
			binding.listeFavoris.setVisibility(View.GONE);
		}
		else {
			Equipe[] equipes = favoris.toArray(new Equipe[]{});
			Arrays.sort(equipes);

			binding.listeFavoris.setVisibility(View.VISIBLE);
			binding.listeFavoris.setAdapter(new EquipeAdapter(equipes));
		}
	}

	/**
	 * Traitement de la liste des championnats
	 * @param championnats Liste des championnats
	 */
	private void initChampionnats(List<Championnat> championnats) {
		// Regroupement des championnats par sport
		mapChampionnats = new TreeMap<>();
		for (Championnat champ: championnats) {
			if (!mapChampionnats.containsKey(champ.getSport())) {
				ArrayList<Championnat> liste = new ArrayList<>();
				liste.add(new Championnat(getString(R.string.label_choix)));
				mapChampionnats.put(champ.getSport(), liste);
			}

			mapChampionnats.get(champ.getSport()).add(champ);
		}

		for (List<Championnat> liste: mapChampionnats.values())
			Collections.sort(liste);

		initChampionnatsSpinner();
	}

	/**
	 * Initialisation des combos sports et championnats
	 */
	private void initChampionnatsSpinner() {
		// Remplissage de la liste des sports
		ArrayAdapter<Sport> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(mapChampionnats.keySet()));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		binding.spinSport.setAdapter(adapter);
		binding.spinChampionnat.setVisibility(View.VISIBLE);
	}

	/**
	 * Recherche d'équipe
	 * @param s Texte de la recherche
	 */
	public void rechercheEquipe(CharSequence s) {

		if (callRechercheEquipe != null) callRechercheEquipe.cancel();

		if (s.length()<2 || clic) return;

		callRechercheEquipe = ApiUtils.appel(
			this,
			equipeService.recherche(s.toString()),
			new ApiUtils.Action<List<Equipe>>() {
				@Override
				public void action(List<Equipe> equipes) {

					binding.equipeSearchTxt.setAdapter(new ArrayAdapter<Equipe>(RechercheActivity.this,android.R.layout.simple_dropdown_item_1line, equipes) {
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
						binding.equipeSearchTxt.showDropDown();
					}
					catch (WindowManager.BadTokenException ignore) {
					}
				}
			},
			false);
	}

	/**
	 * Sélection d'une équipe
	 * @param equipe Equipe sélectionnée
	 */
	public void selectionEquipe(Equipe equipe) {
		clic = true;
		binding.equipeSearchTxt.setText(equipe.getNom());

		Intent intent = new Intent(this, EquipeActivity.class);
		intent.putExtra(EquipeActivity.KEY_EQUIPE, equipe);
		startActivity(intent);
	}
}
