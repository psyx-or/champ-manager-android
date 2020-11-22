package org.fsgt38.fsgt38.activity.coupe;

import androidx.recyclerview.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.SimpleAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Coupes dans laquelle une équipe est engagée
 */
public class MatchesCoupeFragment extends ListeFragment<Championnat, Journee> {

	@Override
	protected Call<Journee> getData(Retrofit retrofit) {
		return retrofit.create(MatchesService.class).getHierarchie(getObjet().getId());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Journee finale) {

		return new SimpleAdapter<>(getObjet(), new Journee[]{finale}, MatchesCoupeViewHolder.class);
	}
}
