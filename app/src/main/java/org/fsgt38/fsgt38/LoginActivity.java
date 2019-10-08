package org.fsgt38.fsgt38;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import org.fsgt38.fsgt38.model.Equipe;
import org.fsgt38.fsgt38.rest.AuthentService;
import org.fsgt38.fsgt38.util.ApiUtils;
import org.fsgt38.fsgt38.util.FSGT38PopupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class LoginActivity extends FSGT38PopupActivity {

	// ----------------------------------------------------------------------------------------
	//    Membres
	// ----------------------------------------------------------------------------------------

	@BindView(R.id.txtLogin)	TextInputEditText txtLogin;
	@BindView(R.id.txtMdp)  	TextInputEditText txtMdp;


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

		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
	}

	/**
	 * Lance l'authentification
	 */
	@OnClick(R.id.btnAuthentifie)
	public void authentifie() {
		Retrofit retrofit = ApiUtils.getApi(this);
		ApiUtils.appel(
				this,
				retrofit.create(AuthentService.class).authentifie(txtLogin.getText().toString(), txtMdp.getText().toString(), "on"),
				new ApiUtils.Action<Equipe>() {
					@Override
					public void action(Equipe equipe) {
						if (equipe == null) {
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									new AlertDialog.Builder(LoginActivity.this)
											.setTitle(R.string.erreur)
											.setMessage(R.string.erreur_login)
											.setPositiveButton(android.R.string.yes, null)
											.show();
								}
							});

						}
						else {
							FSGT38Application.setEquipe(equipe);
							Intent intent = new Intent(LoginActivity.this, ResultatsActivity.class);
							startActivity(intent);
							finish();
						}
					}
				}
		);
	}
}
