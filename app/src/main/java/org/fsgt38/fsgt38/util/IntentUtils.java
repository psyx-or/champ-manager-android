package org.fsgt38.fsgt38.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import org.fsgt38.fsgt38.R;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * Fonctions permettant de faire le lien avec d'autres applications
 */
public class IntentUtils
{
	/**
	 * Ouverture d'un fichier PDF
	 * @param context Le contexte
	 * @param fichier Le fichier à ouvrir
	 */
	public static boolean ouvrePDF(Context context, File fichier)
	{
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(FileProvider.getUriForFile(
					context,
					context.getApplicationContext().getPackageName() + ".provider",
					fichier), "application/pdf");
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
			context.startActivity(intent);
			return true;
		}
		catch (ActivityNotFoundException e) {
			new AlertDialog.Builder(context)
					.setTitle(R.string.erreur)
					.setMessage(R.string.erreur_pdf)
					.setPositiveButton(android.R.string.yes, null)
					.show();
			return false;
		}
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
			intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
					activity,
					activity.getApplicationContext().getPackageName() + ".provider",
					fichier));
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
