package org.fsgt38.fsgt38.activity.commun;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.ButterFragment;

import java.io.Serializable;

import androidx.recyclerview.widget.RecyclerView;
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

	@BindView(R.id.liste)   RecyclerView liste;
	@BindView(R.id.vide)	TextView txtVide;

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
	}

	/**
	 * Création des objets de l'écran
	 *
	 * @param inflater Le gestionnaire de layout
	 * @param container Le conteneur du fragment
	 * @param savedInstanceState Paramètres sauvegardés
	 * @return Les objets à afficher
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		Retrofit retrofit = ApiUtils.getApi(getActivity());

		ApiUtils.appel(
				getActivity(),
				getData(retrofit),
				obj -> {
					RecyclerView.Adapter adapter = getAdapter(obj);
					if (adapter.getItemCount() == 0) {
						liste.setVisibility(View.GONE);
						txtVide.setVisibility(View.VISIBLE);
					}
					else {
						liste.setAdapter(adapter);
					}
				}
		);

		return view;
	}
}
