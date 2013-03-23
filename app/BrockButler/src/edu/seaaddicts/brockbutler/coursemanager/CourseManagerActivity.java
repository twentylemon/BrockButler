package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.animation.ExpandAnimation;

public class CourseManagerActivity extends Activity {

	private static final int VISIBLE = 0;
	private static final int GONE = 8;

	private ArrayList<Course> mRegisteredCoursesList;

	private CourseHandler mCourseHandle = null;

	private LinearLayout mLayout = null;
	private ListView mRegisterCourseListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coursemanager);
		mCourseHandle = new CourseHandler(this.getApplicationContext());

		updateCourseDatabaseFromRegistrar();
		//

		//
		// // Creating an item click listener, to open/close our toolbar for
		// each item
		// list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, final View view, int
		// position, long id) {
		//
		// View toolbar = view.findViewById(R.id.toolbar);
		//
		// // Creating the expand animation for the item
		// ExpandAnimation expandAni = new ExpandAnimation(toolbar,
		// ExpandAnimation.ANIMATE_SHORT);
		//
		// // Start the animation on the toolbar
		// toolbar.startAnimation(expandAni);
		// }
		// });
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateCoursesLayout();
	}

	/**
	 * 
	 */
	private void populateCoursesLayout() {
		TextView tvNoCourses = (TextView) findViewById(R.id.tv_no_courses);
		mRegisterCourseListView = (ListView) findViewById(R.id.courseman_list);
		mRegisteredCoursesList = mCourseHandle.getRegisteredCourses();
		if (mRegisteredCoursesList.size() < 1) {
			// There are no registered courses so set message.
			tvNoCourses.setVisibility(VISIBLE);
		} else {
			// We have registered courses so populate ListView.
			mRegisterCourseListView = (ListView) findViewById(R.id.courseman_list);
			// Creating the list adapter and populating the list
			ArrayAdapter<String> listAdapter = new CustomListAdapter(this,
					R.layout.course_list_item);
			for (int i = 0; i < mRegisteredCoursesList.size(); i++)
				listAdapter.add(mRegisteredCoursesList.get(i).mSubject + " "
						+ mRegisteredCoursesList.get(i).mCode);
			mRegisterCourseListView.setAdapter(listAdapter);
			tvNoCourses.setVisibility(GONE);
			mRegisterCourseListView.setVisibility(VISIBLE);
			mRegisterCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					showHideToolbar(view);
				}
			});
		}
	}
	
	private void showHideToolbar(View view) {
		View toolbar = view.findViewById(R.id.toolbar);

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(toolbar,
				ExpandAnimation.ANIMATE_SHORT);

		// Start the animation on the toolbar
		toolbar.startAnimation(expandAni);

//		((Button) view.findViewById(R.id.prof_email_button))
//				.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						sendEmail();
//					}
//				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_coursemanager, menu);
		return true;
	}

	private ArrayList<Course> getCourses() {
		return null;

	}

	private void expandAll(View[] v) {

	}

	/**
	 * A simple implementation of list adapter.
	 */
	class CustomListAdapter extends ArrayAdapter<String> {

		public CustomListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.sched_list_item, null);
			}

			((TextView) convertView.findViewById(R.id.sched_list_item_title))
					.setText(getItem(position));

			// Resets the toolbar to be closed
			View toolbar = convertView.findViewById(R.id.toolbar);
			((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
			toolbar.setVisibility(View.GONE);

			return convertView;
		}
	}

	public void addCourse(MenuItem menu) {
		Intent i = new Intent(CourseManagerActivity.this,
				AddCourseActivity.class);
		startActivity(i);
	}

	private void updateCourseDatabaseFromRegistrar() {
		final Handler handler = new Handler();
		final ProgressDialog progressDialog;

		TextView title = new TextView(CourseManagerActivity.this);
		title.setText(R.string.loading_courses_registrar);
		title.setGravity(Gravity.FILL);

		TextView msg = new TextView(CourseManagerActivity.this);
		msg.setText(R.string.loading_courses_registrar_msg);
		msg.setGravity(Gravity.FILL);

		progressDialog = ProgressDialog.show(this, "Course Timetable Update",
				"Updating course timetable from registrar. Please be patient.");

		Thread thread = new Thread() {
			public void run() {
				mCourseHandle.getAllCourses();
				// this will handle the post task.
				// it will run when the time consuming task get finished
				handler.post(new Runnable() {
					@Override
					public void run() {

						// Update your UI or
						// do any Post job after the time consuming task
						// remember to dismiss the progress dialog here.
						// updateUI();
						progressDialog.dismiss();
						populateCoursesLayout();
					}
				});
			}
		};
		thread.start();
	}

}
