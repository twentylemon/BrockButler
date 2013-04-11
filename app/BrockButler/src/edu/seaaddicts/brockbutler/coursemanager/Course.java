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
	public int mId;	//course ID
	public String mSubject;  //subject name
	public String mCode;  //course code
	public String mDesc;  //course description
	public String mInstructor;  //instructor name
	public String mInstructor_email;  //instructor's email
	public ArrayList<Offering> mOfferings;  //list of offerings for this course
	public ArrayList<Task> mTasks; //list of tasks associated with this course
	public ArrayList<Contact> mContacts;  //contacts for this course

	//Constructor - initializes the arraylists for offerings, tasks and contacts
	public Course() {
		mOfferings = new ArrayList<Offering>();
		mTasks = new ArrayList<Task>();
		mContacts = new ArrayList<Contact>();
	}
}