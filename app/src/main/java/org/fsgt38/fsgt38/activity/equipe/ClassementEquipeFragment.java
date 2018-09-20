package org.fsgt38.fsgt38.activity.equipe;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.ButterFragment;
import org.fsgt38.fsgt38.util.Utils;

import butterknife.BindView;
import retrofit2.Retrofit;

/**
 * Classements d'une équipe
 */
public class ClassementEquipeFragment extends ButterFragment {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String ARG_EQUIPE = "equipe";


	// ----------------------------------------------------------------------------------------
	//    Méthodes statiques
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param equipe Equipe dont il faut afficher les championnats
	 * @return Le fragment
	 */
	public static ClassementEquipeFragment newInstance(Equipe equipe) {
		ClassementEquipeFragment fragment = new ClassementEquipeFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_EQUIPE, equipe);
		fragment.setArguments(args);
		return fragment;
	}


	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.liste)    RecyclerView liste;
	private Equipe equipe;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 */
	public ClassementEquipeFragment() {
		super(R.layout.fragment_classement_equipe);
	}

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			equipe = (Equipe) getArguments().getSerializable(ARG_EQUIPE);
		}

		// Init API
		Retrofit retrofit = ApiUtils.getApi(getActivity());

		ApiUtils.appel(
				getActivity(),
				retrofit.create(ClassementService.class).getEquipe(equipe.getId(), Utils.getSaison()),
				new ApiUtils.Action<ChampionnatEquipeDTO>() {
					@Override
					public void action(ChampionnatEquipeDTO dto) {
						liste.setAdapter(new ClassementAdapter(dto));
					}
				}
		);
	}
}
