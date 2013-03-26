package edu.seaaddicts.brockbutler.maps;

/**
 * Position.java
 * Brock Butler
 * Type for holding Position node 
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Position implements Comparable<Object> {
	
	/**
	 * Class variable for the POSITION class. All are public
	 * to avoid using get/set variables to increase performance
	 */ 
	public int      xPosition;
	public int      yPosition;
	public double   fScore;
	public double   gScore;
	public double   hScore;
	public String   nodeNumber;
	public String   nodeName;
	public boolean  visited;
    public Position from;
    public Position accesible[];
    public Position nonaccesible[];
	
	/**
	 * Constructor methods for the POSITION class. The constructor
	 * is overloaded 3 times for 3 different way to initialize a 
	 * POSITION.
	 */ 
	public Position ( ) {
		xPosition = 0;
		yPosition = 0;
		
		nodeNumber = "";
		nodeName   = "";
		
		fScore = Double.MAX_VALUE;
        gScore = Double.MAX_VALUE;
        hScore = -1;
        
        visited = false;
        from    = null;
	}
	
	public Position (int inputX, int inputY) {
		xPosition = inputX;
		yPosition = inputY;
		
		nodeNumber = "";
		nodeName   = "";
		
		fScore = Double.MAX_VALUE;
		gScore = Double.MAX_VALUE;
		hScore = Double.MAX_VALUE;
        
		visited = false;
        from    = null;
	}
	
	public Position (int inputX, int inputY, String inputName, String inputNumber) {
		xPosition = 0;
		yPosition = 0;
		
		nodeNumber = inputNumber;
		nodeName   = inputName;
		
		fScore = Double.MAX_VALUE;
		gScore = Double.MAX_VALUE;
		hScore = Double.MAX_VALUE;
        
		visited = false;
        from    = null;
	}

	
	/**
	 * Methods for the POSITION class. These methods are provided to
	 * get and set the class variables although for performance reasons they
	 * will be used very rarely. The final method is able to compare this position
	 * to another with a boolean return.
	 */ 
	public void setCoordinates (int inputX, int inputY) {
		xPosition  = inputX;
		yPosition  = inputY;
	}
	
	public void setNumber (String inputNumber) {
		nodeNumber  = inputNumber;
	}
	
	public void setName (String inputName) {
		nodeName  = inputName;
	}
	
	public int getX ( ) {
		return xPosition;
	}
	
	public int getY ( ) {
		return yPosition;
	}
	
	public String getNumber ( ) {
		return nodeNumber;
	}
	
	public String getName ( ) {
		return nodeName;
	}
	
	public boolean compare (Position node) {
		if(this.xPosition == node.xPosition && this.yPosition == node.yPosition && this.nodeNumber.equals(node.nodeNumber) && this.nodeName.equals(node.nodeName))
			return true;
		return false;
	}
	
	@Override
	public int compareTo (Object node) {
		Position temp = (Position) node;
        if(this.xPosition == temp.xPosition && this.yPosition == temp.yPosition && this.nodeNumber.equals(temp.nodeNumber) && this.nodeName.equals(temp.nodeName))
			return true;
		return false;
	}
	
	/**
	 * The following method is used only to search coordinates
	 * from a room number
	 */
	public void fillFromName(Context ourContext) {
		DatabaseHelper ourHelper = new DatabaseHelper(ourContext);
	    SQLiteDatabase ourDatabase = ourHelper.getWritableDatabase();
	    
	    String[] columns = new String[]{"node_id","x","y"};
    	Cursor cur = ourDatabase.query("node_connections", columns, "desc"+"=?", new String[]{nodeName}, null, null, null);
    	if (cur.moveToFirst()) {
    		nodeNumber = cur.getString(cur.getColumnIndex("node_id"));
    		xPosition = cur.getInt(cur.getColumnIndex("x"));
    		yPosition = cur.getInt(cur.getColumnIndex("y"));
    	} else {
    		Log.e("POSITION CLASS", "Can't find room data");
    	}
	    
	    ourHelper.close();
	}
	
	/**
	 * gathers all metadata for a node based off of the
	 * coordinates
	 */
	public void fillFromCoordinates(Context ourContext) {
		DatabaseHelper ourHelper = new DatabaseHelper(ourContext);
	    SQLiteDatabase ourDatabase = ourHelper.getWritableDatabase();
	    
	    String[] columns = new String[]{"node_id","desc"};
    	Cursor cur = ourDatabase.query("node_connections", columns, "x=? AND y=?", new String[]{Integer.toString(xPosition),Integer.toString(yPosition)}, null, null, null);
    	if (cur.moveToFirst()) {
    		nodeNumber = cur.getString(cur.getColumnIndex("node_id"));
    		nodeName = cur.getString(cur.getColumnIndex("desc"));
    	} else {
    		Log.e("POSITION CLASS", "Can't find position data");
    	}
	    
	    ourHelper.close();
	}
	
	
	/**
	 * Testing methods for the POSITION class. These methods are provided
	 * for testing and debugging purposes capable of printing variables to the log
	 */ 
	public void printCoordinates ( ) {
		Log.d("POSITION CLASS", "Coordinates: (" + xPosition + "," + yPosition + ")");
	}
	
	public void printNumber ( ) {
		Log.d("POSITION CLASS", "Node Number: " + nodeNumber);
	}
	
	public void printName ( ) {
		Log.d("POSITION CLASS", "Node Name: " + nodeName);
	}
}