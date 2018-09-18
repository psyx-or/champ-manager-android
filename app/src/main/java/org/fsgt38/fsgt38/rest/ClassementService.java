package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.dto.ChampionnatEquipeDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClassementService {
	@GET("classement/equipe/{id}")
	Call<ChampionnatEquipeDTO> getEquipe(@Path("id") int equipeId, @Query("saison") String saison);
}
