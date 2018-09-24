package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.model.Journee;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.TableauAdapter;
import org.fsgt38.fsgt38.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Coupes dans laquelle une équipe est engagée
 */
public class CoupeEquipeFragment extends ListeEquipeFragment<Journee[]> {

	@Override
	protected Call<Journee[]> getData(Retrofit retrofit) {
		return retrofit.create(MatchesService.class).getHierarchies(getEquipe().getId(), Utils.getSaison());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Journee[] finales) {

		return new TableauAdapter<>(getEquipe(), finales, CoupeViewHolder.class);
	}
}
