package edu.seaaddicts.brockbutler.coursemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.seaaddicts.brockbutler.R;

public class CourseManagerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coursemanager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_coursemanager, menu);
		return true;
	}

}
