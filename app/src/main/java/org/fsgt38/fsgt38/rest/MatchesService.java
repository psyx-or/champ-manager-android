package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Journee;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchesService {

	/**
	 * Renvoie la liste des matches d'un championnat
	 * @param champId Championnat
	 */
	@GET("match/{id}")
	Call<Championnat> liste(@Path("id") int champId);

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

	/**
	 * Récupère une feuille de match
	 * @param feuille Nom de la feuille de match
	 */
	@GET("match/{feuille}")
	Call<ResponseBody> getFeuille(@Path("feuille") String feuille);

	/**
	 * Met à jour le résultat d'un match
	 * @param matchId Match
	 * @param score1 score1
	 * @param score2 score2
	 * @param forfait1 forfait1
	 * @param forfait2 forfait2
	 * @param feuille feuille
	 * @return ??
	 */
	@Multipart
	@POST("match/")
	Call<Object> majMatch(
			@Part("id") int matchId,
			@Part("score1") Integer score1, @Part("score2") Integer score2,
			@Part("forfait1") boolean forfait1, @Part("forfait2") boolean forfait2,
			@Part MultipartBody.Part feuille);
}
