package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;

public class CourseManagerActivity extends Activity {
	
	private CourseHandler mCourseHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coursemanager);
		mCourseHandle = new CourseHandler(this.getApplicationContext());
		//mCourseHandle.getAllCourses();
//
//		ArrayList<Course> regcourses = mCourseHandle.getRegisteredCourses();
//        ListView list = (ListView) findViewById(R.id.courseman_list);
//
//        // Creating the list adapter and populating the list
//        ArrayAdapter<String> listAdapter = new CustomListAdapter(this, R.layout.course_list_item);
//        for (int i = 0; i < regcourses.size(); i++)
//            listAdapter.add(regcourses.get(i).mSubject + " " + regcourses.get(i).mCode);
//        list.setAdapter(listAdapter);
//
//        // Creating an item click listener, to open/close our toolbar for each item
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//
//                View toolbar = view.findViewById(R.id.toolbar);
//
//                // Creating the expand animation for the item
//                ExpandAnimation expandAni = new ExpandAnimation(toolbar, ExpandAnimation.ANIMATE_SHORT);
//
//                // Start the animation on the toolbar
//                toolbar.startAnimation(expandAni);
//            }
//        });
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

	public void addCourse(MenuItem menu) {
		Intent i = new Intent(CourseManagerActivity.this, AddCourseActivity.class);
        startActivity(i);
	}

}
