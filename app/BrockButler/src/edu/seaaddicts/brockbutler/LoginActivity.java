package edu.seaaddicts.brockbutler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private Button mBtnLogin;
	private Button mBtnSkip;
	private CheckBox mChkRemember;
	private CheckBox mChkSkip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Exit application?");
		alertDialogBuilder
				.setMessage(
						"Are you sure you want to exit and close BrockButler?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								LoginActivity.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	// Assigns Buttons and Checkboxes, as well as their functionalities, i.e.
	// OnClickListeners.
	public void init() {

		mBtnLogin = (Button) findViewById(R.id.btn_login_login);
		mBtnSkip = (Button) findViewById(R.id.btn_login_skip);
		
		mChkRemember = (CheckBox) findViewById(R.id.chk_login_remember);
		mChkSkip = (CheckBox) findViewById(R.id.chk_login_skip);
		
		mBtnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
			}
		});
		mBtnSkip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView messageText = new TextView(LoginActivity.this);
				messageText.setText(R.string.msg_skip);
				messageText.setGravity(Gravity.FILL);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LoginActivity.this);
				alertDialogBuilder.setTitle("Skip login?");
				alertDialogBuilder
						.setView(messageText)
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										Intent i = new Intent(LoginActivity.this, MainActivity.class);
					                    startActivity(i);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				messageText.setGravity(Gravity.CENTER);
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

}
