package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;

/**
 * Adapteur pour les classements d'une équipe
 */
public class ClassementAdapter extends RecyclerView.Adapter<ClassementViewHolder> {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private final ChampionnatEquipeDTO dto;


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Constructor
	 * @param dto Liste des championnats
	 */
	public ClassementAdapter(ChampionnatEquipeDTO dto) {
		this.dto = dto;
	}

	/**
	 * Création d'une ligne
	 * @param parent Conteneur
	 * @param viewType Type de vue
	 * @return La ligne
	 */
	@Override
	public ClassementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tableau, parent, false);
		return new ClassementViewHolder(view);
	}

	/**
	 * Affichage d'un championnat
	 * @param holder Ligne
	 * @param position Index du championnat
	 */
	@Override
	public void onBindViewHolder(ClassementViewHolder holder, int position) {
		holder.affiche(dto.getEquipe(), dto.getChampionnats()[position]);
	}

	/**
	 * @return Nombre de championnats
	 */
	@Override
	public int getItemCount() {
		return dto.getChampionnats().length;
	}
}
