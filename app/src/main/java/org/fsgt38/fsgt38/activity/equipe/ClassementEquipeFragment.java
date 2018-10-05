package org.fsgt38.fsgt38.activity.equipe;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.FSGT38Application;
import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.activity.commun.ClassementViewHolder;
import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.TableauAdapter;
import org.fsgt38.fsgt38.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Classements d'une équipe
 */
public class ClassementEquipeFragment extends ListeFragment<Equipe, ChampionnatEquipeDTO> {

	@Override
	protected Call<ChampionnatEquipeDTO> getData(Retrofit retrofit) {
		return retrofit.create(ClassementService.class).getEquipe(getObjet().getId(), Utils.getSaison());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(ChampionnatEquipeDTO data) {

		// On affiche l'astuce si nécessaire
		if (FSGT38Application.withAstuceRotation()) {
			FSGT38Application.setAstuceRotation();

			new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_phone_rotate_landscape)
					.setTitle("Astuce")
					.setMessage("Faites pivoter votre téléphone pour afficher plus d'informations")
					.setPositiveButton(android.R.string.ok, null)
					.show();
		}

		// On trie les classements
		for (Championnat championnat: data.getChampionnats()) {
			Utils.trieClassements(championnat);
		}

		return new TableauAdapter<>(getObjet(), data.getChampionnats(), ClassementViewHolder.class);
	}
}
