package edu.seaaddicts.brockbutler.scheduler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.coursemanager.Course;

public class SchedulerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scheduler, menu);
		return true;
	}
	
	private Course [] getCourses() {
		return null;
		
	}
	
	private void expandAll(View [] v) {
		
	}

}
