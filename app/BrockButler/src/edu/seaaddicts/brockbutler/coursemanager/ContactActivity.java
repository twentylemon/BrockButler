package edu.seaaddicts.brockbutler.coursemanager;

import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.R.layout;
import edu.seaaddicts.brockbutler.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_contact, menu);
		return true;
	}

}
