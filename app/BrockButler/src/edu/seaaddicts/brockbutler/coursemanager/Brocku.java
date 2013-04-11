/** 
 * Brocku.java
 * Brock Butler
 * Connects to the Brock University registrars' website to obtain course
 * information from the current timetable
 * Created by James Grisdale on 2013-02-24
 * * Copyright (c) 2013 Sea Addicts. All rights reserved.
**/

package edu.seaaddicts.brockbutler.coursemanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.HttpResponse;
import android.os.AsyncTask;
//Using AsyncTask to do operations off the main thread
public class Brocku extends AsyncTask<Void, Void, ArrayList<MasterCourse>> {

	/* doInBackground - connects to Brock University's registrar's office website to gather
	 * information on courses being offered, then returns an arraylist of MasterCourse
	 * objects which hold the data for all offerings at Brock.
	 */
	protected ArrayList<MasterCourse> doInBackground(Void... Void) {
		String codes[] = new String[74];
		BufferedReader in = null;
		String info = new String();
		String substring = new String();
		MasterCourse course;
		boolean done;
		ArrayList<MasterCourse> courseList = new ArrayList<MasterCourse>();
		courseList.ensureCapacity(8000);
		//test = "working";
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			URI BTimeTable = new URI("http://www.brocku.ca/registrar/guides/returning/timetable/a_get_subj.php?subj=COSC");
			request.setURI(BTimeTable);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			for (int i=0; i<3; i++) in.readLine();
			for (int i=0; i<74; i++){
				info = in.readLine();
				codes[i] = info.substring(19,23);
			}//retrieving all subjects
			in.close();
			for (int h=0; h<codes.length ; h++){
				done = false;
				BTimeTable = new URI("http://www.brocku.ca/registrar/guides/returning/timetable/a_get_subj.php?subj="+codes[h]);
				request.setURI(BTimeTable);
				response = client.execute(request);
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				for (int i=0; i<98; i++) {in.readLine();}
				info = in.readLine();
				//for each subjects get all course offering information
				if (info.length()<50){
					while(!done){
						course = new MasterCourse();				
						substring = info.substring(24, info.length() - 5);
						course.id = substring;
						for (int i=0; i<2; i++) {in.readLine();}
						info = in.readLine();
						substring = info.substring(123, 127);
						course.subj = substring;
						substring = info.substring(128, 132);
						course.code = substring;
						in.readLine();
						info = in.readLine();
						substring = info.substring(26,info.length() - 7);
						course.desc = substring;
						for (int i=0; i<4; i++) {in.readLine();}
						info = in.readLine();
						substring = info.substring(24,26);
						course.dur = substring;
						info = in.readLine();
						substring = info.substring(24,info.length() - 5);
						course.type = substring;
						info = in.readLine();
						substring = info.substring(24,info.length() - 5);
						course.sec = substring;
						info = in.readLine();
						substring = info.substring(24,30);
						course.days = substring;
						info = in.readLine();
						substring = info.substring(24,info.length() - 5);
						course.time = substring;
						info = in.readLine();				
						substring = info.substring(24,info.length() - 5);
						if (info.substring(24,26).equals("<a"))
							substring = info.substring(94, info.length()-9);
						course.location = substring;
						info = in.readLine();
						if (info.length()>16)
							substring = info.substring(9,info.length() - 5);
						else substring = " ";
						course.instructor = substring;
						in.readLine();
						in.readLine();
						info = in.readLine();
						courseList.add(course);
						if (info.length() <20){
							info = in.readLine();
							done = true;
						}				
					}
				}
				in.close();
			}
		}
		//if there's an error return the error information in a course object
		catch (Exception e){
		info = e.toString();
		course = new MasterCourse();
		course.id = info;
		courseList.add(course);
		}	
		return courseList;		
	}
	
	//not used
	protected void onPostExecute(MasterCourse course){
		posttest(course);
	}

	//not used
	public MasterCourse posttest(MasterCourse course){
		return course;
	}
}
