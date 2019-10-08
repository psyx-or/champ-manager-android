package org.fsgt38.fsgt38.activity.recherche;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.fsgt38.fsgt38.EquipeActivity;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Equipe;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.RequiredArgsConstructor;

/**
 * Adaptateur listant des équipes par leur nom
 */
@RequiredArgsConstructor
public class EquipeAdapter extends RecyclerView.Adapter {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	/** Les équipes (triées) */
	private final Equipe[] equipes;


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
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_equipe, parent, false);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), EquipeActivity.class);
				intent.putExtra(EquipeActivity.KEY_EQUIPE, (Equipe)view.getTag());
				view.getContext().startActivity(intent);
			}
		});
		return new RecyclerView.ViewHolder(view) {};
	}

	/**
	 * Affichage d'une équipe
	 * @param holder Ligne
	 * @param position Index de l'équipe
	 */
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		Equipe equipe = equipes[position];
		((TextView)holder.itemView).setText(equipe.getNom());
		holder.itemView.setTag(equipe);
	}

	/**
	 * @return Nombre d'équipes
	 */
	@Override
	public int getItemCount() {
		return equipes.length;
	}
}
