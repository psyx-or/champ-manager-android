package org.fsgt38.fsgt38.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.fsgt38.fsgt38.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiUtils {

	public interface Action<T> {
		void action(T arg);
	}

	private static Retrofit retrofit;


	/**
	 * @param context
	 * @return Objet pour accéder à l'API REST
	 */
	public static Retrofit getApi(Context context) {
		if (retrofit == null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			retrofit = new Retrofit.Builder()
					.baseUrl(context.getString(R.string.url_api))
					.addConverterFactory(JacksonConverterFactory.create(mapper))
					.build();
		}

		return retrofit;
	}

	public static<T> Call<T> appel(final Activity activity, Call<T> call, final Action<T> fonction) {
		call.enqueue(new Callback<T>() {
			@Override
			public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
				fonction.action(response.body());
			}

			@Override
			public void onFailure(@NonNull Call<T> call, @NonNull final Throwable t) {
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
}
