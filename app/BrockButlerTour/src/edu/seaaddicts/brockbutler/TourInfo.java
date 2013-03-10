/**
 * TourInfo.java
 * Brock Butler
 * Wrapper class for all passing required information around throughout the tour.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package edu.seaaddicts.brockbutler;

import java.util.Stack;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class TourInfo {
	public RelativeLayout rl;
	public ImageButton[] buttons;
	public int[] arrows;
	public Context context;
	public TourNode current;
	public Stack<TourNode> history;

	/**
	 * @param r - the layout of which the background is changed from node to node
	 * @param b - array of ImageButtons where one would tap to change nodes
	 * @param c - getApplicationContext(), for toasts
	 */
	public TourInfo(RelativeLayout r, ImageButton[] b, Context c){
		rl = r;
		buttons = b;
		context = c;
		current = null;
		history = new Stack<TourNode>();
		arrows = new int[5];
		arrows[0] = R.drawable.qb_outerleft;
		arrows[1] = R.drawable.qb_innerleft;
		arrows[2] = R.drawable.qb_center;
		arrows[3] = R.drawable.qb_innerright;
		arrows[4] = R.drawable.qb_outerright;
	}
}
