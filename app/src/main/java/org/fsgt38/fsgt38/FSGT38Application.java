package org.fsgt38.fsgt38;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.fsgt38.fsgt38.model.Equipe;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class FSGT38Application extends Application {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	private static final String PREF_EQUIPES_PREFEREES = "equipes";
	private static final String PREF_REMEMBER_ME = "cookie_remmeber_me";
	private static final String PREF_ASTUCE_ROTATION = "astuce_rotation";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	/** Le gestionnaire android des préférences */
	private static SharedPreferences sp;
	/** Les équipes préférées */
	private static Set<Equipe> equipesPreferees;

	/** La session PHP courante */
	@Setter
	@Getter
	private static String sessionId;

	/** L'équipe connectée */
	@Setter
	@Getter
	private static Equipe equipe;


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
	 * Sauvegarde le cookie rememberme
	 * @param rememberMe Cookie remember me
	 */
	public static void setRememberMe(String rememberMe) {
		if (rememberMe != null) {
			sp.edit().putString(PREF_REMEMBER_ME, rememberMe).apply();
		}
		else {
			sp.edit().remove(PREF_REMEMBER_ME).apply();
		}
	}

	/**
	 * @return Le cookie rememberme
	 */
	public static String getRememberMe() {
		return sp.getString(PREF_REMEMBER_ME, null);
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
