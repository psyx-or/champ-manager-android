package org.fsgt38.fsgt38;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.fsgt38.fsgt38.model.Equipe;

import java.util.HashSet;
import java.util.Set;

public class FSGT38Application extends Application {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String PREF_EQUIPES_PREFEREES = "equipes";
	private static final String PREF_COOKIE = "cookie";
	private static final String PREF_ASTUCE_ROTATION = "astuce_rotation";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	/** Le gestionnaire android des préférences */
	private static SharedPreferences sp;
	private static Set<Equipe> equipesPreferees;


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

	/**
	 * Sauvegarde que l'astuce de rotation a déjà été affichée
	 */
	public static void setAstuceRotation() {
		sp.edit()
			.putBoolean(PREF_ASTUCE_ROTATION, false)
			.apply();
	}

	/**
	 * @return Vrai si l'astuce de rotation a déjà été affichée
	 */
	public static boolean withAstuceRotation() {
		return sp.getBoolean(PREF_ASTUCE_ROTATION, true);
	}


	// ----------------------------------------------------------------------------------------
	//    Gestion des préférences liées aux équipes préférées
	// ----------------------------------------------------------------------------------------

	/**
	 * Ajoute une équipe préférée
	 * @param equipe equipe
	 */
	public static void retireEquipePreferee(Equipe equipe) {
		equipesPreferees.remove(equipe);
		sauveEquipesPreferees();
	}

	/**
	 * Ajoute une équipe préférée
	 * @param equipe equipe
	 */
	public static void ajouteEquipePreferee(Equipe equipe) {
		equipesPreferees.add(equipe);
		sauveEquipesPreferees();
	}

	/**
	 * Enregistre les équipes préférées dans les préférences
	 */
	private static void sauveEquipesPreferees() {

		Set<String> set = new HashSet<>();
		for (Equipe equipe: equipesPreferees)
			set.add(equipe.serialize());

		sp.edit()
			.putStringSet(PREF_EQUIPES_PREFEREES, set)
			.apply();
	}

	/**
	 * @return Les équipes préférées
	 */
	public static Set<Equipe> getEquipesPreferees() {

		if (equipesPreferees == null) {
			equipesPreferees = new HashSet<>();
			for (String equipeStr: sp.getStringSet(PREF_EQUIPES_PREFEREES, new HashSet<String>()))
				equipesPreferees.add(new Equipe(equipeStr));
		}

		return equipesPreferees;
	}
}
