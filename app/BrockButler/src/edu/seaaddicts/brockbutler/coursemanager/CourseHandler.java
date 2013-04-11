/**
 * CourseHandler.java
 * Brock Butler
 * A class to allow easy access to database functions
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 **/

package edu.seaaddicts.brockbutler.coursemanager;
import edu.seaaddicts.brockbutler.contacts.Contact;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.seaaddicts.brockbutler.scheduler.Task;

public class CourseHandler {
	// Context context;
	CurrentCoursesHandler CH;
	CourseListHandler courseList;

	/* Constructor - opens and closes database to ensure the database exists
	 *    and if not, it copies over the installed database of course offerings
	 * @param context - application context
	 */
	public CourseHandler(Context context) {
		// this.context = context;
		CH = new CurrentCoursesHandler(context);
		courseList = new CourseListHandler(context);
		try{
		courseList.createDataBase();
		courseList.openDataBase();
		courseList.close();
		}
		catch(Exception e){};
		//SQLiteDatabase db = courseList.getWritableDatabase();
		//db.close();
	}
	
	/* updateAll - updates all course information from the Brock University registrar's
	 * office website
	 */
	public void updateAll(){
		courseList.addCourse();
	}

	/* Depreciated - getAllCourses - grabs course data from the registrar's timetable and
	 * inserts data into the masterlist table.
	 * Depreciated due to information no longer being available on website
	 */
	public void getAllCourses() {
		courseList.addCourse();
	}

	/* getCourse - gets all information for a given course subject and code
	 * @param subj - subject name to get
	 * @param code - course code to get
	*/
	public Course getCourse(final String subj, final String code) {
		return CH.getCourse(subj, code);
	}

	/* updateCourse - updates all the information for a given course
	 * @param course - course information to update
	 */
	public void updateCourse(Course course) {
		CH.addCourse(course);
	}

	/* getSubjects - gets a list of subjects available from the master list
	 * returns an arraylist of subject offerings
	 */
	public ArrayList<String> getSubjects() throws Exception {
		return courseList.getSubjects();
	}

	/* getCodes - gets a list of codes for a given subject from the master list
	 * returns an arraylist of subject codes
	 * @param subj - return codes for this subject
	 */
	public ArrayList<String> getCodes(String subj) {
		return courseList.getCodes(subj);
	}

	/* getCourseOfferings - returns all offerings offered for a given course
	 * Converts the offerings from MasterCourse format to Course format
	 * @param subj - subject name
	 * @param code - course code
	 */
	public Course getCourseOfferings(String subj, String code) {
		//get list of offerings as a list of MasterCourse objects
		ArrayList<MasterCourse> list = courseList.getCourses(subj, code);
		Course course = new Course(); //create a new course object
		ArrayList<OfferingTime> offeringtimes;
		course.mSubject = list.get(0).subj;
		course.mCode = list.get(0).code;
		course.mInstructor = list.get(0).instructor;
		course.mDesc = list.get(0).desc;
		Offering offering;
		int tindex = 0;
		OfferingTime otime;
		//add all offerings for a particular course
		ArrayList<Offering> offerings = new ArrayList<Offering>();
		for (int i = 0; i < list.size(); i++) {
			offering = new Offering();
			offering.mSubj = list.get(i).subj;
			offering.mCode = list.get(i).code;
			offering.mType = list.get(i).type;
			offering.mSection = Integer.parseInt(list.get(i).sec);
			//add all the offeringtimes associated with all the offerings
			offeringtimes = new ArrayList<OfferingTime>();
			for (int j = 0; j < 5; j++) {
				if (list.get(i).days.charAt(j) != ' ') {
					otime = new OfferingTime();
					otime.mDay = list.get(i).days.substring(j, j+1);
					otime.mLocation = list.get(i).location;
					for (int h = 0; h < list.get(i).time.length(); h++) {
						if (list.get(i).time.charAt(h) == '-') {
							tindex = h;
							break;
						}
					}
					//get the times for each offering
					otime.mStartTime = list.get(i).time.substring(0, tindex);
					otime.mEndTime = list.get(i).time.substring(tindex + 1,
							list.get(i).time.length());
					offeringtimes.add(otime);
				}
			}
			offering.mOfferingTimes = offeringtimes;
			offerings.add(offering);

		}
		course.mOfferings = offerings;

		return course;//return the course object
	}

	/* addCourse - adds information for a course into the database
	 * returns a 0 if sucessful, 1 if the add failed
	 * @param course - the course object to be added
	 */
	public int addCourse(Course course) throws Exception {
		try {
			CH.addCourse(course);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	/* removeCourse - deletes all information from the database for a course
	 * returns a 0 on success, returns 1 if failure
	 * @param course - the course information to be deleted
	 */
	public int removeCourse(Course course) {
		try {
			CH.deleteCourse(course);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	/*
	 * getRegisteredCourses - returns all information for all courses in the
	 * current courses database
	 */
	public ArrayList<Course> getRegisteredCourses() {
		return CH.getRegCourses();
	}

	/* getOfferings - get all offerings for a certain course
	 * @param subj - course name
	 * @param code - course code
	 */
	public ArrayList<Offering> getOfferings(String subj, String code) {
		return CH.getOfferings(subj, code);
	}

	/* getTasks - gets all tasks from the database
	 */
	public ArrayList<Task> getTasks() {
		return CH.getTasks();
	}

	/* addTask - adds a given task to the task table in the database
	 * returns 0 if sucessful, returns 1 if it fails
	 * @param task - the task information to be added to the database
	 */
	public int addTask(Task task) {
		try {
			CH.addTasks(task);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	// addTask - adds the tasks for a given course to the task table in the
		// database
	public int addTask(Course course) {
		try {
			CH.addTasks(course);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	/* removeTask - deletes task information from the database for a given task
	 * returns 0 if sucessful, 1 if failure
	 * @param task -  the task information to be removed from the database
	 */
	public int removeTask(Task task) {
		try {
			CH.removeTask(task);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	/* getBase - returns the total base mark for a particular course
	 * given base information from the course
	 * @param course - the course to calculate the total base for
	 */
	public float getBase(Course course) {
		float base = 0;
		for (int i = 0; i < course.mTasks.size(); i++)				
			base +=course.mTasks.get(i).mWeight;
		 return base;
	}
	
	/* getMark - returns the calculated progress mark for a course
	 * given mark information from the course, the mark is calculated and returned
	 * as a float
	 * @param course - the course to calculate the marks for
	 */
	public float getMark(Course course) {
		float mark = 0;
		for (int i = 0; i < course.mTasks.size(); i++){
			if(course.mTasks.get(i).mBase !=0){
			mark += (course.mTasks.get(i).mMark / course.mTasks.get(i).mBase)
					* course.mTasks.get(i).mWeight;}
			else mark+=0;
		}
			
		 return mark;
	}

	/* getSize - returns the number of courses added to the course database
	 */
	public int getSize() {
		return courseList.size();
	}
	
	/* removeContact - removes contact information from the database
	 * @param contact - the contact information to be removed
	 */
	public void removeContact(Contact contact){
		CH.removeContact(contact);
	}

	/* Query - returns a cursor with results for a custom query
	 * @param query - a string with a sqlite query
	 */
	public Cursor Query(String query) {
		return CH.Query(query);
	}
}
