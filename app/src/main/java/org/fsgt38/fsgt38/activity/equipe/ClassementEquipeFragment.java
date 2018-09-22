package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Classement;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.TableauAdapter;
import org.fsgt38.fsgt38.util.Utils;

import java.util.Arrays;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Classements d'une équipe
 */
public class ClassementEquipeFragment extends ListeEquipeFragment<ChampionnatEquipeDTO> {

	@Override
	protected Call<ChampionnatEquipeDTO> getData(Retrofit retrofit) {
		return retrofit.create(ClassementService.class).getEquipe(getEquipe().getId(), Utils.getSaison());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(ChampionnatEquipeDTO data) {

		// On trie les classements
		for (Championnat championnat: data.getChampionnats()) {
			Arrays.sort(championnat.getClassements(), new Comparator<Classement>() {
				@Override
				public int compare(Classement o1, Classement o2) {
					if (o1.getPosition() == o2.getPosition())
						return o1.getEquipe().getNom().compareTo(o2.getEquipe().getNom());
					else
						return o1.getPosition() - o2.getPosition();
				}
			});
		}

		return new TableauAdapter<>(getEquipe(), data.getChampionnats(), ClassementViewHolder.class);
	}
}
