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
import android.widget.Toast;
import edu.seaaddicts.brockbutler.R;

public class AddCourseActivity extends Activity {
	private static final int DATE_DIALOG_ID = 0;
	
	private String mSubject;
	private String mCode;

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
					mSubject = arg0.getItemAtPosition(arg2).toString();
					mCodesSpinner.setAdapter(new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_dropdown_item, mCourseHandle.getCodes(mSubject)));
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
		
		mCodesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mCode = arg0.getItemAtPosition(arg2).toString();
				mCodesSpinner.setAdapter(new ArrayAdapter<String>(AddCourseActivity.this, android.R.layout.simple_spinner_dropdown_item, mCourseHandle.getCodes(mSubject)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Course c = new Course();
				c.mSubject = mSubject;
				c.mCode = mCode;
				try {
					mCourseHandle.addCourse(c);
					Toast.makeText(getApplicationContext(), c.mSubject + " " + c.mCode + " added!", Toast.LENGTH_LONG).show();
					onBackPressed();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
