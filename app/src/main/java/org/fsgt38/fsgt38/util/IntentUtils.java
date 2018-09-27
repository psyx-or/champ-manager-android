package org.fsgt38.fsgt38.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Fonctions permettant de faire le lien avec d'autres applications
 */
public class IntentUtils
{
	/**
	 * Ouverture d'un fichier PDF
	 * @param context Le contexte
	 * @param fic Le fichier à ouvrir (doit être accessible publiquement)
	 */
	public static void ouvrePDF(Context context, File fic)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(fic), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		context.startActivity(intent);
	}

	/**
	 * Compose un numéro
	 * @param context Le contexte
	 * @param numero Le numéro à appeler
	 */
	public static void ouvreTelephone(Context context, String numero)
	{
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.fromParts("tel",numero, null));
		context.startActivity(intent);
	}

	/**
	 * Envoi d'un nouveau mail
	 * @param context Le contexte
	 * @param destinataire Le destinataire du mail
	 */
	public static void ouvreMail(Context context, String destinataire)
	{
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.fromParts("mailto", destinataire, null));
		context.startActivity(intent);
	}

	/**
	 * Ouverture de l'appareil photo
	 * <p/>
	 * L'activité recevra un événement {@link Activity#onActivityResult} lorsque la photo sera prise (ou non)
	 *
	 * @param activity L'activité en cours
	 * @param fichier Le fichier dans lequel écrire la photo
	 * @return Vrai si l'appareil dispose d'un appareil photo, faux sinon
	 */
	public static boolean ouvreAppareilPhoto(Activity activity, File fichier)
	{
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(activity.getPackageManager()) != null)
		{
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fichier));
			activity.startActivityForResult(intent, 0);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Affiche une position dans Google maps
	 * @param context L'activité en cours
	 * @param coordonnees Coordonnées de destination
	 */
	public static void ouvreGMaps(Context context, String coordonnees)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://maps.google.com/maps?q=" + coordonnees));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}