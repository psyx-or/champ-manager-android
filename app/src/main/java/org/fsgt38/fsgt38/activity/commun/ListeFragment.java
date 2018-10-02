package org.fsgt38.fsgt38.activity.commun;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.ButterFragment;

import java.io.Serializable;

import butterknife.BindView;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Un fragment possédant un objet interne et affichant une liste
 */
public abstract class ListeFragment<E extends Serializable, T> extends ButterFragment {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String ARG_OBJ = "objet";


	// ----------------------------------------------------------------------------------------
	//    Méthodes statiques
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param clazz Classe du fragment
	 * @param obj Objet lié au fragment
	 * @return Le fragment
	 */
	public static<T extends Fragment, E extends Serializable> T newInstance(Class<T> clazz, E obj) {
		try {
			T fragment = clazz.newInstance();
			Bundle args = new Bundle();
			args.putSerializable(ARG_OBJ, obj);
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
	private E objet;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 */
	public ListeFragment() {
		super(R.layout.fragment_liste);
	}

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			objet = (E) getArguments().getSerializable(ARG_OBJ);
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
