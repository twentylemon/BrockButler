/**
 * TourNode.java
 * Brock Butler
 * Simple abstract wrapper for TourRoom and TourHall.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package com.seaaddicts.brockbutler;


public abstract class TourNode {
	protected int image;		//resource for the image on this node
	protected TourNode turnAroundNode;
	protected String title;		//text for Toast to display, if any

	public boolean canTurnAround(){ return(turnAroundNode != null); }
	public TourNode getTurnAroundNode(){ return(turnAroundNode); }

	public void setTurnAroundNode(TourNode node){ turnAroundNode = node; }

	public void setOuterLeftNode(TourNode node){}
	public void setInnerLeftNode(TourNode node){}
	public void setCenterNode(TourNode node){}
	public void setInnerRightNode(TourNode node){}
	public void setOuterRightNode(TourNode node){}

	/**
	 * Changes the background image and redefines what the buttons do,
	 * ie where the buttons will take you.
	 * @param info - current tour state
	 */
	public abstract void paint(TourInfo info);
}
