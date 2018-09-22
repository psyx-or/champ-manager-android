package org.fsgt38.fsgt38.activity.equipe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.ButterFragment;

import butterknife.BindView;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Classe mère des fragments de EquipeActivity
 */
public abstract class ListeEquipeFragment<T> extends ButterFragment {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String ARG_EQUIPE = "equipe";


	// ----------------------------------------------------------------------------------------
	//    Méthodes statiques
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param clazz Classe du fragment
	 * @param equipe Equipe dont il faut afficher les championnats
	 * @return Le fragment
	 */
	public static<T extends Fragment> T newInstance(Class<T> clazz, Equipe equipe) {
		try {
			T fragment = clazz.newInstance();
			Bundle args = new Bundle();
			args.putSerializable(ARG_EQUIPE, equipe);
			fragment.setArguments(args);
			return fragment;
		}
		catch (java.lang.InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}


	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------
	//    Méthodes abstraites
	// ----------------------------------------------------------------------------------------

	/**
	 * @param retrofit API
	 * @return Données permettant d'afficher l'écran
	 */
	abstract protected Call<T> getData(Retrofit retrofit);

	/**
	 * @param data Données permettant d'afficher l'écran
	 * @return L'adapteur pour l'affichage
	 */
	abstract protected RecyclerView.Adapter getAdapter(T data);


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.liste)    RecyclerView liste;

	@Getter
	private Equipe equipe;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 */
	public ListeEquipeFragment() {
		super(R.layout.fragment_liste);
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

		Retrofit retrofit = ApiUtils.getApi(getActivity());

		ApiUtils.appel(
				getActivity(),
				getData(retrofit),
				new ApiUtils.Action<T>() {
					@Override
					public void action(T obj) {
						liste.setAdapter(getAdapter(obj));
					}
				}
		);
	}
}
