package edu.seaaddicts.brockbutler.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.coursemanager.Course;
import edu.seaaddicts.brockbutler.coursemanager.CourseHandler;

public class AddTaskActivity extends Activity {
	private static final String TAG = "AddTaskActivity";
	private static final int DATE_DIALOG_ID = 0;

	private Button mDueDateButton;
	private Button mSaveButton;
	private Button mCancelButton;

	private ArrayList<Course> mRegCourses;
	private TextView mDueDateTextView;

	private EditText mTaskTitle;
	private EditText mTaskWeight;
	private EditText mTaskBase;
	private EditText mTaskMark;

	private Spinner mCourseSpinner;
	private Spinner mPrioritySpinner;

	private CourseHandler mCourseHandle = null;

	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
	}

	/*
	 * Initialize all views and sets Button OnClickListeners.
	 */
	private void init() {
		/*
		 * CourseHandler
		 */
		mCourseHandle = new CourseHandler(this);

		/*
		 * Buttons
		 */
		mDueDateButton = (Button) findViewById(R.id.add_task_due_date_button);
		mSaveButton = (Button) findViewById(R.id.add_task_save_button);
		mCancelButton = (Button) findViewById(R.id.add_task_cancel_button);

		/*
		 * TextViews
		 */
		mDueDateTextView = (TextView) findViewById(R.id.add_task_date_textview);

		/*
		 * Spinners
		 */
		mPrioritySpinner = (Spinner) findViewById(R.id.add_task_priority_spinner);
		mCourseSpinner = (Spinner) findViewById(R.id.add_task_course_spinner);

		/*
		 * EditTexts
		 */
		mTaskTitle = (EditText) findViewById(R.id.add_task_title);
		mTaskWeight = (EditText) findViewById(R.id.task_weight);
		mTaskBase = (EditText) findViewById(R.id.task_base);
		mTaskMark = (EditText) findViewById(R.id.task_mark);

		mRegCourses = mCourseHandle.getRegisteredCourses();
		ArrayList<String> cs = new ArrayList<String>();

		/*
		 * ArrayList of Course Strings in format: SUBJECT CODE
		 */
		for (int i = 0; i < mRegCourses.size(); i++) {
			cs.add(mRegCourses.get(i).mSubject + " " + mRegCourses.get(i).mCode);
		}

		/*
		 * Set the course Spinner
		 */
		mCourseSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, cs));

		/*
		 * DatePicker stuff.
		 */
		final Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);

		/*
		 * OnClickListeners
		 */

		// Deprecated but easier than alternative.
		mDueDateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		mSaveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Task t = new Task();
				t.mSubj = mRegCourses.get(mCourseSpinner
						.getSelectedItemPosition()).mSubject;
				t.mCode = mRegCourses.get(mCourseSpinner
						.getSelectedItemPosition()).mCode;
				t.mName = mTaskTitle.getText().toString();
				// Lower == higher priority
				t.mPriority = mPrioritySpinner.getSelectedItemPosition();
				t.mDueDate = mDueDateTextView.getText().toString();

				try {
					t.mWeight = Float.parseFloat(mTaskWeight.getText()
							.toString());
					t.mBase = Float.parseFloat(mTaskBase.getText().toString());
					t.mMark = Float.parseFloat(mTaskMark.getText().toString());
				} catch (NumberFormatException e) {
					Log.e(TAG,
							"It is OK, we just had a blank field when parsing a float.");
				}

				// Get current date
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA);
				Date date = new Date();
				t.mCreationDate = dateFormat.format(date);
				mCourseHandle.addTask(t);
				onBackPressed();
			}
		});

		mCancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Do nothing.
				onBackPressed();
			}
		});
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 * 
	 *      Overridden method to handle multiple dialogs that may be created
	 *      from this activity.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDatePickerListener, mYear,
					mMonth, mDay);
		}
		return null;
	}

	/**
	 * Retrieves date set from DatePickerDialog and sets TextView in this
	 * activity.
	 */
	private DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			mYear = selectedYear;
			mMonth = selectedMonth + 1; // Since index starts at 0 and there is
										// no 0th month.
			mDay = selectedDay;

			// Set TextView in this activity.
			if (mMonth < 10) {
				if (mDay < 10) {
					mDueDateTextView.setText(new StringBuilder().append(mYear)
							.append("/").append(0).append(mMonth).append("/")
							.append(0).append(mDay));
				} else
					mDueDateTextView.setText(new StringBuilder().append(mYear)
							.append("/").append(0).append(mMonth).append("/")
							.append(mDay));
			} else if (mMonth > 9) {
				if (mDay < 10) {
					mDueDateTextView.setText(new StringBuilder().append(mYear)
							.append("/").append(mMonth).append("/").append(0)
							.append(mDay));
				} else
					mDueDateTextView.setText(new StringBuilder().append(mYear)
							.append("/").append(mMonth).append("/")
							.append(mDay));
			}

		}
	};

}
