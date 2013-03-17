package edu.seaaddicts.brockbutler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.seaaddicts.brockbutler.coursemanager.CourseManagerActivity;
import edu.seaaddicts.brockbutler.help.HelpActivity;
import edu.seaaddicts.brockbutler.maps.MapsActivity;
import edu.seaaddicts.brockbutler.scheduler.SchedulerActivity;
import edu.seaaddicts.brockbutler.tour.TourActivity;

public class MainActivity extends Activity {
	
	private Button mSchedulerButton;
	private Button mCourseManager;
	private Button mMapsButton;
	private Button mTourButton;
	private Button mHelpButton;
	View v[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init()
	{
		// Instantiate Buttons
		mSchedulerButton = (Button) findViewById(R.id.btn_sched);
		mCourseManager = (Button) findViewById(R.id.btn_contacts);
		mMapsButton 	= (Button) findViewById(R.id.btn_maps);
		mTourButton 	= (Button) findViewById(R.id.btn_tour);
		mHelpButton 	= (Button) findViewById(R.id.btn_help);
		
		// Set OnClickListeners
		mSchedulerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SchedulerActivity.class);
                startActivity(i);
			}
		});
		mCourseManager.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CourseManagerActivity.class);
                startActivity(i);
			}
		});
		mMapsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
			}
		});
		mTourButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, TourActivity.class);
                startActivity(i);
			}
		});
		mHelpButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, HelpActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("activity", "main");
				i.putExtras(bundle);
                startActivity(i);
			}
		});
		
	}	
}
