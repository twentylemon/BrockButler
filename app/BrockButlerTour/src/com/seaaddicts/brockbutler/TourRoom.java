/**
 * TourRoom.java
 * Brock Butler
 * A room node in the tour. Rooms are simple, they don't go anywhere.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package com.seaaddicts.brockbutler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TourRoom extends TourNode {

	/**
	 * Initializes this room, rooms only need the image resource value
	 * @param img - the resource value of the image of this room
	 */
	public TourRoom(int img, String s){
		image = img;
		title = s;
		turnAroundNode = null;
	}

	@Override
	public void paint(TourInfo info) {
		info.rl.setBackgroundResource(image);
		info.current = this;
		if (title != null)	//should never be null, but just in case
			Toast.makeText(info.context,title,Toast.LENGTH_SHORT).show();
		for (int i = 0; i < info.buttons.length; i++){
			info.buttons[i].setOnClickListener(new OnClickListener(){
				public void onClick(View v){}
			});
			info.buttons[i].setImageResource(R.drawable.qb_empty);
		}
	}
}
