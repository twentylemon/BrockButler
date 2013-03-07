package edu.seaaddicts.brockbutler.map;

import android.util.Log;

public class Position {
	
	/**
	 * Class variable for the POSITION class. All are public
	 * to avoid using get/set variables to increase performance
	 */ 
	public int    xPosition;
	public int    yPosition;
	public String nodeNumber;
	public String nodeName;
	
	
	/**
	 * Constructor methods for the POSITION class. The constructor
	 * is overloaded 3 times for 3 different way to initialize a 
	 * POSITION.
	 */ 
	public Position ( ) {
		xPosition  = 0;
		yPosition  = 0;
		nodeNumber = "";
		nodeName   = "";
	}
	
	public Position (int inputX, int inputY) {
		xPosition  = inputX;
		yPosition  = inputY;
		nodeNumber = "";
		nodeName   = "";
	}
	
	public Position (String inputNumber) {
		xPosition  = 0;
		yPosition  = 0;
		nodeNumber = inputNumber;
		nodeName   = "";
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
	
	
	/**
	 * Testing methods for the POSITION class. These methods are provided
	 * for testing and debugging purposes capable of printing variables to the log
	 */ 
	public void printCoordinates ( ) {
		Log.d("POSITION", "Coordinates: (" + xPosition + "," + yPosition + ")");
	}
	
	public void printNumber ( ) {
		Log.d("POSITION", "Node Number: " + nodeNumber);
	}
	
	public void printName ( ) {
		Log.d("POSITION", "Node Name: " + nodeName);
	}
}
