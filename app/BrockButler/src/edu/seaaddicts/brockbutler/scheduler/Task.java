package edu.seaaddicts.brockbutler.scheduler;

import edu.seaaddicts.brockbutler.contacts.Contact;

public class Task {
	public int mId;
	public boolean mIsPastDue;
	public int mPriority;
	public String mTitle;
	public String mDescription;
	public String mCourse;
	public String mCreationDate;
	public String mDueDate;
	public Contact mContacts[];
	
	public Task()
	{
		// Do something.
	}
	
	public Task(int id, boolean pastDue, int priority, String title,
			String desc, String course, String createDate, String dueDate,
			Contact contacts[]) {
		mId = id;
		mPriority = priority;
		mTitle = title;
		mDescription = desc;
		mCourse = course;
	}
	
	private boolean isPastDueDate() {
		// Have to talk about how dates are to be stored.
		return false;
	}
}
