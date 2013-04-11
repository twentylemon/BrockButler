/**
 * Tasks.java
 * Brock Butler
 * A wrapper class for tasks information
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler.scheduler;

import java.util.ArrayList;

import edu.seaaddicts.brockbutler.contacts.Contact;

public class Task {
	public String mSubj; //course name
	public String mCode; //course code
	public boolean mIsPastDue; //is the task late
	public float mMark; //mark on task
	public float mBase; //base mark of task
	public float mWeight; //weight of the mark
	public int mAssign;  //assignment number
	public String mName; //name of task
	public String mCreationDate; //creation date
	public String mDueDate; //due date
	public int mPriority; //priority of task
	public int mIsDone; //is the task complete
	public ArrayList<Contact> mContacts; //task contacts

	//contructor - initializes the contacts arraylist
	public Task(){
		mContacts = new ArrayList<Contact>();
	}
	
	//isPastDue - calculates if the task is past the due date
	public boolean isPastDueDate(){
		return true;
	}

}
