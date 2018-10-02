package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClassementService {

	/**
	 * Récupère le classement d'un championnat
	 * @param champId Id champ
	 * @return Classement
	 */
	@GET("classement/{id}")
	Call<Championnat> get(@Path("id") int champId);

	/**
	 * @param equipeId Equipe
	 * @param saison Saison
	 * @return Liste des championnats de l'équipe avec leur classement
	 */
	@GET("classement/equipe/{id}")
	Call<ChampionnatEquipeDTO> getEquipe(@Path("id") int equipeId, @Query("saison") String saison);

	/**
	 * @param equipeId Equipe
	 * @return Historique des classements d'une équipe
	 */
	@GET("classement/equipe/{id}/historique")
	Call<ChampionnatEquipeDTO> getHistoriqueEquipe(@Path("id") int equipeId);
}
