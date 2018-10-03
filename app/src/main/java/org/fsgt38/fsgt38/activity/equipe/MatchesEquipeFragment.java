package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.TableauAdapter;
import org.fsgt38.fsgt38.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Matches d'une équipe
 */
public class MatchesEquipeFragment extends ListeFragment<Equipe, Championnat[]> {

	@Override
	protected Call<Championnat[]> getData(Retrofit retrofit) {
		return retrofit.create(MatchesService.class).listeEquipe(getObjet().getId(), Utils.getSaison());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Championnat[] data) {
		return new TableauAdapter<>(getObjet(), data, MatchesEquipeViewHolder.class);
	}
}
