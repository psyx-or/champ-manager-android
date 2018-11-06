package org.fsgt38.fsgt38.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fsgt38.fsgt38.R;

import butterknife.ButterKnife;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Adapteur pour un tableau
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class SimpleAdapter<K,T,V extends SimpleAdapter.ViewHolder<K,T>> extends RecyclerView.Adapter<V> {

	// ----------------------------------------------------------------------------------------
	//    Classe outil
	// ----------------------------------------------------------------------------------------

	public static abstract class ViewHolder<K,T> extends RecyclerView.ViewHolder {

		abstract public void affiche(K clef, T objet);

		/**
		 * Constructeur
		 * @param itemView La vue
		 */
		protected ViewHolder(View itemView) {
			super(itemView);

			ButterKnife.bind(this, itemView);
		}
	}

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private final K clef;
	private final T[] objs;
	private final Class<V> clazz;
	private int layout = R.layout.tableau;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Création d'une ligne
	 * @param parent Conteneur
	 * @param viewType Type de vue
	 * @return La ligne
	 */
	@NonNull
	@Override
	public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
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
	public void onBindViewHolder(@NonNull V holder, int position) {
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
