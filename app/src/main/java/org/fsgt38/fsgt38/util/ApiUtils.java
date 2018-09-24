package org.fsgt38.fsgt38.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.fsgt38.fsgt38.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Fonctions liées à l'API REST
 */
public class ApiUtils {

	// ----------------------------------------------------------------------------------------
	//    Membres statiques
	// ----------------------------------------------------------------------------------------

	private static Retrofit retrofit;


	// ----------------------------------------------------------------------------------------
	//    Méthodes statiques
	// ----------------------------------------------------------------------------------------

	/**
	 * @param context Contexte
	 * @return Objet pour accéder à l'API REST
	 */
	public static Retrofit getApi(Context context) {
		if (retrofit == null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.readTimeout(60, TimeUnit.SECONDS)
					.connectTimeout(60, TimeUnit.SECONDS)
					.build();

			retrofit = new Retrofit.Builder()
					.client(okHttpClient)
					.baseUrl(context.getString(R.string.url_api))
					.addConverterFactory(JacksonConverterFactory.create(mapper))
					.build();
		}

		return retrofit;
	}


	/**
	 * Effectue un appel REST avec waiter
	 * @param activity Activité
	 * @param call Méthode REST à appeler
	 * @param fonction Action à effectuer avec le résultat
	 * @param <T> Type du résultat de la méthode REST
	 * @return Paramètre call
	 */
	public static<T> Call<T> appel(final Activity activity, Call<T> call, final Action<T> fonction) {
		return appel(activity, call, fonction, true);
	}

	/**
	 * Effectue un appel REST
	 * @param activity Activité
	 * @param call Méthode REST à appeler
	 * @param fonction Action à effectuer avec le résultat
	 * @param withWaiter Vrai pour afficher la pop-up de chargement
	 * @param <T> Type du résultat de la méthode REST
	 * @return Paramètre call
	 */
	public static<T> Call<T> appel(final Activity activity, final Call<T> call, final Action<T> fonction, boolean withWaiter) {

		// Mise en place du sablier
		ProgressDialog tmpWaiter = null;
		if (withWaiter) {
			tmpWaiter = Utils.creeWaiter(activity, new DialogInterface.OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dialogInterface)
				{
					call.cancel();
				}
			});
		}

		// Appel REST
		final ProgressDialog waiter = tmpWaiter;
		call.enqueue(new Callback<T>() {

			@Override
			public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
				// C'est OK => on transmet et on cache le waiter
				fonction.action(response.body());
				if (waiter!=null && waiter.isShowing())
					waiter.dismiss();
			}

			@Override
			public void onFailure(@NonNull Call<T> call, @NonNull final Throwable t) {

				// C'est KO => On cache le waiter et on affiche l'erreur
				if (waiter!=null && waiter.isShowing())
					waiter.dismiss();

				if (call.isCanceled()) return;

				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						new AlertDialog.Builder(activity)
								.setTitle(R.string.erreur)
								.setMessage(t.getLocalizedMessage())
								.setPositiveButton(android.R.string.yes, null)
								.show();
					}
				});
			}
		});

		return call;
	}


	// ----------------------------------------------------------------------------------------
	//    Classes internes
	// ----------------------------------------------------------------------------------------

	/**
	 * Une action (pas de Java 8 avec Android Kitkat...)
	 * @param <T>
	 */
	public interface Action<T> {
		void action(T arg);
	}
}
