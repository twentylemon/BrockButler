package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
	public Course course;
	public Course brock;

	private Button mSaveButton;
	private Button mCancelButton;

	private ListView mLecsListView;
	private ListView mSemsListView;
	private ListView mTutsListView;
	private ListView mLabsListView;

	private CurrentCoursesHandler mCourseHandle;

	private TextView mSubjectTextView;
	private TextView mCodesTextView;
	
	private Bundle mCourseBundle;
	private Intent mCallingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_course);
		Bundle bundle = this.getIntent().getExtras();

		brock = new CourseHandler(getApplicationContext()).getCourseOfferings(
				bundle.getString(CourseManagerActivity.CODE_COURSE_SUBJECT),
				bundle.getString(CourseManagerActivity.CODE_COURSE_CODE));
		mCourseHandle = new CurrentCoursesHandler(this.getApplicationContext());
		course = mCourseHandle.getCourse(bundle.getString(CourseManagerActivity.CODE_COURSE_SUBJECT),
				bundle.getString(CourseManagerActivity.CODE_COURSE_CODE));
		
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
		
		// Set TextView
		mSubjectTextView.setText(course.mSubject+" "+course.mCode);
		
		mCode = course.mCode;
		mOfferings = brock.mOfferings;

		String s;
		mLecs = new ArrayList<String>();
		mLabs = new ArrayList<String>();
		mTuts = new ArrayList<String>();
		mSems = new ArrayList<String>();

		mLecsOfferings = new ArrayList<Offering>();
		mLabsOfferings = new ArrayList<Offering>();
		mTutsOfferings = new ArrayList<Offering>();
		mSemsOfferings = new ArrayList<Offering>();

		for (int i = 0; i < mOfferings.size(); i++) {
			s = mOfferings.get(i).mType;

			// Some offerings don't have any of what we are looking for,
			// so check length to make sure.
			if (s.length() > 2) {
				String ss = s.substring(0, 3).trim();
				if (ss.equalsIgnoreCase("lec")) {
					mLecs.add(s + ", SEC " + mOfferings.get(i).mSection);
					mLecsOfferings.add(mOfferings.get(i));
				} else if (ss.equalsIgnoreCase("lab")) {
					mLabs.add(s + ", SEC " + mOfferings.get(i).mSection);
					mLabsOfferings.add(mOfferings.get(i));
				} else if (ss.equalsIgnoreCase("tut")) {
					mTuts.add(s + ", SEC " + mOfferings.get(i).mSection);
					mTutsOfferings.add(mOfferings.get(i));
				} else if (ss.equalsIgnoreCase("sem")) {
					mSems.add(s + ", SEC " + mOfferings.get(i).mSection);
					mSemsOfferings.add(mOfferings.get(i));
				}
			}
		}

		// Check if offerings available, and add ListView to display if so.
		LinearLayout lec_lay = (LinearLayout) findViewById(R.id.layout_add_lecs);
		LinearLayout lab_lay = (LinearLayout) findViewById(R.id.layout_add_labs);
		LinearLayout tut_lay = (LinearLayout) findViewById(R.id.layout_add_tuts);
		LinearLayout sem_lay = (LinearLayout) findViewById(R.id.layout_add_sems);

		if (mLecs.size() > 0) {
			lec_lay.setVisibility(VISIBLE);
			mLecsListView = (ListView) findViewById(R.id.add_course_add_lecs);
			mLecsListView.setAdapter(new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_multiple_choice,
					mLecs));
		} else {
			lec_lay.setVisibility(GONE);
		}
		if (mLabs.size() > 0) {
			lab_lay.setVisibility(VISIBLE);
			mLabsListView = (ListView) findViewById(R.id.add_course_add_labs);
			mLabsListView.setAdapter(new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_multiple_choice,
					mLabs));
		} else {
			lab_lay.setVisibility(GONE);
		}
		if (mTuts.size() > 0) {
			tut_lay.setVisibility(VISIBLE);
			mTutsListView = (ListView) findViewById(R.id.add_course_add_tuts);
			mTutsListView.setAdapter(new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_multiple_choice,
					mTuts));
		} else {
			tut_lay.setVisibility(GONE);
		}
		if (mSemsOfferings.size() > 0) {
			sem_lay.setVisibility(VISIBLE);
			mSemsListView = (ListView) findViewById(R.id.add_course_add_sems);
			mSemsListView.setAdapter(new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_multiple_choice,
					mSems));
		} else {
			sem_lay.setVisibility(GONE);
		}

		// Set OnClickListener
		mSaveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Course c = new Course();
				c.mSubject          = course.mSubject;
				c.mCode             = course.mCode;
				c.mDesc             = course.mDesc;
				c.mInstructor       = course.mInstructor;
				c.mInstructor_email = course.mInstructor_email;
				for (Task t : course.mTasks)
					c.mTasks.add(t);
				
				for (Offering o : course.mOfferings)
					mCourseHandle.deleteOffering(o);
				
				SparseBooleanArray sba1, sba2, sba3, sba4;
				if (mLecsListView != null) {
					sba1 = mLecsListView.getCheckedItemPositions();
					for (int i = 0; i < mLecsListView.getCount(); i++) {
						if (sba1.get(i) == true) {
							c.mOfferings.add(mLecsOfferings.get(i));
							Log.d(TAG, "Added: " + mLecsOfferings.get(i).mSubj
									+ " " + mOfferings.get(i).mCode + ", Type "
									+ mOfferings.get(i).mType + ", Section "
									+ mOfferings.get(i).mSection
									+ " to Offerings");
						}
					}
				}

				if (mLabsListView != null) {
					sba2 = mLabsListView.getCheckedItemPositions();

					for (int i = 0; i < mLabsListView.getCount(); i++) {
						if (sba2.get(i) == true) {
							c.mOfferings.add(mLabsOfferings.get(i));
							Log.d(TAG, "Added: " + mLabsOfferings.get(i).mSubj
									+ " " + mOfferings.get(i).mCode + ", Type "
									+ mOfferings.get(i).mType + ", Section "
									+ mOfferings.get(i).mSection
									+ " to Offerings");
						}
					}
				}

				if (mTutsListView != null) {
					sba3 = mTutsListView.getCheckedItemPositions();

					for (int i = 0; i < mTutsListView.getCount(); i++) {
						if (sba3.get(i) == true) {
							c.mOfferings.add(mTutsOfferings.get(i));
							Log.d(TAG, "Added: " + mTutsOfferings.get(i).mSubj
									+ " " + mOfferings.get(i).mCode + ", Type "
									+ mOfferings.get(i).mType + ", Section "
									+ mOfferings.get(i).mSection
									+ " to Offerings");
						}
					}
				}

				if (mSemsListView != null) {
					sba4 = mSemsListView.getCheckedItemPositions();

					for (int i = 0; i < mSemsListView.getCount(); i++) {
						if (sba4.get(i) == true) {
							c.mOfferings.add(mSemsOfferings.get(i));
							Log.d(TAG, "Added: " + mSemsOfferings.get(i).mSubj
									+ " " + mOfferings.get(i).mCode + ", Type "
									+ mOfferings.get(i).mType + ", Section "
									+ mOfferings.get(i).mSection
									+ " to Offerings");
						}
					}
				}
				if (c.mSubject != null) {
					try {
						mCourseHandle.addCourse(c);
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
