package edu.seaaddicts.brockbutler.scheduler;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.contacts.AddContactActivity;
import edu.seaaddicts.brockbutler.contacts.ContactsActivity;

public class AddTaskActivity extends Activity {
	private static final int DATE_DIALOG_ID = 0;

	private Button mDueDateButton;
	private Button mAddContactButton;
	private Button mSaveButton;
	private Button mCancelButton;
	private ListView mContactListView;
	private DatePicker mDueDatePicker;
	private TextView mDueDateTextView;

	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		init();
	}

	/*
	 * Initialize all views and sets Button OnClickListeners.
	 */
	private void init() {
		mDueDateButton = (Button) findViewById(R.id.add_task_due_date_button);
		mAddContactButton = (Button) findViewById(R.id.add_task_add_contact_button);
		mSaveButton = (Button) findViewById(R.id.add_task_save_button);
		mCancelButton = (Button) findViewById(R.id.add_task_cancel_button);
		mContactListView = (ListView) findViewById(R.id.add_task_add_contact_listview);
		mDueDateTextView = (TextView) findViewById(R.id.add_task_date_textview);

		// DatePicker stuff.
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// OnClickListener for DatePicker
		mDueDateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		mAddContactButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showContactSelectDialog(AddTaskActivity.this, null, "");
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

	private void showContactSelectDialog(Activity activity, String title,
			CharSequence message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if (title != null)
			builder.setTitle(title);
		// builder.setMessage(message);
		builder.setPositiveButton("Existing",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(AddTaskActivity.this,
								ContactsActivity.class);
						startActivity(i);
					}
				});
		builder.setNegativeButton("New", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(AddTaskActivity.this,
						AddContactActivity.class);
				startActivity(i);
			}
		});
		builder.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 * 
	 * Overridden method to handle multiple dialogs that may be created from
	 * this activity.
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

	/*
	 * Retrieves date set from DatePickerDialog and sets TextView in this
	 * activity.
	 */
	private DatePickerDialog.OnDateSetListener mDatePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			mYear = selectedYear;
			mMonth = selectedMonth;
			mDay = selectedDay;

			// Set TextView in this activity.
			mDueDateTextView.setText(new StringBuilder().append(mMonth + 1)
					.append("-").append(mDay).append("-").append(mYear)
					.append(" "));

		}
	};

}
