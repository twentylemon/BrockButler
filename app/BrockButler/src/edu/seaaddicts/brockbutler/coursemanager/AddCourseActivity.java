package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.seaaddicts.brockbutler.R;

public class AddCourseActivity extends Activity {

	private static final String TAG = "AddCourseActivity";

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

	private String mSubject;
	private String mCode;

	private Course mCourse;

	private Button mSaveButton;
	private Button mCancelButton;

	private CourseHandler mCourseHandle;
	ArrayList<Offering> mOfferings;

	private Spinner mSubjectSpinner;
	private Spinner mCodesSpinner;

	private ListView mLecsListView;
	private ListView mSemsListView;
	private ListView mTutsListView;
	private ListView mLabsListView;

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
			mSubjectSpinner.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item,
					mCourseHandle.getSubjects()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mCodesSpinner = (Spinner) findViewById(R.id.add_course_codes_spinner);
		mSubjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try {
					mSubject = arg0.getItemAtPosition(arg2).toString();
					mCodesSpinner.setAdapter(new ArrayAdapter<String>(
							AddCourseActivity.this,
							android.R.layout.simple_spinner_dropdown_item,
							mCourseHandle.getCodes(mSubject)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// Do nothing.
			}
		});
		mCodesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mCode = arg0.getItemAtPosition(arg2).toString();
				mCourse = mCourseHandle.getCourseOfferings(mSubject, mCode);
				mOfferings = mCourse.mOfferings;

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

				// Check if offerings available, and add ListView to display if
				// so.
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
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		mSaveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Course c = new Course();
				c.mSubject = mSubject;
				c.mCode = mCode;
				c.mInstructor = mCourseHandle.getCourseOfferings(mSubject,
						mCode).mInstructor;

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
