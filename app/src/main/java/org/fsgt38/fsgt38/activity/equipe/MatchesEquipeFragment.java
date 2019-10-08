package org.fsgt38.fsgt38.activity.equipe;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.SimpleAdapter;
import org.fsgt38.fsgt38.util.Utils;

import androidx.recyclerview.widget.RecyclerView;
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
		return new SimpleAdapter<>(getObjet(), data, MatchesEquipeViewHolder.class);
	}
}
