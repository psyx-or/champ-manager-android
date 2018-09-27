package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Equipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EquipeService
{
	/**
	 * Recherche une équipe par nom
	 * @param q Partie du nom
	 * @return Liste des 10 premières équipes correspondant
	 */
	@GET("equipe/recherche")
	Call<List<Equipe>> recherche(@Query("q") String q);

	/**
	 * Récupère les données d'une équipe (dépend des droits de l'utilisateur)
	 * @param equipeId Equipe
	 * @return Equipe
	 */
	@GET("equipe/{id}")
	Call<Equipe> get(@Path("id") int equipeId);
}
