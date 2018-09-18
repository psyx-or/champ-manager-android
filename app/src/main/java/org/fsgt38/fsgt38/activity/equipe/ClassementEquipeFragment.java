package org.fsgt38.fsgt38.activity.equipe;

import android.os.Bundle;
import android.widget.TextView;

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

	private static final String ARG_EQUIPE = "equipe";

	/**
	 * Instanciate fragment
	 * @param equipe
	 * @return
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

	@BindView(R.id.toto)    TextView textView;
	private Equipe equipe;


	public ClassementEquipeFragment() {
		super(R.layout.fragment_classement_equipe);
	}

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
						textView.setText("Champs: " + dto.getChampionnats().length);
//						initChampionnats(championnats);
					}
				}
		);
	}
}
