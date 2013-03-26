package edu.seaaddicts.brockbutler.maps;

/**
 * Astar.java
 * Brock Butler
 * Path finding algorithm for navigation route
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */

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
	
	private DatabaseHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;
    private Position[] graph;

    
    /**
	 * Constructor method for the ASTAR class. The constructor
	 * is given the context of the calling activity as an argument
	 * then opens and sets up the database connection.
	 */ 
    public Astar (Context c) {
    	ourContext = c;
    	ourHelper = new DatabaseHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        
        Cursor cur = ourDatabase.rawQuery("SELECT DISTINCT node_id, desc, x, y FROM node_connections", null);
    	graph = new Position[cur.getCount()];
    	if (cur.moveToFirst()) {
    		int i = 0;
    		do {
    			graph[i] = new Position(cur.getInt(cur.getColumnIndex(KEY_XPOS)),cur.getInt(cur.getColumnIndex(KEY_YPOS)),cur.getString(cur.getColumnIndex(KEY_DESC)),cur.getString(cur.getColumnIndex(KEY_NODE)));
    			i++;
    		} while (cur.moveToNext());
    	}
    	
    	for(int i=0; i<graph.length; i++) {
    		String[] columns = new String[]{KEY_NODE,KEY_DESC,KEY_XPOS,KEY_YPOS,KEY_CONN};
        	cur = ourDatabase.query(DATABASE_TABLE, columns, KEY_CONN+"=?", new String[]{graph[i].nodeNumber}, null, null, null);
        	if (cur.moveToFirst()) {
        		graph[i].accesible = new Position[cur.getCount()];
        		int k=0;
        		do {
        			graph[i].accesible[k] = new Position(cur.getInt(cur.getColumnIndex(KEY_XPOS)), cur.getInt(cur.getColumnIndex(KEY_YPOS)), cur.getString(cur.getColumnIndex(KEY_DESC)), cur.getString(cur.getColumnIndex(KEY_NODE)));
        			for(int j=0; j<graph.length; j++) {
        				if(graph[i].accesible[k].compare(graph[j]))
        					graph[i].accesible[k] = graph[j];
        			}
        			k++;
        		} while(cur.moveToNext());
        	}
    	}
    	Log.d("PRINT", "Graph Built");
    }
    
    /**
	 * This method returns true or false if a given position exists
	 * within the graph(school). This method is to be used before pathGeneration.
	 */ 
    public boolean nodeExist(Position node) {
    	for(int j=0; j<graph.length; j++) {
			if(node.compare(graph[j]))
				return true;
		}
    	return false;
    }
    
    /**
	 * This method returns a position if it exists within
	 * the graph(school). Used by the interface searching 
	 * feature.
	 */ 
    public Position findPosition(String nodeName) {
    	for(int j=0; j<graph.length; j++) {
			if(nodeName.equals(graph[j].nodeName))
				return graph[j];
		}
    	return null;
    }
    
    
    /**
	 * The pathGeneration method is the main part of the A* algorithm. This method
	 * achieves an efficient and route between two positions based on a heuristic 
	 * score.
	 */ 
    public Position[] pathGeneration(Position startNode, Position goalNode)  {
    	for(int j=0; j<graph.length; j++) {
			if(startNode.compare(graph[j]))
				startNode = graph[j];
			if(goalNode.compare(graph[j]))
				goalNode = graph[j];
		}
    	
    	if(startNode.accesible.length == 0 || goalNode.accesible.length == 0)
    		return null;
    	
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
}
