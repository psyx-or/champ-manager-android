package org.fsgt38.fsgt38.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fsgt38.fsgt38.R;

import lombok.RequiredArgsConstructor;

/**
 * Adapteur pour un tableau
 */
@RequiredArgsConstructor
public class TableauAdapter<K,T,V extends TableauViewHolder<K,T>> extends RecyclerView.Adapter<V> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private final K clef;
	private final T[] objs;
	private final Class<V> clazz;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Création d'une ligne
	 * @param parent Conteneur
	 * @param viewType Type de vue
	 * @return La ligne
	 */
	@Override
	public V onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tableau, parent, false);
		try {
			return clazz.getConstructor(View.class).newInstance(view);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Affichage d'un championnat
	 * @param holder Ligne
	 * @param position Index du championnat
	 */
	@Override
	public void onBindViewHolder(V holder, int position) {
		holder.affiche(clef, objs[position]);
	}

	/**
	 * @return Nombre de championnats
	 */
	@Override
	public int getItemCount() {
		return objs.length;
	}
}
