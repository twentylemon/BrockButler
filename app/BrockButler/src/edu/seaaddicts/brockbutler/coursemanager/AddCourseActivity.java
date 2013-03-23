package edu.seaaddicts.brockbutler.coursemanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;

public class AddCourseActivity extends Activity {
	private static final int DATE_DIALOG_ID = 0;

	private Button mSaveButton;
	private Button mCancelButton;
	private TextView mDueDateTextView;
	
	private CourseHandler mCourseHandle;
	
	private Spinner mSubjectSpinner;
	private Spinner mCodesSpinner;

	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		mCourseHandle = new CourseHandler(this.getApplicationContext());
		init();
	}

	/*
	 * Initialize all views and sets Button OnClickListeners.
	 */
	private void init() {
		
		mSaveButton = (Button) findViewById(R.id.add_course_save_button);
		mCancelButton = (Button) findViewById(R.id.add_course_cancel_button);
		
		mSubjectSpinner = (Spinner) findViewById(R.id.add_course_subjects_spinner);
		try {
			mSubjectSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mCourseHandle.getSubjects()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mCodesSpinner = (Spinner) findViewById(R.id.add_course_codes_spinner);
		mSubjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try {
					String sub = arg0.getItemAtPosition(arg2).toString();
					mCodesSpinner.setAdapter(new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_dropdown_item, mCourseHandle.getCodes(sub)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing.
			}
		});
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Run Grisdale's function to add a task to the database.
				onBackPressed();
			}
		});

		
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Do nothing.
				onBackPressed();
			}
		});
	}
}
