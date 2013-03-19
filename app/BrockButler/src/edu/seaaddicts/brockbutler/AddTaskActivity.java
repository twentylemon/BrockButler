package edu.seaaddicts.brockbutler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.seaaddicts.brockbutler.coursemanager.CourseHandler;
import edu.seaaddicts.brockbutler.scheduler.Task;

public class AddTaskActivity extends Activity {
	
	Button mSaveContactButton;
	Button mCancelButton;
	CourseHandler mCourseHandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_task, menu);
		return true;
	}
	
	private void init() {
		mCourseHandle = new CourseHandler(this.getApplicationContext());
		mSaveContactButton = (Button) findViewById(R.id.add_task_save_button);
		mSaveContactButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Task t = new Task();
//				t.mMark;
//				t.mBase;
//				t.mWeight;
//				t.mAssign;
//				t.mName;
//				t.mDueDate;
//				t.mPriority;
			}
		});
		
		mCancelButton = (Button) findViewById(R.id.add_task_cancel_button);
	}

}
