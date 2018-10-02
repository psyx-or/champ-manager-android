package org.fsgt38.fsgt38.activity.equipe;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.rest.EquipeService;
import org.fsgt38.fsgt38.util.TableauAdapter;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Coordonnées d'une équipe
 */
public class InfoEquipeFragment extends ListeFragment<Equipe, Equipe> {

	@Override
	protected Call<Equipe> getData(Retrofit retrofit) {
		return retrofit.create(EquipeService.class).get(getObjet().getId());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Equipe data) {

		return new TableauAdapter<>(getObjet(), new Equipe[]{data}, InfoEquipeViewHolder.class);
	}
}
