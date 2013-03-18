package com.excerebros.locationtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class TestHarness extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_harness);
		
		Context contextNew = this;
		Astar a = new Astar(contextNew);
		
		a.printTable();
		a.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test_harness, menu);
		return true;
	}

}
