package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

import org.fsgt38.fsgt38.rest.MatchesService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;
import org.fsgt38.fsgt38.util.IntentUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Affichage d'une image
 */
public class ImageActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Constantes
	// ----------------------------------------------------------------------------------------

	public static final String KEY_FICHIER = ImageActivity.class.getName() + ".fichier";


	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	/** Le conteneur de l'image */
	@BindView(R.id.photoView)	public PhotoView image;

	/** Nom du fichier à télécharger */
	private String fichier;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		fichier = getIntent().getStringExtra(KEY_FICHIER);

		setContentView(R.layout.activity_image);
		ButterKnife.bind(this);

		// Chargement de l'image
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(MatchesService.class).getFeuille(fichier),
				new ApiUtils.Action<ResponseBody>() {
					@Override
					public void action(ResponseBody feuille) {
						afficheImage(feuille.byteStream());
					}
				}
		);
	}


	// ----------------------------------------------------------------------------------------
	//    Méthodes
	// ----------------------------------------------------------------------------------------

	/**
	 * Affichage de l'image reçue
	 * @param feuille Iage à afficher
	 */
	private void afficheImage(InputStream feuille) {
		if (fichier.toLowerCase().endsWith(".pdf")) {
			try {
				File fic = new File(getCacheDir(), "Feuille de match.pdf");
				fic.deleteOnExit();

				// On enregistre l'image dans un fichier
				OutputStream output = new FileOutputStream(fic);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = feuille.read(buffer, 0, buffer.length)) >= 0)
					output.write(buffer, 0, bytesRead);
				output.close();

				// On lance le lecteur de PDF
				if (IntentUtils.ouvrePDF(this, fic))
					finish();
			}
			catch (final IOException e) {
				// Gestion des erreurs
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						new AlertDialog.Builder(ImageActivity.this)
								.setTitle(R.string.erreur)
								.setMessage(e.getLocalizedMessage())
								.setPositiveButton(android.R.string.yes, null)
								.show();
					}
				});
			}
		}
		else {
			// Image clasique (a priori) => on l'affiche directement
			image.setImageBitmap(BitmapFactory.decodeStream(feuille));
		}
	}
}
