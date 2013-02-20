package com.seaaddicts.brockbutler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class TourHandler extends Activity {
	private TourNode[] nodes;
	private final int numImages = R.drawable._j315f - R.drawable._a301b + 1;
	private TourInfo info;

	private final int groupID = 1;
	private final int backID = Menu.FIRST;
	private final int turnID = Menu.FIRST + 1;
	private final int exitID = Menu.FIRST + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_handler);
		
		ProgressDialog progress = new ProgressDialog(this);
		progress.setTitle("Loading Tour");
		progress.setMessage("Please wait while the tour loads...");
		progress.show();
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.screen);
		ImageButton[] buttons = new ImageButton[5];
		buttons[0] = (ImageButton)findViewById(R.id.lowerleft);
		buttons[1] = (ImageButton)findViewById(R.id.upperleft);
		buttons[2] = (ImageButton)findViewById(R.id.center);
		buttons[3] = (ImageButton)findViewById(R.id.upperright);
		buttons[4] = (ImageButton)findViewById(R.id.lowerright);
		info = new TourInfo(rl,buttons,getApplicationContext());
		initNodes();
		int first = 0;
		while (nodes[first] == null) first++;
		nodes[first].paint(info);
		progress.dismiss();
		//TODO ask user where they want to be dropped
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(groupID,backID,backID,"Go Back");
		menu.add(groupID,turnID,turnID,"Turn Around");
		menu.add(groupID,exitID,exitID,"End Tour");
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_tour_handler, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case backID:
			onBackPressed();
			return(true);
		case turnID:
			turnAround();
			return(true);
		case exitID:
			super.onBackPressed();
			return(true);
		default:
			return(super.onOptionsItemSelected(item));
		}
	}
	
	/**
	 * void onBackPressed()
	 * Overrides the back button to pop previous TourNode from the stack
	 * and goes to that node.
	 */
	@Override
	public void onBackPressed(){
		if (!info.history.empty())
			info.history.pop().paint(info);
	}

	/**
	 * void turnAround()
	 * Goes to the node in the tour which is logically turning around. Fun.
	 */
	private void turnAround(){
		if (info.current != null && info.current.canTurnAround())
			info.current.turnAroundNode.paint(info);
	}
	
	/**
	 * void initNodes()
	 * Giant method to link all of the images together. The fun stuff. Right here.
	 */
	private void initNodes(){
		nodes = new TourNode[numImages];
		for (int i = 0; i < nodes.length; i++)
			nodes[i] = null;
		/** J BLOCK FORWARD PASS **/
		nodes[idx(R.drawable._j315f)] = new TourHall(R.drawable._j315f,null);
		nodes[idx(R.drawable._j314f)] = new TourHall(R.drawable._j314f,nodes[idx(R.drawable._j315f)]);
		nodes[idx(R.drawable._j313f)] = new TourHall(R.drawable._j313f,nodes[idx(R.drawable._j314f)]);
		nodes[idx(R.drawable._j312f)] = new TourHall(R.drawable._j312f,null,null,null,nodes[idx(R.drawable._j313f)],null);
		nodes[idx(R.drawable._j311f)] = new TourHall(R.drawable._j311f,nodes[idx(R.drawable._j312f)]);
		nodes[idx(R.drawable._j310f)] = new TourHall(R.drawable._j310f,null,null,nodes[idx(R.drawable._j311f)],nodes[idx(R.drawable._j311f)],null);
		nodes[idx(R.drawable._j309f)] = new TourHall(R.drawable._j309f,nodes[idx(R.drawable._j310f)]);
		nodes[idx(R.drawable._j308f)] = new TourHall(R.drawable._j308f,nodes[idx(R.drawable._j309f)]);
		nodes[idx(R.drawable._j307f)] = new TourHall(R.drawable._j307f,null);
		nodes[idx(R.drawable._j306f)] = new TourHall(R.drawable._j306f,nodes[idx(R.drawable._j307f)],nodes[idx(R.drawable._j307f)],null,nodes[idx(R.drawable._j308f)],nodes[idx(R.drawable._j308f)]);
		nodes[idx(R.drawable._j305f)] = new TourHall(R.drawable._j305f,nodes[idx(R.drawable._j306f)]);
		nodes[idx(R.drawable._j304f)] = new TourHall(R.drawable._j304f,nodes[idx(R.drawable._j305f)]);
		nodes[idx(R.drawable._j303f)] = new TourHall(R.drawable._j303f,nodes[idx(R.drawable._j304f)]);
		nodes[idx(R.drawable._j302f)] = new TourHall(R.drawable._j302f,null);
		nodes[idx(R.drawable._j301f)] = new TourHall(R.drawable._j301f,nodes[idx(R.drawable._j303f)],nodes[idx(R.drawable._j303f)],nodes[idx(R.drawable._j303f)],nodes[idx(R.drawable._j302f)],nodes[idx(R.drawable._j302f)]);

		/** D BLOCK FORWARD PASS **/
		nodes[idx(R.drawable._d343f)] = new TourHall(R.drawable._d343f,null,null,null,/*_j312b*/null,/*_j312b*/null);
		nodes[idx(R.drawable._d342f)] = new TourHall(R.drawable._d342f,nodes[idx(R.drawable._d343f)]);
		nodes[idx(R.drawable._d341f)] = new TourHall(R.drawable._d341f,null,null,nodes[idx(R.drawable._j301f)],nodes[idx(R.drawable._j301f)],nodes[idx(R.drawable._d342f)]);
		nodes[idx(R.drawable._d340f)] = new TourHall(R.drawable._d340f,nodes[idx(R.drawable._d341f)]);
		nodes[idx(R.drawable._d339f)] = new TourHall(R.drawable._d339f,nodes[idx(R.drawable._d340f)]);
		nodes[idx(R.drawable._d338f)] = new TourHall(R.drawable._d338f,nodes[idx(R.drawable._d339f)]);
		nodes[idx(R.drawable._d337f)] = new TourHall(R.drawable._d337f,nodes[idx(R.drawable._d338f)]);
		nodes[idx(R.drawable._d336f)] = new TourHall(R.drawable._d336f,nodes[idx(R.drawable._d337f)]);
		nodes[idx(R.drawable._d335f)] = new TourHall(R.drawable._d335f,/*_d306b*/null,/*_d306b*/null,null,nodes[idx(R.drawable._d341f)],nodes[idx(R.drawable._d341f)]);
		nodes[idx(R.drawable._d334f)] = new TourHall(R.drawable._d334f,nodes[idx(R.drawable._d335f)]);
		nodes[idx(R.drawable._d333f)] = new TourHall(R.drawable._d333f,nodes[idx(R.drawable._d334f)]);
		nodes[idx(R.drawable._d332f)] = new TourHall(R.drawable._d332f,nodes[idx(R.drawable._d333f)]);
		nodes[idx(R.drawable._d331f)] = new TourHall(R.drawable._d331f,nodes[idx(R.drawable._d332f)]);
		nodes[idx(R.drawable._d330f)] = new TourHall(R.drawable._d330f,nodes[idx(R.drawable._d331f)]);
		nodes[idx(R.drawable._d329f)] = new TourHall(R.drawable._d329f,nodes[idx(R.drawable._d330f)]);
		nodes[idx(R.drawable._d328f)] = new TourHall(R.drawable._d328f,nodes[idx(R.drawable._d329f)],nodes[idx(R.drawable._d329f)],null,null,null);
		nodes[idx(R.drawable._d327f)] = new TourHall(R.drawable._d327f,nodes[idx(R.drawable._d328f)]);
		nodes[idx(R.drawable._d326f)] = new TourHall(R.drawable._d326f,nodes[idx(R.drawable._d327f)]);
		nodes[idx(R.drawable._d325f)] = new TourHall(R.drawable._d325f,nodes[idx(R.drawable._d326f)]);
		nodes[idx(R.drawable._d324f)] = new TourHall(R.drawable._d324f,nodes[idx(R.drawable._d325f)]);
		nodes[idx(R.drawable._d323f)] = new TourHall(R.drawable._d323f,null,nodes[idx(R.drawable._d324f)],nodes[idx(R.drawable._d324f)],null,null);
		nodes[idx(R.drawable._d322f)] = new TourHall(R.drawable._d322f,nodes[idx(R.drawable._d323f)]);
		nodes[idx(R.drawable._d321f)] = new TourHall(R.drawable._d321f,nodes[idx(R.drawable._d322f)]);
		nodes[idx(R.drawable._d320f)] = new TourHall(R.drawable._d320f,nodes[idx(R.drawable._d321f)]);
		nodes[idx(R.drawable._d319f)] = new TourHall(R.drawable._d319f,nodes[idx(R.drawable._d320f)]);
		nodes[idx(R.drawable._d318f)] = new TourHall(R.drawable._d318f,nodes[idx(R.drawable._d319f)]);
		nodes[idx(R.drawable._d317f)] = new TourHall(R.drawable._d317f,nodes[idx(R.drawable._d336f)],nodes[idx(R.drawable._d336f)],null,null,nodes[idx(R.drawable._d318f)]);
		nodes[idx(R.drawable._d316f)] = new TourHall(R.drawable._d316f,nodes[idx(R.drawable._d317f)]);
		nodes[idx(R.drawable._d315f)] = new TourHall(R.drawable._d315f,nodes[idx(R.drawable._d316f)]);
		nodes[idx(R.drawable._d314f)] = new TourHall(R.drawable._d314f,nodes[idx(R.drawable._d315f)]);
		nodes[idx(R.drawable._d313f)] = new TourHall(R.drawable._d313f,nodes[idx(R.drawable._d314f)]);
		nodes[idx(R.drawable._d312f)] = new TourHall(R.drawable._d312f,/*_d319b*/null,/*_d319b*/null,null,nodes[idx(R.drawable._d319f)],nodes[idx(R.drawable._d319f)]);
		nodes[idx(R.drawable._d311f)] = new TourHall(R.drawable._d311f,nodes[idx(R.drawable._d312f)]);
		nodes[idx(R.drawable._d310f)] = new TourHall(R.drawable._d310f,nodes[idx(R.drawable._d311f)],nodes[idx(R.drawable._d311f)],null,null,null);
		nodes[idx(R.drawable._d309f)] = new TourHall(R.drawable._d309f,nodes[idx(R.drawable._d310f)]);
		nodes[idx(R.drawable._d308f)] = new TourHall(R.drawable._d308f,nodes[idx(R.drawable._d309f)]);
		nodes[idx(R.drawable._d307f)] = new TourHall(R.drawable._d307f,nodes[idx(R.drawable._d308f)]);
		nodes[idx(R.drawable._d306f)] = new TourHall(R.drawable._d306f,nodes[idx(R.drawable._d307f)]);
		nodes[idx(R.drawable._d305f)] = new TourHall(R.drawable._d305f,nodes[idx(R.drawable._d306f)]);
		nodes[idx(R.drawable._d304f)] = new TourHall(R.drawable._d304f,null);
		nodes[idx(R.drawable._d303f)] = new TourHall(R.drawable._d303f,null,nodes[idx(R.drawable._d305f)],null,nodes[idx(R.drawable._d304f)],null);
		nodes[idx(R.drawable._d302f)] = new TourHall(R.drawable._d302f,null,null,nodes[idx(R.drawable._d313f)],null,nodes[idx(R.drawable._d303f)]);
		nodes[idx(R.drawable._d301f)] = new TourHall(R.drawable._d301f,nodes[idx(R.drawable._d302f)]);

		/** C BLOCK FORWARD PASS **/
	}
	
	/**
	 * int idx()
	 * @param r - image resource value
	 * @return index in `nodes` of TourNode which shows that image
	 */
	private int idx(int r){
		return(r - R.drawable._a301b);
	}
	
}
