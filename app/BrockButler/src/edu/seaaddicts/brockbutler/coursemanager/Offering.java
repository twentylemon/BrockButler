/**
 * Offering.java
 * Brock Butler
 * A wrapper class for Offering information
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

public class Offering {
	public int mId;	    //offering id
	public String mSubj;//faculty name
	public String mCode;//course code
	public int mSection;//section
	public String mType;//type
	public ArrayList<OfferingTime> mOfferingTimes;//list of times offered
	public int mOid; //extra id if needed
	
	//constructor - initializes arraylist of offering times
	public Offering(){
		mOfferingTimes = new ArrayList<OfferingTime>();
	}
}