package org.fsgt38.fsgt38.util;

import java.util.Calendar;

/**
 * Méthodes outil
 */
public class Utils {

	/**
	 * @return La saison en cours
	 */
	public static String getSaison() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) < 8 ?
				(now.get(Calendar.YEAR) - 1) + " / " + now.get(Calendar.YEAR) :
				now.get(Calendar.YEAR) + " / " + (now.get(Calendar.YEAR) + 1);
	}
}
