package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Championnat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChampionnatService {

	/**
	 * @param saison Saison concernée
	 * @return Liste des championnats d'une saison
	 */
	@GET("championnat")
	Call<List<Championnat>> getChampionnats(@Query("saison") String saison);
}
