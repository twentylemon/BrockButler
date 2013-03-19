/**
 * Course.java
 * Brock Butler
 * A wrapper class for Course information
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import edu.seaaddicts.brockbutler.contacts.Contact;
import edu.seaaddicts.brockbutler.scheduler.Task;

public class Course {
	public int mId;
	public String mSubject;
	public String mCode;
	public String mDesc;
	public String mInstructor;
	public String mInstructor_email;
	public ArrayList<Offering> mOfferings;
	public ArrayList<Task> mTasks;
	public ArrayList<Contact> mContacts;

	public Course(){
		mOfferings = new ArrayList<Offering>();
		mTasks = new ArrayList<Task>();
		mContacts = new ArrayList<Contact>();
	}
}