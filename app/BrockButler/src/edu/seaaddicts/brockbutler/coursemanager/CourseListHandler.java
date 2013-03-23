/**
 * CourseListHandler.java
 * Brock Butler
 * Creates a database table for a full list of offerings on the registrar's
 * timetable and allows the table to have inserts or be read
 * Created by James Grisdale on 2013-02-24
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler.coursemanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseListHandler extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "Database";
 
    // Contacts table name
    private static final String TABLE_MCOURSES = "MasterList";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SUBJ = "subj";
    private static final String KEY_CODE = "code";
    private static final String KEY_DESC = "desc";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SEC = "sec";
    private static final String KEY_DUR = "dur";
    private static final String KEY_DAYS = "days";
    private static final String KEY_TIME = "time";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_INSTRUCTOR = "instructor";
    
    
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_OFFERINGS = "offerings";
    private static final String TABLE_OFFERING_TIMES = "offering_times";
    private static final String TABLE_CONTACTS = "contacts"; 
    //field names
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
    private static final String KEY_DUR = "dur";
    private static final String KEY_ASSIGN = "assign";
    private static final String KEY_NAME = "name";
    private static final String KEY_MARK = "mark";
    private static final String KEY_BASE = "base";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DUE = "due";
    private static final String KEY_CREATE_DATE = "create_date";
    private static final String KEY_CID = "cid";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_INSTREMAIL = "insructor_email";
    
    Context context;
    public CourseListHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //creates the courses table for the master list of courses
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_MCOURSES + "("
                + KEY_ID + " TEXT," + KEY_SUBJ + " TEXT," + KEY_CODE + " TEXT," 
				+ KEY_DESC + " TEXT," + KEY_TYPE + " TEXT," + KEY_SEC + " TEXT," + KEY_DUR + " TEXT,"
                + KEY_DAYS + " TEXT,"+ KEY_TIME + " TEXT,"+ KEY_LOCATION + " TEXT,"
                + KEY_INSTRUCTOR + " TEXT"+ ")";
        db.execSQL(CREATE_COURSES_TABLE);
        
        String CREATE_COURSES = "CREATE TABLE " + TABLE_COURSES + "("
                + KEY_SUBJ + " TEXT,"
                + KEY_CODE + " TEXT," + KEY_DESC + " TEXT,"
                + KEY_INSTRUCTOR + " TEXT," + KEY_INSTREMAIL + " TEXT," + "PRIMARY KEY("+KEY_SUBJ+","+KEY_CODE+")"+ ")";
		
		String CREATE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_SUBJ + " TEXT,"
                + KEY_CODE + " TEXT," + KEY_ASSIGN + " INTEGER,"
                + KEY_NAME + " TEXT," + KEY_MARK + " INTEGER," + KEY_BASE + " INTEGER," 
                + KEY_WEIGHT + " REAL," + KEY_DUE + " TEXT," + KEY_CREATE_DATE + " TEXT,"
                + KEY_PRIORITY + " INTEGER,"
                + "PRIMARY KEY("+KEY_SUBJ+","+KEY_CODE+","+KEY_ASSIGN+"),"
                + "FOREIGN KEY("+KEY_SUBJ+") REFERENCES "+ TABLE_COURSES +"("+KEY_SUBJ+") ON DELETE CASCADE,"
                + "FOREIGN KEY("+KEY_CODE+") REFERENCES "+ TABLE_COURSES +"("+KEY_CODE+") ON DELETE CASCADE"
                + ")";
		
		String CREATE_OFFERINGS = "CREATE TABLE " + TABLE_OFFERINGS + "("
                + KEY_ID + " INTEGER," + KEY_SUBJ + " TEXT ,"
                + KEY_CODE + " TEXT ," + KEY_TYPE + " TEXT,"
                + KEY_SEC + " INTEGER,"+ "PRIMARY KEY("+KEY_ID+"),"
                + "FOREIGN KEY("+KEY_SUBJ+") REFERENCES "+ TABLE_COURSES +"("+KEY_SUBJ+") ON DELETE CASCADE,"
                + "FOREIGN KEY("+KEY_CODE+") REFERENCES "+ TABLE_COURSES +"("+KEY_CODE+") ON DELETE CASCADE"
                + ")";
		
		String CREATE_OFFERING_TIMES = "CREATE TABLE " + TABLE_OFFERING_TIMES + "("
                + KEY_ID + " INTEGER," + KEY_DAY + " TEXT,"
                + KEY_TIMES + " TEXT ," + KEY_TIMEE + " TEXT,"
                + KEY_LOCATION + " TEXT,"+ "PRIMARY KEY("+KEY_ID+","+KEY_DAY+"),"
                + "FOREIGN KEY("+KEY_ID+") REFERENCES "+ TABLE_OFFERINGS +"("+KEY_ID+")  ON DELETE CASCADE"
                + ")";
		
		String CREATE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_SUBJ + " TEXT," + KEY_CODE + " TEXT,"
                + KEY_CID + " INTEGER," + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT," + KEY_EMAIL + " TEXT,"+ "PRIMARY KEY("+KEY_CID+"),"
                + "FOREIGN KEY("+KEY_SUBJ+") REFERENCES "+ TABLE_COURSES +"("+KEY_SUBJ+") ON DELETE CASCADE,"
                + "FOREIGN KEY("+KEY_CODE+") REFERENCES "+ TABLE_COURSES +"("+KEY_CODE+") ON DELETE CASCADE"
                + ")";	
		
        db.execSQL(CREATE_COURSES);
        db.execSQL(CREATE_TASKS);
        db.execSQL(CREATE_OFFERINGS);
        db.execSQL(CREATE_OFFERING_TIMES);
        db.execSQL(CREATE_CONTACTS);
	}

	//upgrading the database will drop the table and recreate
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MCOURSES);
        // Create tables again
        onCreate(db);
	}
	
	//addCourse - adds information for a course into the database
	public void addCourse(){
		Brocku list = new Brocku();
		ArrayList<MasterCourse> course = new ArrayList<MasterCourse>();
		try{
		course = list.execute().get();
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MCOURSES);
		onCreate(db);
		//sets database for multiple line insert
		db.beginTransaction();
		for (int i=0; i<course.size(); i++){
			ContentValues values = new ContentValues();
			values.put(KEY_ID, course.get(i).id); // Course id
			values.put(KEY_SUBJ, course.get(i).subj); // subject code
			values.put(KEY_CODE, course.get(i).code);
			values.put(KEY_DESC, course.get(i).desc);
			values.put(KEY_TYPE, course.get(i).type);
			values.put(KEY_SEC, course.get(i).sec);
			values.put(KEY_DUR, course.get(i).dur);
			values.put(KEY_DAYS, course.get(i).days);
			values.put(KEY_TIME, course.get(i).time);
			values.put(KEY_LOCATION, course.get(i).location);
			values.put(KEY_INSTRUCTOR, course.get(i).instructor);	 
			// Inserting Row
			db.insert(TABLE_COURSES, null, values);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	    db.close(); // Closing database connection
		}catch(Exception e){};
	}
	
	//getCourses - returns a list of offerings for a particular subject and code
	public ArrayList<MasterCourse> getCourses(String subj, String code) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    ArrayList<MasterCourse> courseList = new ArrayList<MasterCourse>();
	    courseList.ensureCapacity(50);
	    MasterCourse course;
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MCOURSES + " where " + KEY_SUBJ + "= '" + subj + "' and " + KEY_CODE + " = '" + code + "'", null);
		//int i = 0;
		if (c != null){
			if (c.moveToFirst()){
				do{
					course = new MasterCourse();
					course.id = c.getString(c.getColumnIndex(KEY_ID));
					course.subj = c.getString(c.getColumnIndex(KEY_SUBJ));
					course.code = c.getString(c.getColumnIndex(KEY_CODE));
					course.desc = c.getString(c.getColumnIndex(KEY_DESC));
					course.type = c.getString(c.getColumnIndex(KEY_TYPE));
					course.sec = c.getString(c.getColumnIndex(KEY_SEC));
					course.dur = c.getString(c.getColumnIndex(KEY_DUR));
					course.days = c.getString(c.getColumnIndex(KEY_DAYS));
					course.time = c.getString(c.getColumnIndex(KEY_TIME));
					course.location = c.getString(c.getColumnIndex(KEY_LOCATION));
					course.instructor = c.getString(c.getColumnIndex(KEY_INSTRUCTOR));
					courseList.add(course);
				}while (c.moveToNext());
			}
		}
		c.close();
		return courseList;	    
	}
	
	//getSubjects - returns a list of subjects from the database
	public ArrayList<String> getSubjects(){
		//String subjects;
		ArrayList<String> subj = new ArrayList<String>();
		try{		
		SQLiteDatabase db = this.getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT DISTINCT "+KEY_SUBJ+" FROM " + TABLE_MCOURSES + " ORDER BY "+KEY_SUBJ+" ASC", null);

		if (c != null){
			if (c.moveToFirst()){
				do{					
					subj.add(c.getString(c.getColumnIndex(KEY_SUBJ)));					
				}while (c.moveToNext());
			}
		}
		 
		c.close();
		}catch(Exception e){subj.add(e.toString());}
		return subj;
	}
	
	//getCodes - returns a list of codes for a subject from the database
	public ArrayList<String> getCodes(String subj){
		ArrayList<String> codes = new ArrayList<String>();
		try{		
		SQLiteDatabase db = this.getReadableDatabase();		
		Cursor c = db.rawQuery("SELECT DISTINCT "+KEY_CODE+" FROM " + TABLE_MCOURSES+
				" WHERE "+KEY_SUBJ+"='"+subj+"' ORDER BY "+KEY_SUBJ+" ASC", null);
		if (c != null){
			if (c.moveToFirst()){
				do{					
					codes.add(c.getString(c.getColumnIndex(KEY_CODE)));					
				}while (c.moveToNext());
			}
		}
		c.close();
		}catch(Exception e){codes.add(e.toString());}
		return codes;
	}
	
	public int size(){
		int i=0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_M COURSES,null);
		if (c != null){
			c.moveToFirst();
			i = c.getInt(0);
		}
		return i;
	}
}