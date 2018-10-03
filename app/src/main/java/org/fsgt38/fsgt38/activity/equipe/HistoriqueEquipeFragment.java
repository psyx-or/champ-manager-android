package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.TableauAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Hstorique d'une équipe
 */
public class HistoriqueEquipeFragment extends ListeFragment<Equipe, ChampionnatEquipeDTO> {

	@Override
	protected Call<ChampionnatEquipeDTO> getData(Retrofit retrofit) {
		return retrofit.create(ClassementService.class).getHistoriqueEquipe(getObjet().getId());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(ChampionnatEquipeDTO data) {

		return new TableauAdapter<>(getObjet(), new Championnat[][] {data.getChampionnats()}, HistoriqueEquipeViewHolder.class);
	}
}
