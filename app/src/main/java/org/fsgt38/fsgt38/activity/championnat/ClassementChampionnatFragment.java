package org.fsgt38.fsgt38.activity.championnat;

import android.support.v7.widget.RecyclerView;

import org.fsgt38.fsgt38.activity.commun.ClassementViewHolder;
import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.TableauAdapter;
import org.fsgt38.fsgt38.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Classements d'une équipe
 */
public class ClassementChampionnatFragment extends ListeFragment<Championnat, Championnat> {

	@Override
	protected Call<Championnat> getData(Retrofit retrofit) {
		return retrofit.create(ClassementService.class).get(getObjet().getId());
	}

	@Override
	protected RecyclerView.Adapter getAdapter(Championnat data) {

		// On trie les classements
		Utils.trieClassements(data);

		return new TableauAdapter<>(null, new Championnat[]{data}, ClassementViewHolder.class);
	}
}
