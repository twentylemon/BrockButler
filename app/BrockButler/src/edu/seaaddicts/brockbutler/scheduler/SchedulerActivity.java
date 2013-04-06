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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.animation.ExpandAnimation;
import edu.seaaddicts.brockbutler.coursemanager.Course;
import edu.seaaddicts.brockbutler.coursemanager.CourseHandler;
import edu.seaaddicts.brockbutler.coursemanager.Offering;
import edu.seaaddicts.brockbutler.coursemanager.OfferingTime;
import edu.seaaddicts.brockbutler.help.HelpActivity;

public class SchedulerActivity extends Activity {
	private static final String TAG = "SchedulerActivity";

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

		// Get current course info.
		String subj = mRegisteredCoursesList.get(position).mSubject;
		String code = mRegisteredCoursesList.get(position).mCode;
		String offering = "";
		ArrayList<Offering> offs = mRegisteredCoursesList.get(position).mOfferings;
		ArrayList<OfferingTime> offTimes;

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(toolbar,
				ExpandAnimation.ANIMATE_SHORT);

		// Start the animation on the toolbar
		toolbar.startAnimation(expandAni);

		((TextView) view.findViewById(R.id.tv_prof_name))
				.setText(mRegisteredCoursesList.get(position).mInstructor);

		// Add Offerings registered for.
		for (int i = 0; i < offs.size(); i++) {
			String what = offs.get(i).mType.substring(0, 3).trim();

			offTimes = offs.get(i).mOfferingTimes;

			offering = "";

			// Loop through OfferingTimes for each Offering to populate
			for (int j = 0; j < offTimes.size(); j++) {
				offering += offTimes.get(j).mDay + " "
						+ offTimes.get(j).mStartTime + " - "
						+ offTimes.get(j).mEndTime + " @ "
						+ offTimes.get(j).mLocation + "\n";

				// Check for type of Offering and add as appropriate
				if (what.equalsIgnoreCase("lec")) {
					TextView tv = ((TextView) view
							.findViewById(R.id.tv_lecture));
					tv.setText(offering);
				}

				else if (what.equalsIgnoreCase("lab"))
					((TextView) view.findViewById(R.id.tv_lab))
							.setText(offering);

				else if (what.equalsIgnoreCase("tut"))
					((TextView) view.findViewById(R.id.tv_tutorial))
							.setText(offering);

				else if (what.equalsIgnoreCase("sem"))
					((TextView) view.findViewById(R.id.tv_seminar))
							.setText(offering);
			}

			/*
			 * Hide class type if none available
			 */
			if (((TextView) view.findViewById(R.id.tv_lecture)).getText()
					.toString().equalsIgnoreCase("none"))
				view.findViewById(R.id.row_lec).setVisibility(GONE);
			if (((TextView) view.findViewById(R.id.tv_lab)).getText()
					.toString().equalsIgnoreCase("none"))
				view.findViewById(R.id.row_lab).setVisibility(GONE);
			if (((TextView) view.findViewById(R.id.tv_tutorial)).getText()
					.toString().equalsIgnoreCase("none"))
				view.findViewById(R.id.row_tut).setVisibility(GONE);
			if (((TextView) view.findViewById(R.id.tv_seminar)).getText()
					.toString().equalsIgnoreCase("none"))
				view.findViewById(R.id.row_sem).setVisibility(GONE);

			/*
			 * Add Tasks to view
			 */
			ArrayList<Task> tasks = mRegisteredCoursesList.get(position).mTasks;
			if (tasks.size() > 0) {
				// Set visibility for Tasks
				LinearLayout tasks_layout = (LinearLayout) findViewById(R.id.sched_tasks);
				TextView task_title = (TextView) findViewById(R.id.sched_tasks_title);
				task_title.setVisibility(VISIBLE);
				tasks_layout.setVisibility(VISIBLE);
				tasks_layout.removeAllViews();
				for (int j = 0; j < tasks.size(); j++) {
					// LinearLayout for each task
					LinearLayout ll = new LinearLayout(this);
					//ll.setOrientation(1);
					TextView tv = new TextView(this);
					tv.setPadding(20, 20, 50, 20);
					tv.setLayoutParams(new LinearLayout.LayoutParams(
							500,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					tv.setText(tasks.get(j).mName + "\n\tDue: "
							+ tasks.get(j).mDueDate);
					CheckBox cb = new CheckBox(this);
					cb.setPadding(50, 0, 0, 0);
					cb.setFocusable(false);
					cb.setFocusableInTouchMode(false);
					ll.addView(tv);
					ll.addView(cb);
					tasks_layout.addView(ll);
				}
			}
		}

		Log.d(TAG, "NUMBER TASKS: "
				+ mRegisteredCoursesList.get(position).mTasks.size());

		for (int i = 0; i < mRegisteredCoursesList.get(position).mTasks.size(); i++) {
			Log.d(TAG,
					"Title: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mName
							+ "\nPriority: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mPriority
							+ "\nDue Date: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mDueDate
							+ "\nWeight: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mWeight
							+ "\nBase: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mBase
							+ "\nMark: "
							+ mRegisteredCoursesList.get(position).mTasks
									.get(i).mMark);
		}

		((TextView) view.findViewById(R.id.tv_prof_name))
				.setText(mRegisteredCoursesList.get(position).mInstructor);

	}

	public void showHelp(MenuItem item) {
		Intent intent = new Intent(SchedulerActivity.this, HelpActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("activity", "scheduler");
		intent.putExtras(bundle);
		startActivity(intent);
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
