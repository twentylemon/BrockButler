/**
 * TourHall.java
 * Brock Butler
 * A hallway node in the tour. Hallways have multiple branching points,
 * and can turn around. Each hallway knows about all of it's branching points.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package edu.seaaddicts.brockbutler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TourHall extends TourNode {
	
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
	 * Changes the image displayed on the screen and redefines where the buttons lead us to.
	 * Also pushes this node onto the TourInfo's history.
	 */
	@Override
	protected void paint(final TourInfo info){
		info.rl.setBackgroundResource(image);
		info.current = this;
		for (int i = 0; i < nodes.length; i++){
			final int idx = i;
			info.buttons[idx].setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					if (nodes[idx] != null){
						info.history.push(TourHall.this);
						nodes[idx].paint(info);
					}
					else
						Toast.makeText(info.context, "No data available", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
