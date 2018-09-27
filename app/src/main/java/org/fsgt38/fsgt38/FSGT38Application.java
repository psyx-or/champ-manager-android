package org.fsgt38.fsgt38;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class FSGT38Application extends Application {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String PREF_COOKIE = "cookie";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	/** Le gestionnaire android des préférences */
	private static SharedPreferences sp;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'application
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}


	// ----------------------------------------------------------------------------------------
	//    Gestion des préférences
	// ----------------------------------------------------------------------------------------

	/**
	 * Modifie les cookies
	 * @param cookies Cookies
	 */
	public static void setCookies(Set<String> cookies) {
		sp.edit()
			.putStringSet(PREF_COOKIE, cookies)
			.apply();
	}

	/**
	 * @return Les cookies
	 */
	public static Set<String> getCookies() {
		return sp.getStringSet(PREF_COOKIE, new HashSet<String>());
	}
}
