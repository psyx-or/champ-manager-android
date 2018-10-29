package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Equipe;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthentService {

	/**
	 * Essaie de se connecter si les infos sont fournies.
	 * Vérifie si l'utilisateur est connecté sinon.
	 * @param login Login
	 * @param mdp Mot de passe
	 * @param remember "on"
	 */
	@FormUrlEncoded
	@POST("me")
	Call<Equipe> authentifie(@Field("login") String login, @Field("password") String mdp, @Field("_remember_me") String remember);
}
