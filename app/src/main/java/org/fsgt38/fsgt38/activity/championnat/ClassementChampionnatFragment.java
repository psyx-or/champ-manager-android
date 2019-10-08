package org.fsgt38.fsgt38.activity.championnat;

import org.fsgt38.fsgt38.activity.commun.ClassementViewHolder;
import org.fsgt38.fsgt38.activity.commun.ListeFragment;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.rest.ClassementService;
import org.fsgt38.fsgt38.util.SimpleAdapter;
import org.fsgt38.fsgt38.util.Utils;

import androidx.recyclerview.widget.RecyclerView;
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

		return new SimpleAdapter<>(null, new Championnat[]{data}, ClassementViewHolder.class);
	}
}
