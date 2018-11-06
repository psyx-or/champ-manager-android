package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.FPFeuille;
import org.fsgt38.fsgt38.model.dto.FPFeuilleAfficheDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FairplayService {
	/**
	 * Récupère une feuille de fair-play (éventuellement vide)
	 * @param matchId Identifiant du match
	 * @param equipeNum Numéro de l'équipe (1 ou 2)
	 */
	@GET("fairplay/feuille/{matchId}/{equipeNum}")
	Call<FPFeuilleAfficheDTO> getFeuille(@Path("matchId") int matchId, @Path("equipeNum") int equipeNum);

	/**
	 * Met à jour une feuille de fair-play
	 * @param matchId Identifiant du match
	 * @param dto Feuille de fair-play
	 */
	@POST("fairplay/feuille/{matchId}")
	Call<FPFeuille> majFeuille(@Path("matchId") int matchId, @Body FPFeuilleAfficheDTO dto);
}
