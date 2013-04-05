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
import android.util.Log;

public class Astar {
	
	/**
	 * Class variable for the ASTAR class. All are private variables
	 * as they are all atributited to the database helper and 
	 * are only used by this class.
	 */
    private Position[] graph;
    
    /**
     * This method returns true or false if a given position exists
	 * within the graph(school). This method is to be used before pathGeneration. 
     * @param node
     * @return
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
     * @param nodeName
     * @return
     */
    public Position findPosition(String nodeName) {
    	for(int j=0; j<graph.length; j++) {
			if(nodeName.equals(graph[j].nodeNumber))
				return graph[j];
		}
    	return null;
    }
    
    
    /**
     * The pathGeneration method is the main part of the A* algorithm. This method
	 * achieves an efficient and route between two positions based on a heuristic 
	 * score. 
     * @param startNode
     * @param goalNode
     * @return
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
     * @param end
     * @return
     * @throws ClassCastException
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
	 * Constructor method for the ASTAR class. The constructor
	 * creates the searchable graph space that represents the
	 * school hallways and classrooms.
	 */
    public Astar () {
    	
    	graph = new Position[28];
    	
    	graph[0] = new Position(1732,687,"J Block","J01");
    	graph[1] = new Position(1763,688,"J Block","J02");
    	graph[2] = new Position(1775,698,"J Block","J03");
    	graph[3] = new Position(1799,723,"J Block","J04");
    	graph[4] = new Position(1724,677,"J Block","J05");
    	graph[5] = new Position(1789,663,"J Block","J06");
    	graph[6] = new Position(1805,648,"J Block","J07");
    	graph[7] = new Position(1819,637,"J Block","J08");
    	graph[8] = new Position(1832,625,"J Block","J09");
    	graph[9] = new Position(1846,613,"J Block","J10");
    	graph[10] = new Position(1836,600,"J Block","J11");
    	graph[11] = new Position(1818,583,"J Block","J12");
		graph[12] = new Position(1858,624,"J Block","J13");
		graph[13] = new Position(1873,638,"J Block","J14");
		graph[14] = new Position(1886,652,"J Block","J15");
		graph[15] = new Position(1901,666,"J Block","J16");
		graph[16] = new Position(1906,684,"J Block","J17");
		graph[17] = new Position(1902,705,"J Block","J18");
		graph[18] = new Position(1886,721,"J Block","J19");
		graph[19] = new Position(1868,738,"J Block","J20");
    	graph[20] = new Position(1856,752,"J Block","J21");
    	graph[21] = new Position(1836,771,"J Block","J22");
    	graph[22] = new Position(1818,789,"J Block","J23");
    	graph[23] = new Position(1845,714,"J310","J310");
    	graph[24] = new Position(1791,765,"J301","J301");
    	graph[25] = new Position(1831,747,"J304","J304");
    	graph[26] = new Position(1841,651,"J328","J328");
    	graph[27] = new Position(1799,604,"J327","J327");

    	
    		graph[0].accesible = new Position[1];
    		graph[0].accesible[0] = graph[1];
    	
    		graph[1].accesible = new Position[3];
    		graph[1].accesible[0] = graph[0];
    		graph[1].accesible[1] = graph[2];
    		graph[1].accesible[2] = graph[4];
    	
    		graph[2].accesible = new Position[2];
    		graph[2].accesible[0] = graph[1];
    		graph[2].accesible[1] = graph[3];
    	
    		graph[3].accesible = new Position[3];
    		graph[3].accesible[0] = graph[2];
    		graph[3].accesible[1] = graph[24];
    		graph[3].accesible[2] = graph[23];

    		graph[4].accesible = new Position[2];
			graph[4].accesible[0] = graph[1];
			graph[4].accesible[1] = graph[5];
    	
    		graph[5].accesible = new Position[2];
			graph[5].accesible[0] = graph[4];
			graph[5].accesible[1] = graph[6];
    	
    		graph[6].accesible = new Position[4];
			graph[6].accesible[0] = graph[5];
			graph[6].accesible[1] = graph[7];
			graph[6].accesible[2] = graph[27];
			graph[6].accesible[3] = graph[26];
    	
    		graph[7].accesible = new Position[2];
			graph[7].accesible[0] = graph[6];
			graph[7].accesible[1] = graph[8];
    	
    		graph[8].accesible = new Position[2];
    		graph[8].accesible[0] = graph[7];
    		graph[8].accesible[1] = graph[9];
    	
    		graph[9].accesible = new Position[3];
			graph[9].accesible[0] = graph[8];
			graph[9].accesible[1] = graph[10];
			graph[9].accesible[2] = graph[12];
			
    		graph[10].accesible = new Position[2];
    		graph[10].accesible[0] = graph[9];
    		graph[10].accesible[1] = graph[11];
    	
    		graph[11].accesible = new Position[1];
    		graph[11].accesible[0] = graph[10];
    	
    		graph[12].accesible = new Position[2];
    		graph[12].accesible[0] = graph[9];
    		graph[12].accesible[1] = graph[13];
    	
    		graph[13].accesible = new Position[2];
    		graph[13].accesible[0] = graph[12];
			graph[13].accesible[1] = graph[14];
    	
    		graph[14].accesible = new Position[2];
    		graph[14].accesible[0] = graph[13];
    		graph[14].accesible[1] = graph[15];
    	
    		graph[15].accesible = new Position[2];
    		graph[15].accesible[0] = graph[14];
    		graph[15].accesible[1] = graph[16];
    	
    		graph[16].accesible = new Position[2];
    		graph[16].accesible[0] = graph[15];
    		graph[16].accesible[1] = graph[17];
    	
    		graph[17].accesible = new Position[2];
    		graph[17].accesible[0] = graph[16];
    		graph[17].accesible[1] = graph[18];
    	
    		graph[18].accesible = new Position[2];
    		graph[18].accesible[0] = graph[17];
    		graph[18].accesible[1] = graph[19];
    	
    		graph[19].accesible = new Position[2];
    		graph[19].accesible[0] = graph[18];
    		graph[19].accesible[1] = graph[20];
    	
    		graph[20].accesible = new Position[3];
    		graph[20].accesible[0] = graph[19];
    		graph[20].accesible[1] = graph[21];
    		graph[20].accesible[2] = graph[23];
    	
    		graph[21].accesible = new Position[2];
    		graph[21].accesible[0] = graph[20];
    		graph[21].accesible[1] = graph[22];
    	
    		graph[22].accesible = new Position[2];
    		graph[22].accesible[0] = graph[21];
    		graph[22].accesible[1] = graph[24];
    		
    		graph[23].accesible = new Position[3];
    		graph[23].accesible[0] = graph[3];
    		graph[23].accesible[1] = graph[20];
    		graph[23].accesible[2] = graph[25];
    		
    		graph[24].accesible = new Position[3];
    		graph[24].accesible[0] = graph[3];
    		graph[24].accesible[1] = graph[22];
    		graph[24].accesible[2] = graph[25];
    		
    		graph[25].accesible = new Position[2];
    		graph[25].accesible[0] = graph[23];
    		graph[25].accesible[1] = graph[24];
    		
    		graph[26].accesible = new Position[1];
    		graph[26].accesible[0] = graph[6];
    		
    		graph[27].accesible = new Position[1];
    		graph[27].accesible[0] = graph[6];
    		
    	Log.i("ASTAR", "Graph Built of size " + graph.length);
    }
}
