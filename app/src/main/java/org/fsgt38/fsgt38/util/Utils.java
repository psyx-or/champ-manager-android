package org.fsgt38.fsgt38.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.fsgt38.fsgt38.R;

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

	/**
	 * Création du sablier d'attente
	 * @param context Le contexte
	 * @param listener L'objet à appeler en cas d'annulation
	 * @return Le sablier
	 */
	public static ProgressDialog creeWaiter(Context context, DialogInterface.OnCancelListener listener)
	{
		return ProgressDialog.show(
				context,
				context.getString(R.string.app_name),
				context.getString(R.string.chargement),
				true,
				true,
				listener);
	}
}
