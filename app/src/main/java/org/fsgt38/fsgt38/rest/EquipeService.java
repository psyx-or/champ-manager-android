package org.fsgt38.fsgt38.rest;

import org.fsgt38.fsgt38.model.Equipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EquipeService
{
	@GET("equipe/recherche")
	Call<List<Equipe>> recherche(@Query("q") String q);
}
