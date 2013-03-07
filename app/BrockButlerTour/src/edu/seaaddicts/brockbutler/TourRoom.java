/**
 * TourRoom.java
 * Brock Butler
 * A room node in the tour. Rooms are simple, they don't go anywhere.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package edu.seaaddicts.brockbutler;

import android.view.View;
import android.view.View.OnClickListener;

public class TourRoom extends TourNode {

	/**
	 *  Initializes this room, rooms only need the image resource value
	 * @param img - the resource value of the image of this room
	 */
	public TourRoom(int img){
		image = img;
		nodes = null;
		turnAroundNode = null;
	}

	@Override
	protected void paint(TourInfo info) {
		info.rl.setBackgroundResource(image);
		info.current = this;
		for (int i = 0; i < info.buttons.length; i++){
			info.buttons[i].setOnClickListener(new OnClickListener(){
				public void onClick(View v){}
			});
		}
	}
}
