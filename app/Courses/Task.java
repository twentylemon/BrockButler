/**
 * Tasks.java
 * Brock Butler
 * A wrapper class for tasks information
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler;

import java.util.ArrayList;

public class Task {
	
	public String mSubj;
	public String mCode;
	public boolean mIsPastDue;
	public float mMark;
	public float mBase;
	public float mWeight;
	public int mAssign;
	public String mName;
	public String mCreationDate;
	public String mDueDate;
	public int mPriority;
	public ArrayList<Contacts> mContacts;

	public Task(){
		mContacts = new ArrayList<Contacts>();
	}
	public boolean isPastDueDate(){
		return true;
	}

}
