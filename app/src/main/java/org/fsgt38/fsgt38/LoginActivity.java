package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import org.fsgt38.fsgt38.databinding.ActivityLoginBinding;
import org.fsgt38.fsgt38.rest.AuthentService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;

import retrofit2.Retrofit;

public class LoginActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	private ActivityLoginBinding binding;


	// ----------------------------------------------------------------------------------------
	//    Gestion des événements
	// ----------------------------------------------------------------------------------------

	/**
	 * Initialisation de l'écran
	 * @param savedInstanceState paramètres sauvegardés
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityLoginBinding.inflate(getLayoutInflater());
		binding.setActivity(this);
		setContentView(binding.getRoot());
	}

	/**
	 * Lance l'authentification
	 */
	public void authentifie() {

		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(AuthentService.class).authentifie(binding.txtLogin.getText().toString(), binding.txtMdp.getText().toString(), "on"),
				equipe -> {
					if (equipe == null) {
						runOnUiThread(() -> new AlertDialog.Builder(LoginActivity.this)
								.setTitle(R.string.erreur)
								.setMessage(R.string.erreur_login)
								.setPositiveButton(android.R.string.yes, null)
								.show());

					}
					else {
						FSGT38Application.setEquipe(equipe);
						Intent intent = new Intent(LoginActivity.this, ResultatsActivity.class);
						startActivity(intent);
						finish();
					}
				}
		);
	}
}
