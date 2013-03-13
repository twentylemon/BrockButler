/**
 * TourHall.java
 * Brock Butler
 * A hallway node in the tour. Hallways have multiple branching points,
 * and can turn around. Each hallway knows about all of it's branching points.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package com.seaaddicts.brockbutler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TourHall extends TourNode {
	protected TourNode[] nodes;	//each of the nodes this node branches off to
	
	/**
	 * Forward pass constructor. Defines each button, and inits the turn around
	 * location to null.
	 * @param img - the image resource value
	 * @param ll - outer left node
	 * @param ul - upper left node
	 * @param c - center node
	 * @param ur - upper right node
	 * @param lr - outer right node
	 */
	public TourHall(int img, TourNode ll, TourNode ul, TourNode c, TourNode ur, TourNode lr){
		image = img;
		nodes = new TourNode[5];
		nodes[0] = ll;
		nodes[1] = ul;
		nodes[2] = c;
		nodes[3] = ur;
		nodes[4] = lr;
		turnAroundNode = null;
		title = makeTitle(img);
	}

	/**
	 * Second pass constructor. Defines each button, and also defines the turn
	 * around node. Links `turnAroundNode` back to this node via turning around.
	 * @param ta - turn around node
	 */
	public TourHall(int img, TourNode ll, TourNode ul, TourNode c, TourNode ur, TourNode lr, TourNode ta){
		this(img,ll,ul,c,ur,lr);
		turnAroundNode = ta;
		turnAroundNode.setTurnAroundNode(this);
	}

	public void setOuterLeftNode(TourNode node){  nodes[0] = node; }
	public void setInnerLeftNode(TourNode node){  nodes[1] = node; }
	public void setCenterNode(TourNode node){     nodes[2] = node; }
	public void setInnerRightNode(TourNode node){ nodes[3] = node; }
	public void setOuterRightNode(TourNode node){ nodes[4] = node; }

	/**
	 * @param img - the resource value of the image of this node
	 * @return - the value for `title`
	 */
	private String makeTitle(int img){
		switch (img){
		case R.drawable.b303: return("B303");
		case R.drawable.c306_3: return("C306");
		case R.drawable.g301_1: return("G301");
		case R.drawable.h304: return("H304");
		case R.drawable.h306: return("H306");
		case R.drawable.h306a6: case R.drawable.h306a9: return("H306A");
		case R.drawable.h309: return("H309");
		case R.drawable.h309a6: case R.drawable.h309a9: return("H309A");
		case R.drawable.h318: return("H318");
		case R.drawable._a301f: case R.drawable._a301b:
			return("A Block");
		case R.drawable._b301f: case R.drawable._b301b: case R.drawable._b306b:
			return("B Block");
		case R.drawable._c301f: case R.drawable._c301b: case R.drawable._c313f:
			return("C Block");
		case R.drawable._d301f: case R.drawable._d301b: case R.drawable._d304b:
			return("D Block");
		case R.drawable._e301f: case R.drawable._e301b:
			return("E Block");
		case R.drawable._f301b: case R.drawable._f302f:
			return("F Block");
		case R.drawable._g301f:
			return("G Block");
		case R.drawable._h301f: case R.drawable._h301b:
			return("H Block");
		case R.drawable._j301f: case R.drawable._j312b:
			return("J Block");
		}
		return(null);
	}

	/**
	 * Changes the image displayed on the screen and redefines where the buttons lead us to.
	 * Also pushes this node onto the TourInfo's history.
	 */
	@Override
	public void paint(final TourInfo info){
		info.rl.setBackgroundResource(image);
		info.current = this;
		if (title != null)
			Toast.makeText(info.context,title,Toast.LENGTH_SHORT).show();
		for (int i = 0; i < nodes.length; i++){
			final int idx = i;
			info.buttons[idx].setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					if (nodes[idx] != null){
						info.history.push(TourHall.this);
						nodes[idx].paint(info);
					}
				}
			});
			int resID = (nodes[idx] == null)? R.drawable.qb_empty : info.arrows[idx];
			info.buttons[idx].setImageResource(resID);
		}
	}
}
