package com.seaaddicts.brockbutler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TourHall extends TourNode {
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
	public TourHall(int img, TourNode ul, TourNode ll, TourNode c, TourNode ur, TourNode lr){
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
	public TourHall(int img, TourNode c){
		this(img,null,null,c,null,null);
	}

	/**
	 * Second pass constructor. Defines each button, and also defines the turn
	 * around node. Links `turnAroundNode` back to this node via turning around.
	 * @param ta - turn around node
	 */
	public TourHall(int img, TourNode ul, TourNode ll, TourNode c, TourNode ur, TourNode lr, TourNode ta){
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
	public TourHall(int img, TourNode c, TourNode ta){
		this(img,null,null,c,null,null,ta);
	}

	/**
	 * paint(TourInfo)
	 * Changes the image displayed on the screen and redefines where the buttons lead us to.
	 * Also pushes this node onto the TourInfo's history.
	 */
	@Override
	protected void paint(final TourInfo info) {
		info.rl.setBackgroundResource(image);
		info.current = this;
		for (int i = 0; i < nodes.length; i++){
			final int idx = i;
			info.buttons[i].setOnClickListener(new OnClickListener(){
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
