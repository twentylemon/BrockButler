package edu.seaaddicts.brockbutler.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.seaaddicts.brockbutler.R;

public class AddContactActivity extends Activity {

	private Button mSaveButton;
	private Button mCancelButton;
	//private Contact mContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		init();
	}

	/*
	 * Initialize all views and sets Button OnClickListeners.
	 */
	private void init() {
		mSaveButton = (Button) findViewById(R.id.add_contact_save_button);
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// Grisdale add contact
				onBackPressed();
			}
		});
		mCancelButton = (Button) findViewById(R.id.add_contact_cancel_button);
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
