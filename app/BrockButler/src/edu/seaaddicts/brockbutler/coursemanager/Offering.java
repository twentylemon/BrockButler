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
	public int mId;
	public String mSubj;
	public String mCode;
	public int mSection;
	public String mType;
	public ArrayList<OfferingTime> mOfferingTimes;
	public int mOid;
	
	public Offering(){
		mOfferingTimes = new ArrayList<OfferingTime>();
	}
}