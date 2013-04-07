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

public class SchedulerActivityBAK extends Activity {
	private static final String TAG = "SchedulerActivity";

	private static final int VISIBLE = 0;
	private static final int GONE = 8;

	private ArrayList<Course> mRegisteredCoursesList = null;
	private CourseHandler mCourseHandle = null;
	private ListView mRegisteredCoursesListView = null;
	ArrayAdapter<String> mListAdaptor;

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
			mListAdaptor = new CustomListAdapter(this, R.layout.sched_list_item);
			for (int i = 0; i < mRegisteredCoursesList.size(); i++)
				mListAdaptor.add(mRegisteredCoursesList.get(i).mSubject + " "
						+ mRegisteredCoursesList.get(i).mCode);
			mRegisteredCoursesListView.setAdapter(mListAdaptor);
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
		populateCourseViews();
	}

	private void populateCourseViews() {
		LinearLayout convert = (LinearLayout) findViewById(R.id.sched_list_item);
		for (int m = 0; m < mListAdaptor.getCount(); m++) {

			View view = mListAdaptor.getView(m, convert,
					mRegisteredCoursesListView);

			// Get current course info.
			String offering = "";
			ArrayList<Offering> offs = mRegisteredCoursesList.get(m).mOfferings;
			ArrayList<OfferingTime> offTimes;
			ArrayList<Task> tasks = mRegisteredCoursesList.get(m).mTasks;
			Log.d(TAG, "NUMBER OF TASKS: " + tasks.size());

			// Set Instructor
			((TextView) view.findViewById(R.id.tv_prof_name))
					.setText(mRegisteredCoursesList.get(m).mInstructor);

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
						((TextView) view.findViewById(R.id.tv_lecture))
								.setText(offering);
					}

					// ...a lab?
					else if (what.equalsIgnoreCase("lab"))
						((TextView) view.findViewById(R.id.tv_lab))
								.setText(offering);

					// ..a tutorial?
					else if (what.equalsIgnoreCase("tut"))
						((TextView) view.findViewById(R.id.tv_tutorial))
								.setText(offering);

					// ...a seminar?
					else if (what.equalsIgnoreCase("sem"))
						((TextView) view.findViewById(R.id.tv_seminar))
								.setText(offering);
				}
			}
			view.findViewById(R.id.sched_tasks_title).setVisibility(VISIBLE);

			/*
			 * Add Tasks to view
			 */
			if (tasks.size() > 0) {
				// Set visibility for Tasks
				view.findViewById(R.id.sched_tasks).setVisibility(VISIBLE);
				((LinearLayout) view.findViewById(R.id.sched_tasks))
						.removeAllViews();
				for (int j = 0; j < tasks.size(); j++) {
					// LinearLayout for each task
					LinearLayout ll = new LinearLayout(this);
					TextView tv = new TextView(this);
					tv.setPadding(20, 20, 50, 20);
					tv.setLayoutParams(new LinearLayout.LayoutParams(500,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					tv.setText(tasks.get(j).mName + "\n\tDue: "
							+ tasks.get(j).mDueDate);
					CheckBox cb = new CheckBox(this);
					cb.setPadding(50, 0, 0, 0);
					cb.setFocusable(false);
					cb.setFocusableInTouchMode(false);
					ll.addView(tv);
					ll.addView(cb);
					// tasks_layout.addView(ll);
				}
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
		}
	}

	private void showHideToolbar(View view, int position) {
		View toolbar = view.findViewById(R.id.sched_toolbar);

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(toolbar,
				ExpandAnimation.ANIMATE_SHORT);

		// Start the animation on the toolbar
		toolbar.startAnimation(expandAni);

	}

	public void showHelp(MenuItem item) {
		Intent intent = new Intent(SchedulerActivityBAK.this,
				HelpActivity.class);
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
			Toast.makeText(SchedulerActivityBAK.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void addTask(MenuItem item) {
		Intent i = new Intent(SchedulerActivityBAK.this, AddTaskActivity.class);
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
