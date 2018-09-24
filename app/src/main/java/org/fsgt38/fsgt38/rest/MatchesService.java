package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Journee;

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

	/**
	 * Renvoie les hiérarchies de matches associées à une équipe
	 * @param equipeId Equipe
	 * @param saison Saison
	 */
	@GET("match/hierarchie/equipe/{id}")
	Call<Journee[]> getHierarchies(@Path("id") int equipeId, @Query("saison") String saison);
}
