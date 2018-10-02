package org.fsgt38.fsgt38.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Classement;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

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

	/**
	 * Trie les classements d'un championnat
	 * @param championnat Championnat
	 */
	public static void trieClassements(Championnat championnat) {
		Arrays.sort(championnat.getClassements(), new Comparator<Classement>() {
			@Override
			public int compare(Classement o1, Classement o2) {
				if (o1.getPosition() == o2.getPosition())
					return o1.getEquipe().getNom().compareTo(o2.getEquipe().getNom());
				else
					return o1.getPosition() - o2.getPosition();
			}
		});
	}
}
