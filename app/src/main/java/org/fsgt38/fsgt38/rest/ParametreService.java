package org.fsgt38.fsgt38.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParametreService {

	/**
	 * Renvoie la valeur d'un paramètre
	 * @param nom Nom du paramètre
	 * @return Valeur du paramètre
	 */
	@GET("parametre/{nom}")
	Call<String> get(@Path("nom") String nom);
}
