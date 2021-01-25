package org.fsgt38.fsgt38.activity.commun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.fsgt38.fsgt38.databinding.FragmentListeBinding;
import org.fsgt38.fsgt38.util.ApiUtils;

import java.io.Serializable;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Un fragment possédant un objet interne et affichant une liste
 */
public abstract class ListeFragment<E extends Serializable, T> extends Fragment {

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

	private FragmentListeBinding binding;

	@Getter
	private E objet;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

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
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentListeBinding.inflate(inflater, container, false);
		View view = super.onCreateView(inflater, container, savedInstanceState);

		Retrofit retrofit = ApiUtils.getApi(getActivity());

		ApiUtils.appel(
				getActivity(),
				getData(retrofit),
				obj -> {
					RecyclerView.Adapter adapter = getAdapter(obj);
					if (adapter.getItemCount() == 0) {
						binding.liste.setVisibility(View.GONE);
						binding.txtVide.setVisibility(View.VISIBLE);
					}
					else {
						binding.liste.setAdapter(adapter);
					}
				}
		);

		return binding.getRoot();
	}
}
