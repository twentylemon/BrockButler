package edu.seaaddicts.brockbutler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_task, menu);
		return true;
	}

}
