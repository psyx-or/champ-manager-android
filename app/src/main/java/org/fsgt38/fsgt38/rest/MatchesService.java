package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Championnat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchesService {

	/**
	 * Renvoie la liste des matches d'une équipe pour la saison courante
	 * @param equipeId Equipe
	 * @param saison Saison
	 * @return Liste des championnats de l'équipe avec leur classement
	 */
	@GET("match/equipe/{id}")
	Call<Championnat[]> listeEquipe(@Path("id") int equipeId, @Query("saison") String saison);
}
