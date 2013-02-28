package edu.seaaddicts.brockbutler.tour;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.seaaddicts.brockbutler.R;

public class TourActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour, menu);
		return true;
	}

}
