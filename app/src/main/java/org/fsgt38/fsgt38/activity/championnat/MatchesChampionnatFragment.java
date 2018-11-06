package org.fsgt38.fsgt38.activity.championnat;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Matches d'une équipe
 */
public class MatchesChampionnatFragment extends ListeFragment<Championnat, Championnat> {

	@Override
	protected Call<Championnat> getData(Retrofit retrofit) {
		return retrofit.create(MatchesService.class).liste(getObjet().getId());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Championnat data) {
		return new SimpleAdapter<>(getObjet(), data.getJournees(), MatchesChampionnatViewHolder.class);
	}
}
