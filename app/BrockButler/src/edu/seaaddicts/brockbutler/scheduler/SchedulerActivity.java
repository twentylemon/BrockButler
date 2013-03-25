package edu.seaaddicts.brockbutler.scheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.animation.ExpandAnimation;
import edu.seaaddicts.brockbutler.coursemanager.Course;
import edu.seaaddicts.brockbutler.coursemanager.CourseHandler;

public class SchedulerActivity extends Activity {

	private static final int VISIBLE = 0;
	private static final int GONE = 8;

	private ArrayList<Course> mRegisteredCoursesList = null;
	private CourseHandler mCourseHandle = null;
	private ListView mRegisteredCoursesListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);
		mCourseHandle = new CourseHandler(this);
		populateCoursesLayout();
	}

	/**
	 * 
	 */
	private void populateCoursesLayout() {
		TextView tvNoCourses = (TextView) findViewById(R.id.tv_no_courses);
		mRegisteredCoursesListView = (ListView) findViewById(R.id.sched_list);
		mRegisteredCoursesList = mCourseHandle.getRegisteredCourses();
		if (mRegisteredCoursesList.size() == 0) {
			// There are no registered courses so set message.
			mRegisteredCoursesListView.setVisibility(GONE);
			tvNoCourses.setVisibility(VISIBLE);
		} else {
			// We have registered courses so populate ListView.
			// Creating the list adapter and populating the list
			ArrayAdapter<String> listAdapter = new CustomListAdapter(this,
					R.layout.course_list_item);
			for (int i = 0; i < mRegisteredCoursesList.size(); i++)
				listAdapter.add(mRegisteredCoursesList.get(i).mSubject + " "
						+ mRegisteredCoursesList.get(i).mCode);
			mRegisteredCoursesListView.setAdapter(listAdapter);
			tvNoCourses.setVisibility(GONE);
			mRegisteredCoursesListView.setVisibility(VISIBLE);
			mRegisteredCoursesListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								final View view, int position, long id) {
							showHideToolbar(view, position);
						}
					});
			registerForContextMenu(mRegisteredCoursesListView);
		}
	}

	private void showHideToolbar(View view, int position) {
		View toolbar = view.findViewById(R.id.sched_toolbar);

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(toolbar,
				ExpandAnimation.ANIMATE_SHORT);

		// Start the animation on the toolbar
		toolbar.startAnimation(expandAni);

		((TextView) view.findViewById(R.id.tv_prof_name))
				.setText(mRegisteredCoursesList.get(position).mInstructor);

		Log.d("NUMBER OFFERINGS:", ""
				+ mRegisteredCoursesList.get(position).mOfferings.size());
		// Add offerings registered for
		for (int i = 0; i < mRegisteredCoursesList.get(position).mOfferings
				.size(); i++) {
			String what = mRegisteredCoursesList.get(position).mOfferings
					.get(i).mType.substring(0, 3).trim();

			Log.d("TYPE: ", "" + what);

			if (what.equalsIgnoreCase("lec"))
				((TextView) view.findViewById(R.id.tv_lecture))
						.setText(mRegisteredCoursesList.get(position).mOfferings
								.get(i).mOfferingTimes.get(i).mDay
								+ " "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mStartTime
								+ " - "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mEndTime
								+ " @ "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mLocation);

			else if (what.equalsIgnoreCase("lab"))
				((TextView) view.findViewById(R.id.tv_lab))
						.setText(mRegisteredCoursesList.get(position).mOfferings
								.get(i).mOfferingTimes.get(i).mDay
								+ " "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mStartTime
								+ " - "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mEndTime
								+ " @ "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mLocation);

			else if (what.equalsIgnoreCase("tut"))
				((TextView) view.findViewById(R.id.tv_tutorial))
						.setText(mRegisteredCoursesList.get(position).mOfferings
								.get(i).mOfferingTimes.get(i).mDay
								+ " "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mStartTime
								+ " - "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mEndTime
								+ " @ "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mLocation);

			else if (what.equalsIgnoreCase("sem"))
				((TextView) view.findViewById(R.id.tv_seminar))
						.setText(mRegisteredCoursesList.get(position).mOfferings
								.get(i).mOfferingTimes.get(i).mDay
								+ " "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mStartTime
								+ " - "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mEndTime
								+ " @ "
								+ mRegisteredCoursesList.get(position).mOfferings
										.get(i).mOfferingTimes.get(i).mLocation);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scheduler, menu);
		return true;
	}

	private void sendEmail() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(SchedulerActivity.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void addTask(MenuItem item) {
		Intent i = new Intent(SchedulerActivity.this, AddTaskActivity.class);
		startActivity(i);
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
			View toolbar = convertView.findViewById(R.id.sched_toolbar);
			((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -(toolbar
					.getHeight());
			toolbar.setVisibility(View.GONE);

			return convertView;
		}
	}

}
