/**
 * Astar.java
 * Brock Butler
 * Path finding algorithm for navigation route
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */

package com.excerebros.locationtest;

import java.util.ArrayList;
import java.util.PriorityQueue;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Astar {
	
	/**
	 * Class variable for the ASTAR class. All are private variables
	 * as they are all atributited to the database helper and 
	 * are only used by this class.
	 */
	private static final String KEY_NODE = "node_id";
	private static final String KEY_DESC = "desc";
	private static final String KEY_XPOS = "x";
	private static final String KEY_YPOS = "y";
	private static final String KEY_CONN = "con";
	private static final String DATABASE_TABLE = "node_connections";
	
	private DatabaseHarness ourHelper;
    private final Context   ourContext;
    private SQLiteDatabase  ourDatabase;

    
    /**
	 * Constructor method for the ASTAR class. The constructor
	 * is given the context of the calling activity as an argument
	 * then opens and sets up the database connection.
	 */ 
    public Astar (Context c) {
    	ourContext = c;
    	ourHelper = new DatabaseHarness(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
    }
    
    
    /**
	 * The pathGeneration method is the main part of the A* algorithm. This method
	 * achieves an efficient and route between two positions based on a huristic 
	 * score.
	 */ 
    public Position[] pathGeneration(Position startNode, Position goalNode)  {
        PriorityQueue<Position> openList = new PriorityQueue<Position>();
        openList.add(startNode);
        
        while(openList.size() != 0)  {
        	Position tempNode = openList.remove();
            
            if(tempNode == goalNode) 
            	return pathReturn(tempNode);
            
            tempNode.visited = true;
            tempNode.gScore  = 0;
            
            double tempX = tempNode.xPosition - goalNode.xPosition;
            double tempY = tempNode.yPosition - goalNode.yPosition;
            tempNode.hScore = Math.sqrt(tempX*tempX + tempY*tempY);

            tempNode = getAdjacent(tempNode);
            for(int i=0; i<tempNode.accesible.length; i++) {
            	Position adjacentNode = tempNode.accesible[i];
            	
                if(adjacentNode.visited) 
                	continue;
                
                double tempG = tempNode.gScore;
                double x = tempNode.xPosition - adjacentNode.xPosition;
                double y = tempNode.yPosition - adjacentNode.yPosition;
                double dist = Math.sqrt(x*x + y*y);
                tempG += dist;
               
                if(tempG < adjacentNode.gScore ) {
                	openList.remove(adjacentNode);
                	adjacentNode.from = tempNode;
                	adjacentNode.gScore = tempG;

                	tempX = adjacentNode.xPosition - goalNode.xPosition;
                	tempY = adjacentNode.yPosition - goalNode.yPosition;


                	adjacentNode.hScore = Math.sqrt(tempX*tempX + tempY*tempY);
                	adjacentNode.fScore = adjacentNode.gScore + adjacentNode.hScore;
                	adjacentNode.visited = true;
                    openList.add(adjacentNode);
                }

            }
        }
        return null;
    }
    
    
    /**
	 * This method takes the generated route from the A* algorithm
	 * and processes it into a usable ArrayList of positions to be 
	 * passed to the mapping activity for drawing a route on the
	 * map.
	 */ 
    public Position[] pathReturn(Position end) throws ClassCastException {
        ArrayList<Position> routeList = new ArrayList<Position>();
        routeList.add(end);

        while(end.from != null) {
            end = end.from;
            routeList.add(end);
        }
        
        Object tempRoute[] = (routeList.toArray());
        Position route[] = new Position[tempRoute.length];
        
        for(int i=0; i<route.length; i++) {
            route[i] = (Position)(tempRoute[route.length - i-1]);
        }
        
        return route;
    }
    
    
    /**
	 * This method is invoked to close the connection to the database once the
	 * path has been generated.
	 */ 
    public void close(){
        ourHelper.close();
    }
    
    
    /**
	 * This method is used by the A* algorithm to return all adjacent nodes
	 * to the current node. Searches the SQLite database for node connections.
	 */ 
    public Position getAdjacent(Position node) {
    	String[] columns = new String[]{KEY_NODE,KEY_DESC,KEY_XPOS,KEY_YPOS,KEY_CONN};
    	Cursor cur = ourDatabase.query(DATABASE_TABLE, columns, KEY_CONN+"=?", new String[]{node.nodeNumber}, null, null, null);
    	if (cur.moveToFirst()) {
    		node.accesible = new Position[cur.getCount()];
    		for(int i=0; i<cur.getCount(); i++) {
    			cur.moveToNext();
    			node.accesible[i].nodeNumber = cur.getString(cur.getColumnIndex(KEY_NODE));
    			node.accesible[i].nodeName = cur.getString(cur.getColumnIndex(KEY_DESC));
    			node.accesible[i].xPosition = cur.getInt(cur.getColumnIndex(KEY_XPOS));
    			node.accesible[i].yPosition = cur.getInt(cur.getColumnIndex(KEY_YPOS));
    		}
    	} else {
    		Log.e("ASTAR CLASS", "getAdjacent: Empty return on query");
    	}
    	return node;
    }
    
    
    /**
	 * Testing methods for the ASTAR class. These methods are provided
	 * for testing and debugging purposes capable of printing information 
	 * to the log.
	 */ 
    public void printTable () {
    	Cursor cur = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
    	if (cur.moveToFirst()) {
    		while (cur.moveToNext())
    			Log.d("ASTAR CLASS", cur.getString(cur.getColumnIndex(KEY_NODE)) + ", " + cur.getInt(cur.getColumnIndex(KEY_XPOS)) + ", " + cur.getInt(cur.getColumnIndex(KEY_YPOS)));
    	} else {
    		Log.e("ASTAR CLASS", "printTable: Empty return on query");
    	}
    }
    
    public void printAdjacent(Position node) {
    	String[] columns = new String[]{KEY_NODE, KEY_DESC, KEY_XPOS, KEY_YPOS, KEY_CONN};
    	Cursor cur = ourDatabase.query(DATABASE_TABLE, columns, KEY_CONN+"=?", new String[]{node.nodeNumber}, null, null, null);
    	if (cur.moveToFirst()) {
    		while (cur.moveToNext())
    			Log.d("ASTAR CLASS", cur.getString(cur.getColumnIndex(KEY_NODE)) + ", " + cur.getInt(cur.getColumnIndex(KEY_XPOS)) + ", " + cur.getInt(cur.getColumnIndex(KEY_YPOS)));
    	} else {
    		Log.e("ASTAR CLASS", "printAdjacent: Empty return on query");
    	}
    }
}
