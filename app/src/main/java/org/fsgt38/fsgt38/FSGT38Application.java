package org.fsgt38.fsgt38;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
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

	public static final String PARAM_DUREE_SAISIE = "DUREE_SAISIE";

	private static final String PREF_EQUIPES_PREFEREES = "equipes";
	private static final String PREF_REMEMBER_ME = "cookie_remmeber_me";
	private static final String PREF_ASTUCE_ROTATION = "astuce_rotation";

	private static final String BAK_SESSION_ID = "bak_session_id";
	private static final String BAK_EQUIPE = "bak_equipe";
	private static final String BAK_DUREE_SAISIE = "bak_duree_saisie";


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

	/** Durée pendant laquelle la saisie des résultats est autorisée */
	@Setter
	@Getter
	private static Integer dureeSaisie;


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
	//    Sauvegarde / Restauration
	// ----------------------------------------------------------------------------------------

	/**
	 * Sauvegarde l'état courant de l'application
	 * @param outState Bundle de sauvegarde
	 */
	public static void onSaveInstanceState(Bundle outState) {
		outState.putString(BAK_SESSION_ID, sessionId);
		outState.putSerializable(BAK_EQUIPE, equipe);
		outState.putSerializable(BAK_DUREE_SAISIE, dureeSaisie);
	}

	/**
	 * Restaure l'état courant de l'application
	 * @param savedInstanceState Bundle de sauvegarde
	 */
	public static void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState == null) return;;

		if (savedInstanceState.containsKey(BAK_SESSION_ID))
			sessionId = savedInstanceState.getString(BAK_SESSION_ID);
		if (savedInstanceState.containsKey(BAK_EQUIPE))
			equipe = (Equipe) savedInstanceState.getSerializable(BAK_EQUIPE);
		if (savedInstanceState.containsKey(BAK_DUREE_SAISIE))
			dureeSaisie = (Integer) savedInstanceState.getSerializable(BAK_DUREE_SAISIE);
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
