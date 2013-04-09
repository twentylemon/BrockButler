package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.contacts.Contact;
import edu.seaaddicts.brockbutler.scheduler.Task;

public class ModifyCourseActivity extends Activity {

	private static final String TAG = "ModifyCourseActivity";

	private static final int DATE_DIALOG_ID = 0;
	private static final int VISIBLE = 0;
	private static final int INVISIBLE = 4;
	private static final int GONE = 8;

	ArrayList<String> mLecs;
	ArrayList<String> mLabs;
	ArrayList<String> mTuts;
	ArrayList<String> mSems;

	ArrayList<Offering> mLecsOfferings;
	ArrayList<Offering> mLabsOfferings;
	ArrayList<Offering> mTutsOfferings;
	ArrayList<Offering> mSemsOfferings;
	
	public String mSubject;
	public String mCode;
	public String mDesc;
	public String mInstructor;
	public String mInstructor_email;
	public ArrayList<Offering> mOfferings;
	public ArrayList<Task> mTasks;
	public ArrayList<Contact> mContacts;

	private Button mSaveButton;
	private Button mCancelButton;

	private CourseHandler mCourseHandle;

	private TextView mSubjectTextView;
	private TextView mCodesTextView;
	
	private Bundle mCourseBundle;
	private Intent mCallingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_course);
//		mThisCourse = (Course) getIntent().getSerializableExtra(CourseManagerActivity.CODE_COURSE_OBJECT);
//		if (mThisCourse == null) {
//			Log.d(TAG, "Course not retrieved.");
//			finish();
//		}
		mCourseHandle = new CourseHandler(this.getApplicationContext());
		init();
	}

	/*
	 * Initialize all views and sets Button OnClickListeners.
	 */
	private void init() {
		// Set Buttons
		mSaveButton = (Button) findViewById(R.id.add_course_save_button);
		mCancelButton = (Button) findViewById(R.id.add_course_cancel_button);

		// Instantiate TextView
		mSubjectTextView = (TextView) findViewById(R.id.modify_course_subject_textview);
		mCodesTextView = (TextView) findViewById(R.id.modify_course_code_textview);
		
		// Set TextView
//		mSubjectTextView.setText(mThisCourse.mSubject);
//		mCodesTextView.setText(mThisCourse.mCode);

		// Set OnClickListener
		mSaveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Course c = new Course();
				
				if (c.mSubject != null) {
					try {
						mCourseHandle.addCourse(c);
						Log.d("# ADDED OFFERINGS:", "" + c.mOfferings.size());
						for (int i = 0; i < c.mOfferings.size(); i++) {
							Log.d(TAG, "Added: " + c.mOfferings.get(i).mSubj
									+ " " + mOfferings.get(i).mCode + ", Type "
									+ mOfferings.get(i).mType + ", Section "
									+ mOfferings.get(i).mSection
									+ " to Offerings");
						}
						onBackPressed();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		mCancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Do nothing.
				onBackPressed();
			}
		});
	}
}
