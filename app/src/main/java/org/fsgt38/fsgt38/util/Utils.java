package org.fsgt38.fsgt38.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import org.fsgt38.fsgt38.R;
import org.fsgt38.fsgt38.model.Championnat;
import org.fsgt38.fsgt38.model.Classement;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import androidx.exifinterface.media.ExifInterface;

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
		ProgressDialog dialog = ProgressDialog.show(
				context,
				context.getString(R.string.app_name),
				context.getString(R.string.chargement),
				true,
				true,
				listener);
		dialog.setCancelable(false);
		return dialog;
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

	/**
	 * Affiche automatiquement le clavier
	 * @param context Le contexte
	 * @param view La zone pour laquelle le clavier est actif
	 */
	public static void montreClavier(final Context context, final View view)
	{
		view.post(new Runnable()
		{
			@SuppressWarnings("ConstantConditions")
			@Override
			public void run()
			{
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
			}
		});
	}

	/**
	 * Affiche une bitmap dans un {@link ImageView}.
	 * <p/>
	 * L'affichage se fait en utilisant le moins de mémoire possible en tenant compte de la taille du conteneur.
	 * @param view Le conteneur de l'image
	 * @param fichier Le fichier contenant le fichier
	 */
	public static void setBitmap(ImageView view, File fichier)
	{
		// Lecture de la bitmap
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fichier.getAbsolutePath(), options);

		options.inSampleSize = calculateInSampleSize(options, view.getMinimumWidth(), view.getMinimumHeight());

		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(fichier.getAbsolutePath(), options);

		// Rotation de la bitmap si nécessaire
		try
		{
			int degree = 0;
			ExifInterface exif = new ExifInterface(fichier.getAbsolutePath());
			switch (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1))
			{
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
			if (degree!=0)
			{
				Matrix matrix = new Matrix();
				matrix.postRotate(degree);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// On définit la bitmap
		view.setImageBitmap(bitmap);
	}

	/**
	 * Calcul de l'échelle à appliquer à une image pour être affichée entièrement dans l'écran
	 * @param options Dimensions de l'image
	 * @param reqWidth Largeur finale
	 * @param reqHeight Hauteur finale
	 * @return Le ratio à appliquer
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth)
			{
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device density.
	 *
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static int convertDpToPixel(float dp, Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return Math.round(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
