/**
 * CurrentCoursesHandler.java
 * Brock Butler
 * Handles database table creation and queries for course information
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 **/

package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.seaaddicts.brockbutler.contacts.Contact;
import edu.seaaddicts.brockbutler.scheduler.Task;

public class CurrentCoursesHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Database";
	// table names
	private static final String TABLE_COURSES = "courses";
	private static final String TABLE_TASKS = "tasks";
	private static final String TABLE_OFFERINGS = "offerings";
	private static final String TABLE_OFFERING_TIMES = "offering_times";
	private static final String TABLE_CONTACTS = "contacts";
	// field names
	private static final String KEY_SUBJ = "subj";
	private static final String KEY_CODE = "code";
	private static final String KEY_DESC = "desc";
	private static final String KEY_INSTRUCTOR = "instructor";
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_SEC = "sec";
	private static final String KEY_DAY = "day";
	private static final String KEY_TIMES = "time_start";
	private static final String KEY_TIMEE = "time_end";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_ASSIGN = "assign";
	private static final String KEY_NAME = "name";
	private static final String KEY_MARK = "mark";
	private static final String KEY_BASE = "base";
	private static final String KEY_WEIGHT = "weight";
	private static final String KEY_DUE = "due";
	private static final String KEY_CREATE_DATE = "create_date";
	private static final String KEY_IS_DONE = "is_done";
	private static final String KEY_CID = "cid";
	private static final String KEY_FNAME = "fname";
	private static final String KEY_LNAME = "lname";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PRIORITY = "priority";
	private static final String KEY_INSTREMAIL = "instructor_email";

	/* Constructor for the database helper */
	public CurrentCoursesHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/* Create tables for courses, tasks, offerings, offering times, and contacts
	 * in the database if they do not exist when the database helper is first
	 * called
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	/* on an upgrade drop tables and recreate */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERINGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERING_TIMES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		// Create tables again
		onCreate(db);
	}

	/* addCourse - adds all information for a course to the database adding
	 * course, offerings, tasks and contacts information, if information exists
	 * for the course then an update is done, otherwise and insert is done
	 * @param course - the course information to add to the course table
	 */
	public void addCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		long num = 0;
		boolean update = false;
		//check if the course already exists in the table
		num = DatabaseUtils.queryNumEntries(db, TABLE_COURSES, KEY_SUBJ + " ='"
				+ course.mSubject + "' AND " + KEY_CODE + "='" + course.mCode
				+ "'");
		if (num > 0)//if it exists then do an update
			update = true;
		// values to be added to the table
		values.put(KEY_SUBJ, course.mSubject); // subject code
		values.put(KEY_CODE, course.mCode);
		values.put(KEY_DESC, course.mDesc);
		values.put(KEY_INSTRUCTOR, course.mInstructor);
		values.put(KEY_INSTREMAIL, course.mInstructor_email);
		// Inserting or updating Row
		if (update)
			db.update(TABLE_COURSES, values, KEY_SUBJ + " ='" + course.mSubject
					+ "' AND " + KEY_CODE + "='" + course.mCode + "'", null);
		else
			db.insert(TABLE_COURSES, null, values);
		db.close(); // Closing database connection
		values.clear();
		addOfferings(course);//add the offerings for the course
		addTasks(course);//add the tasks for the course
		addContacts(course.mContacts);//add the contacts for the course
		db.close();//close the database
	}
	
	/* deleteCourse - removes all information for the given course from the
	 * database
	 * @param course - the course data to be removed from the course table
	 */
	public void deleteCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();
		//delete the row for the selected course
		db.delete(TABLE_COURSES, KEY_SUBJ + "='" + course.mSubject + "' AND "
				+ KEY_CODE + "='" + course.mCode + "'", null);
		db.close(); //close the db
		//delete all the offerings
		for (int i=0; i<course.mOfferings.size(); i++){
			deleteOffering(course.mOfferings.get(i));		
		}
		//delete all the tasks
		for (int i=0; i<course.mTasks.size(); i++){
			removeTask(course.mTasks.get(i));
		}	
	}

	/* getCourse - retrieves all information for the given course
	 * @param subj - the course name
	 * @param code - the course code 
	 */
	public Course getCourse(String subj, String code) {
		SQLiteDatabase db = this.getReadableDatabase();
		Course course = new Course();
		//query to retrieve all information for the given subj and code
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " where "
				+ KEY_SUBJ + "= '" + subj + "' and " + KEY_CODE + " = '" + code
				+ "'", null);
		if (c != null) {
			//start at the first record
			if (c.moveToFirst()) {
				do {
					//set values in the course object from the table
					course.mSubject = c.getString(c.getColumnIndex(KEY_SUBJ));
					course.mCode = c.getString(c.getColumnIndex(KEY_CODE));
					course.mDesc = c.getString(c.getColumnIndex(KEY_DESC));
					course.mInstructor = c.getString(c
							.getColumnIndex(KEY_INSTRUCTOR));
					course.mInstructor_email = c.getString(c
							.getColumnIndex(KEY_INSTREMAIL));
					course.mOfferings = getOfferings(course.mSubject,
							course.mCode);
					course.mTasks = getTasks(course);//get the tasks for the course
					course.mContacts = getContacts(course);//get the contacts for the course
				} while (c.moveToNext());
			}
		}
		c.close();//close the cursor
		db.close();//close the database
		return course;//return the course object
	}

	 /* addOfferings - adds all offerings offered by a particular course as well
	 * as their offering times
	 * @param course - the course to add the offerings from
	 */
	public void addOfferings(Course course) {
		Offering offering;
		OfferingTime offeringtime;
		ContentValues values = new ContentValues();
		SQLiteDatabase db = this.getWritableDatabase();
		long num = 0;
		boolean update = false;
		for (int i = 0; i < course.mOfferings.size(); i++) {
			offering = course.mOfferings.get(i);
			num = 0;
			update = false;
			//find if the offering already exists
			num = DatabaseUtils.queryNumEntries(db, TABLE_OFFERINGS, KEY_SUBJ
					+ " ='" + offering.mSubj + "' AND " + KEY_CODE + "='"
					+ offering.mCode + "' AND " + KEY_TYPE + "='"
					+ offering.mType + "' AND " + KEY_SEC + "="
					+ offering.mSection);
			if (num > 0)//if the offering exists then do an update
				update = true;
			//set the feilds and values
			values.put(KEY_SUBJ, course.mSubject);
			values.put(KEY_CODE, course.mCode);
			values.put(KEY_TYPE, offering.mType);
			values.put(KEY_SEC, offering.mSection);
			if (update)//update the offering information
				db.update(TABLE_OFFERINGS, values, KEY_SUBJ + " ='"
						+ offering.mSubj + "' AND " + KEY_CODE + "='"
						+ offering.mCode + "' AND " + KEY_TYPE + "='"
						+ offering.mType + "' AND " + KEY_SEC + "="
						+ offering.mSection, null);
			else//insert the offering information
				db.insert(TABLE_OFFERINGS, null, values);
			values.clear();
		}
		SQLiteDatabase rdb = this.getReadableDatabase();
		for (int i=0; i<course.mOfferings.size(); i++){
			offering = course.mOfferings.get(i);
			//now adding the offering times for each offering
			for (int j = 0; j < offering.mOfferingTimes.size(); j++) {
				offeringtime = offering.mOfferingTimes.get(j);
				num = 0;
				update = false;
				//see if the offering time exists
				num = DatabaseUtils.queryNumEntries(db, TABLE_OFFERING_TIMES,
						KEY_ID + " =" + offering.mId+ " AND "+KEY_DAY+"='"+offeringtime.mDay+"'");
				if (num > 1)//if exists then update
					update = true;
				//query for offering id for each offering time
				Cursor c = rdb.rawQuery("SELECT " + KEY_ID + " FROM "
						+ TABLE_OFFERINGS + " WHERE " + KEY_SUBJ + "='"
						+ offering.mSubj + "' AND " + KEY_CODE + "='"
						+ offering.mCode + "' AND " + KEY_TYPE + "='"
						+ offering.mType + "' AND " + KEY_SEC + "="
						+ offering.mSection, null);
				c.moveToFirst();
				//set fields and values to be added
				offering.mId = c.getInt(c.getColumnIndex(KEY_ID));
				values.put(KEY_ID, offering.mId);
				values.put(KEY_DAY, offeringtime.mDay);
				values.put(KEY_TIMES, offeringtime.mStartTime);
				values.put(KEY_TIMEE, offeringtime.mEndTime);
				values.put(KEY_LOCATION, offeringtime.mLocation);
				if (update)//update the record
					db.update(TABLE_OFFERING_TIMES, values, KEY_ID + " ="
							+ offering.mId, null);
				else//insert the record
					db.insert(TABLE_OFFERING_TIMES, null, values);
				values.clear();
				c.close();//close the cursor
			}
		}
		rdb.close();//close database connection
		db.close();//close database connection
	}

	
	/* deleteOffering - removes all information from the databse for the given
	 * offering
	 * @param offering - the offering to be removed from the offerings table
	 */
	public void deleteOffering(Offering offering) {
		int id;
		SQLiteDatabase rdb = this.getReadableDatabase();
		//query to get the id of the offering to be deleted
		Cursor c = rdb.rawQuery("SELECT " + KEY_ID + " FROM "
				+ TABLE_OFFERINGS + " WHERE " + KEY_SUBJ + "='"
				+ offering.mSubj + "' AND " + KEY_CODE + "='"
				+ offering.mCode + "' AND " + KEY_TYPE + "='"
				+ offering.mType + "' AND " + KEY_SEC + "="
				+ offering.mSection, null);
		c.moveToFirst();
		id = c.getInt(c.getColumnIndex(KEY_ID));
		c.close();//close cursor
		SQLiteDatabase db = this.getWritableDatabase();
		//delete the offering times associated to the offering
		db.delete(TABLE_OFFERING_TIMES, KEY_ID +"="+id, null);
		//delete the offering from the offerings table
		db.delete(TABLE_OFFERINGS, KEY_SUBJ
				+ " ='" + offering.mSubj + "' AND " + KEY_CODE + "='"
				+ offering.mCode + "' AND " + KEY_TYPE + "='"
				+ offering.mType + "' AND " + KEY_SEC + "="
				+ offering.mSection, null);
		db.close();//close the database
	}

	/* addTasks - adds all tasks associated with a given course 
	 * @param course - the course object with the tasks to be added
	 */
	public void addTasks(Course course) {
		Task task;
		ContentValues values = new ContentValues();
		SQLiteDatabase db = this.getWritableDatabase();
		long num = 0;
		boolean update = false;
		for (int i = 0; i < course.mTasks.size(); i++) {
			task = course.mTasks.get(i);
			num = 0;
			update = false;
			//see if the task exists already
			num = DatabaseUtils.queryNumEntries(db, TABLE_TASKS, KEY_ASSIGN
					+ " ='" + task.mAssign + "' AND " + KEY_SUBJ + " ='"
					+ task.mSubj + "' AND " + KEY_CODE + "='" + task.mCode
					+ "'");
			if (num > 0)//if exists then update
				update = true;
			values.put(KEY_SUBJ, task.mSubj);
			values.put(KEY_CODE, task.mCode);
			//if the task number is not 0 then use that value
			try {
				if (task.mAssign != 0)
					values.put(KEY_ASSIGN, task.mAssign);
			} catch (NullPointerException e){}
			//set all values to be added
			values.put(KEY_NAME, task.mName);
			values.put(KEY_MARK, task.mMark);
			values.put(KEY_BASE, task.mBase);
			values.put(KEY_WEIGHT, task.mWeight);
			values.put(KEY_DUE, task.mDueDate);
			values.put(KEY_CREATE_DATE, task.mCreationDate);
			values.put(KEY_PRIORITY, task.mPriority);
			values.put(KEY_IS_DONE, task.mIsDone);
			if (update)//update the row
				db.update(TABLE_TASKS, values, KEY_ASSIGN + " ='" + task.mAssign
						+ "' AND " + KEY_SUBJ + " ='" + task.mSubj + "' AND "
						+ KEY_CODE + "='" + task.mCode + "'", null);
			else//insert the row
				db.insert(TABLE_TASKS, null, values);
			values.clear();
		}
		db.close();//close the database
	}

	/* addContacts - add contacts to the contacts table in the database for the
	 * given list of contacts
	 * @param contacts - the list of contacts to be added to the contacts table
	 */
	public void addContacts(ArrayList<Contact> contacts) {
		Contact contact;
		ContentValues values = new ContentValues();
		SQLiteDatabase db = this.getWritableDatabase();
		long num = 0;
		boolean update = false;
		for (int j = 0; j < contacts.size(); j++) {
			contact = contacts.get(j);
			num = 0;
			update = false;
			//check if the contact exists
			num = DatabaseUtils.queryNumEntries(db, TABLE_CONTACTS, KEY_SUBJ
					+ " ='" + contact.mSubj + "' AND " + KEY_CODE + "='"
					+ contact.mCode + "' AND " + KEY_FNAME + "='" + contact.mFirstName
					+ "' AND " + KEY_LNAME + "='"+ contact.mLastName+"'");
			if (num > 0)//if exits then update
				update = true;
			//set the fields and the values
			values.put(KEY_SUBJ, contact.mSubj);
			values.put(KEY_CODE, contact.mCode);
			values.put(KEY_FNAME, contact.mFirstName);
			values.put(KEY_LNAME, contact.mLastName);
			values.put(KEY_EMAIL, contact.mEmail);
			if (update)//update the record
				db.update(TABLE_CONTACTS, values, KEY_SUBJ + " ='"
						+ contact.mSubj + "' AND " + KEY_CODE + "='"
						+ contact.mCode + "' AND " + KEY_FNAME + "='"
						+ contact.mFirstName + "' AND " + KEY_LNAME + "='"
						+ contact.mLastName + "' AND " + KEY_EMAIL + "='"
						+ contact.mEmail + "'", null);
			else//insert the record
				db.insert(TABLE_CONTACTS, null, values);
			values.clear();
		}
		db.close();//close the database
	}

	/* addTasks - adds a task for a certain course using the addTasks(course) method
	 * @param task - the task to be added
	 */
	public void addTasks(Task task) {
		Course course = new Course();
		course.mTasks.add(task);
		addTasks(course);//add the tasks for the course object
	}

	/* getOfferings - gets all offerings for a given subject and code 
	 * @param subj - the course subject
	 * @param code - the course code
	 */
	public ArrayList<Offering> getOfferings(String subj, String code) {
		ArrayList<Offering> offerings = new ArrayList<Offering>();
		ArrayList<OfferingTime> offtimes;
		Offering offering;
		OfferingTime otime;
		SQLiteDatabase db = this.getReadableDatabase();
		//get all offerings for the subj and code
		Cursor c = db.rawQuery("SELECT *  FROM " + TABLE_OFFERINGS + " WHERE "
				+ KEY_SUBJ + "='" + subj + "' and " + KEY_CODE + "='" + code
				+ "'", null);
		try {
			if (c != null) {
				if (c.moveToFirst()) {//start at the first record
					do {
						offering = new Offering();
						//add the data into a new offering object
						offering.mId = c.getInt(c.getColumnIndex(KEY_ID));
						offering.mSubj = c
								.getString(c.getColumnIndex(KEY_SUBJ));
						offering.mCode = c
								.getString(c.getColumnIndex(KEY_CODE));
						offering.mType = c
								.getString(c.getColumnIndex(KEY_TYPE));
						offering.mSection = c.getInt(c.getColumnIndex(KEY_SEC));
						offerings.add(offering);
					}while (c.moveToNext());//get next record
					c.close();//close cursor
					//get all the offering times for each offering
					for (int i=0; i<offerings.size(); i++){
						offtimes = new ArrayList<OfferingTime>();
						//get the id for the offering
						Cursor o = db.rawQuery("SELECT *  FROM "
								+ TABLE_OFFERING_TIMES + " WHERE " + KEY_ID
								+ "=" + offerings.get(i).mId, null);
						if (o != null) {
							if (o.moveToFirst()) {//move to first offering time
								do {
									otime = new OfferingTime();
									//insert data from table to OfferingTime oject
									otime.mOid = o.getInt(o
											.getColumnIndex(KEY_ID));
									otime.mDay = o.getString(o
											.getColumnIndex(KEY_DAY));
									otime.mStartTime = o.getString(o
											.getColumnIndex(KEY_TIMES));
									otime.mEndTime = o.getString(o
											.getColumnIndex(KEY_TIMEE));
									otime.mLocation = o.getString(o
											.getColumnIndex(KEY_LOCATION));
									offtimes.add(otime);
								} while (o.moveToNext());//get next time
							}
						}
						offerings.get(i).mOfferingTimes = offtimes;
						o.close();//close cursor
					} 
				}
			}
			db.close();//close database
		} catch (Exception e) {}
		return offerings;//return the offerings
	}

	/* getTasks - gets all tasks a person may have from the database */
	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		SQLiteDatabase db = this.getReadableDatabase();
		Task task;
		//get all tasks from the tasks table
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
		if (c != null) {
			if (c.moveToFirst()) {//start at the first record
				do {
					task = new Task();
					//insert data from the table into a new task object
					task.mSubj = c.getString(c.getColumnIndex(KEY_SUBJ));
					task.mCode = c.getString(c.getColumnIndex(KEY_CODE));
					task.mAssign = c.getInt(c.getColumnIndex(KEY_ASSIGN));
					task.mName = c.getString(c.getColumnIndex(KEY_NAME));
					task.mMark = c.getInt(c.getColumnIndex(KEY_MARK));
					task.mBase = c.getInt(c.getColumnIndex(KEY_BASE));
					task.mWeight = c.getFloat(c.getColumnIndex(KEY_WEIGHT));
					task.mDueDate = c.getString(c.getColumnIndex(KEY_DUE));
					task.mIsDone = c.getInt(c.getColumnIndex(KEY_IS_DONE));
					task.mCreationDate = c.getString(c.getColumnIndex(KEY_CREATE_DATE));
					task.mPriority = c.getInt(c.getColumnIndex(KEY_PRIORITY));
					tasks.add(task);//add the task to the list
				} while (c.moveToNext());
			}
		}
		c.close();//close cursor
		db.close();//close database
		return tasks;//return the list of tasks
	}

	/* getTasks - gets all tasks for a particular course
	 * @param course - the course to get the tasks for 
	 */
	private ArrayList<Task> getTasks(Course course) {
		ArrayList<Task> tasks = new ArrayList<Task>();
		SQLiteDatabase db = this.getReadableDatabase();
		Task task;
		// get all task information for the choosen course
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE "
				+ KEY_SUBJ + "='" + course.mSubject + "' AND " + KEY_CODE
				+ "='" + course.mCode + "'", null);
		if (c != null) {
			if (c.moveToFirst()) {//start at the first record
				do {
					task = new Task();
					//insert data from the table to a new task object
					task.mSubj = c.getString(c.getColumnIndex(KEY_SUBJ));
					task.mCode = c.getString(c.getColumnIndex(KEY_CODE));
					task.mAssign = c.getInt(c.getColumnIndex(KEY_ASSIGN));
					task.mName = c.getString(c.getColumnIndex(KEY_NAME));
					task.mMark = c.getInt(c.getColumnIndex(KEY_MARK));
					task.mBase = c.getInt(c.getColumnIndex(KEY_BASE));
					task.mWeight = c.getFloat(c.getColumnIndex(KEY_WEIGHT));
					task.mDueDate = c.getString(c.getColumnIndex(KEY_DUE));
					task.mIsDone = c.getInt(c.getColumnIndex(KEY_IS_DONE));
					task.mCreationDate = c.getString(c.getColumnIndex(KEY_CREATE_DATE));
					task.mPriority = c.getInt(c.getColumnIndex(KEY_PRIORITY));
					task.mContacts = getContacts(course);
					tasks.add(task);//add task to the list of tasks
				} while (c.moveToNext());//get next record
			}
		}
		c.close();//close cursor
		db.close();//close database
		return tasks;//return the list of tasks
	}

	/* getContacts - get all contacts for a specified course 
	 * @param course - the course object to get contacts for 
 	 */
	private ArrayList<Contact> getContacts(Course course) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		Contact contact;
		//get all contacts from the contacts table for the specified course
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS + " WHERE "
				+ KEY_SUBJ + "='" + course.mSubject + "' AND " + KEY_CODE
				+ "='" + course.mCode + "'", null);
		if (c != null) {
			if (c.moveToFirst()) {//get first record
				do {
					contact = new Contact();
					//insert data from the contacts table to a new contact object
					contact.mSubj = c.getString(c.getColumnIndex(KEY_SUBJ));
					contact.mCode = c.getString(c.getColumnIndex(KEY_CODE));
					contact.mId = c.getInt(c.getColumnIndex(KEY_CID));
					contact.mFirstName = c.getString(c
							.getColumnIndex(KEY_FNAME));
					contact.mLastName = c
							.getString(c.getColumnIndex(KEY_LNAME));
					contact.mEmail = c.getString(c.getColumnIndex(KEY_EMAIL));
					contacts.add(contact);//add contact to list of contacts
				} while (c.moveToNext());//get next record
			}
		}
		c.close(); //close cursor
		db.close(); //close database
		return contacts; //return list of contacts
	}

	/* removeTask - deletes a given task from the tasks table of the database 
	 * @param task - the task to be removed from the tasks table
	 */
	public void removeTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();
		//delete the record for the given task
		db.delete(TABLE_TASKS, KEY_SUBJ
				+ " ='" + task.mSubj + "' AND " + KEY_CODE + "='"
				+ task.mCode + "' AND " + KEY_ASSIGN + "="
				+ task.mAssign, null);
		db.close();//close database
	}

	/* removeContact - deletes a contact from the contacts table from the database
	 * @param contact - the contact information to be deleted from the table
	 */
	public void removeContact(Contact contact){
		SQLiteDatabase db = this.getWritableDatabase();
		//delete the record associated with the contact id
		db.delete(TABLE_CONTACTS, KEY_CID +"="+contact.mId, null );
	}
	
	/* getRegCourses - gets all courses added to the courses table of the
	 * database and all of it's components
	 */
	public ArrayList<Course> getRegCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			//get all courses from the course table
			Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);
			if (c != null) {
				if (c.moveToFirst()) {//start at the first record
					do {
						//get course information for each course found in courses table
						courses.add(getCourse(
								c.getString(c.getColumnIndex(KEY_SUBJ)),
								c.getString(c.getColumnIndex(KEY_CODE))));
					} while (c.moveToNext());//get next record
				}
			}
			c.close();//close cursor
		} catch (Exception e) {}
		db.close();//close database
		return courses; //return list of current courses
	}
	
	/* Query - a general method to allow a query to be done that has not been
	 * specified. it returns a cursor object to allow the data to be read
	 * @param s - a query sent as a string to perform the query on the database
	 */
	public Cursor Query(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(s, null);//perform query
		db.close();
		return c;//return cursor object
	}
}
