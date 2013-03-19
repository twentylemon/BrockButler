package edu.seaaddicts.brockbutler.scheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class SchedulerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);

		ListView list = (ListView) findViewById(R.id.sched_list);

		// Creating the list adapter and populating the list
		ArrayAdapter<String> listAdapter = new CustomListAdapter(this,
				R.layout.sched_list_item);
		for (int i = 0; i < 4; i++)
			listAdapter.add("COSC " + (i + 1) + "P0" + i);
		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				showHideToolbar(view);
			}
		});
	}

	private void showHideToolbar(View view) {
		View toolbar = view.findViewById(R.id.toolbar);

		// Creating the expand animation for the item
		ExpandAnimation expandAni = new ExpandAnimation(toolbar,
				ExpandAnimation.ANIMATE_SHORT);

		// Start the animation on the toolbar
		toolbar.startAnimation(expandAni);

		((Button) view.findViewById(R.id.prof_email_button))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						sendEmail();
					}
				});
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
			((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -(toolbar
					.getHeight());
			toolbar.setVisibility(View.GONE);

			return convertView;
		}
	}

}
