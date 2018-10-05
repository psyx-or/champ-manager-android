package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Equipe;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthentService {

	/**
	 * Essaie de se connecter si creds est fourni.
	 * Vérifie si l'utilisateur est connecté sinon.
	 * @param creds
	 */
	@FormUrlEncoded
	@POST("me")
	Call<Equipe> authentifie(@Field("login") String login, @Field("password") String mdp, @Field("_remember_me") String remember);
}
