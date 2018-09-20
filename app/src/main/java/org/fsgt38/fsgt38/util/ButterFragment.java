package org.fsgt38.fsgt38.util;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Un fragment utilisant ButterKnife
 */
public abstract class ButterFragment extends Fragment {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private final int layout;
	private Unbinder unbinder;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param layout Layout
	 */
	protected ButterFragment(int layout) {
		this.layout = layout;
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
		View view = inflater.inflate(layout, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	/**
	 * Suppression de la vue
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
