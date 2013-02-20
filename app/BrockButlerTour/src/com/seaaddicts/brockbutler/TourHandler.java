package com.seaaddicts.brockbutler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class TourHandler extends Activity {
	private RelativeLayout rl;
	private ImageButton[] buttons;
	private TourNode[] nodes;
	private final int numImages = 302;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_handler);
		context = getApplicationContext();
		rl = (RelativeLayout)findViewById(R.id.screen);
		buttons = new ImageButton[5];
		buttons[0] = (ImageButton)findViewById(R.id.lowerleft);
		buttons[1] = (ImageButton)findViewById(R.id.upperleft);
		buttons[2] = (ImageButton)findViewById(R.id.center);
		buttons[3] = (ImageButton)findViewById(R.id.upperright);
		buttons[4] = (ImageButton)findViewById(R.id.lowerright);
		initNodes();
		nodes[idx(R.drawable._j301f)].paint(rl,buttons,context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour_handler, menu);
		return true;
	}
	
	/**
	 * void initNodes()
	 * Giant method to link all of the images together. The fun stuff. Right here.
	 */
	private void initNodes(){
		nodes = new TourNode[numImages];
		/** J BLOCK **/
		nodes[idx(R.drawable._j315f)] = new TourHallway(R.drawable._j315f,null);
		nodes[idx(R.drawable._j314f)] = new TourHallway(R.drawable._j314f,nodes[idx(R.drawable._j315f)]);
		nodes[idx(R.drawable._j313f)] = new TourHallway(R.drawable._j313f,nodes[idx(R.drawable._j314f)]);
		nodes[idx(R.drawable._j312f)] = new TourHallway(R.drawable._j312f,null,null,null,nodes[idx(R.drawable._j313f)],null);
		nodes[idx(R.drawable._j311f)] = new TourHallway(R.drawable._j311f,nodes[idx(R.drawable._j312f)]);
		nodes[idx(R.drawable._j310f)] = new TourHallway(R.drawable._j310f,null,null,nodes[idx(R.drawable._j311f)],nodes[idx(R.drawable._j311f)],null);
		nodes[idx(R.drawable._j309f)] = new TourHallway(R.drawable._j309f,nodes[idx(R.drawable._j310f)]);
		nodes[idx(R.drawable._j308f)] = new TourHallway(R.drawable._j308f,nodes[idx(R.drawable._j309f)]);
		nodes[idx(R.drawable._j307f)] = new TourHallway(R.drawable._j307f,null);
		nodes[idx(R.drawable._j306f)] = new TourHallway(R.drawable._j306f,nodes[idx(R.drawable._j307f)],nodes[idx(R.drawable._j307f)],null,nodes[idx(R.drawable._j308f)],nodes[idx(R.drawable._j308f)]);
		nodes[idx(R.drawable._j305f)] = new TourHallway(R.drawable._j305f,nodes[idx(R.drawable._j306f)]);
		nodes[idx(R.drawable._j304f)] = new TourHallway(R.drawable._j304f,nodes[idx(R.drawable._j305f)]);
		nodes[idx(R.drawable._j303f)] = new TourHallway(R.drawable._j303f,nodes[idx(R.drawable._j304f)]);
		nodes[idx(R.drawable._j302f)] = new TourHallway(R.drawable._j302f,null);
		nodes[idx(R.drawable._j301f)] = new TourHallway(R.drawable._j301f,nodes[idx(R.drawable._j303f)],nodes[idx(R.drawable._j303f)],null,nodes[idx(R.drawable._j302f)],nodes[idx(R.drawable._j302f)]);

	}
	
	private int idx(int i){
		return(i - R.drawable._a301b);
	}
	
}
