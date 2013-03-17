package edu.seaaddicts.brockbutler.scheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.AddTaskActivity;
import edu.seaaddicts.brockbutler.MainActivity;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.animation.ExpandAnimation;
import edu.seaaddicts.brockbutler.coursemanager.Course;
import edu.seaaddicts.brockbutler.coursemanager.CourseManagerActivity;
import edu.seaaddicts.brockbutler.help.HelpActivity;

public class SchedulerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);

        ListView list = (ListView) findViewById(R.id.course_list);

        // Creating the list adapter and populating the list
        ArrayAdapter<String> listAdapter = new CustomListAdapter(this, R.layout.sched_list_item);
        for (int i = 0; i < 4; i++)
            listAdapter.add("COSC "+ (i+1) + "P0" + i);
        list.setAdapter(listAdapter);

        // Creating an item click listener, to open/close our toolbar for each item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                View toolbar = view.findViewById(R.id.toolbar);

                // Creating the expand animation for the item
                ExpandAnimation expandAni = new ExpandAnimation(toolbar, ExpandAnimation.ANIMATE_SHORT);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scheduler, menu);
		return true;
	}
	
	public void addTask(MenuItem item) {
		Intent i = new Intent(SchedulerActivity.this, AddTaskActivity.class);
        startActivity(i);
	}
	public void showHelp(MenuItem item)
	{
		Intent intent = new Intent(SchedulerActivity.this,HelpActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("activity", "scheduler");
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	private ArrayList<Course> getCourses() {
		return null;
		
	}
	
	private void expandAll(View [] v) {
		
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
                convertView = getLayoutInflater().inflate(R.layout.sched_list_item, null);
            }

            ((TextView)convertView.findViewById(R.id.title)).setText(getItem(position));

            // Resets the toolbar to be closed
            View toolbar = convertView.findViewById(R.id.toolbar);
            ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
            toolbar.setVisibility(View.GONE);

            return convertView;
        }
    }

}
