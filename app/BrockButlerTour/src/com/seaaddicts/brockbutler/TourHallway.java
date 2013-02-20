package com.seaaddicts.brockbutler;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TourHallway extends TourNode {
	private TourNode[] nodes;
	
	/**
	 * Forward pass constructor. Defines each button, and inits the turn around
	 * location to null.
	 * @param img - the image resource value
	 * @param ul - upper left node
	 * @param ll - lower left node
	 * @param c - center node
	 * @param ur - upper right node
	 * @param lr - lower right node
	 */
	public TourHallway(int img, TourNode ul, TourNode ll, TourNode c, TourNode ur, TourNode lr){
		image = img;
		nodes = new TourNode[5];
		nodes[0] = ul;
		nodes[1] = ll;
		nodes[2] = c;
		nodes[3] = ur;
		nodes[4] = lr;
		turnAroundNode = null;
	}
	
	/**
	 * Simplified forward pass constructor. Inits non-center nodes to null.
	 * @param img - image resource value
	 * @param c - the center node
	 */
	public TourHallway(int img, TourNode c){
		this(img,null,null,c,null,null);
	}

	/**
	 * Second pass constructor. Defines each button, and also defines the turn
	 * around node. Links `turnAroundNode` back to this node via turning around.
	 * @param ta - turn around node
	 */
	public TourHallway(int img, TourNode ul, TourNode ll, TourNode c, TourNode ur, TourNode lr, TourNode ta){
		this(img,ul,ll,c,ur,lr);
		turnAroundNode = ta;
		turnAroundNode.setTurnAroundNode(this);
	}
	
	/**
	 * Simplified second pass constructor. Inits ul,ll,ur,lr to null.
	 * @param img - image resource value
	 * @param c - the center node
	 * @param ta - turn around node
	 */
	public TourHallway(int img, TourNode c, TourNode ta){
		this(img,null,null,c,null,null,ta);
	}

	@Override
	protected void paint(final RelativeLayout rl, final ImageButton[] buttons, final Context c) {
		rl.setBackgroundResource(image);
		for (int i = 0; i < nodes.length; i++){
			final int idx = i;
			buttons[i].setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					if (nodes[idx] != null)
						nodes[idx].paint(rl, buttons, c);
					else
						Toast.makeText(c, "No data available", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

}
