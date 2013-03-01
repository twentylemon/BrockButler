package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import edu.seaaddicts.brockbutler.contacts.Contact;

public class Course {
	public int mId;
	public String mSubject;
	public String mCode;
	public String mInstructor;
	public String mInstructorEmail;
	public ArrayList<Offering> mOfferings;
	public ArrayList<Contact> mContacts;
}
