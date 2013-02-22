/**
 * TourHandler.java
 * Brock Butler
 * Main activity for the tour portion of Brock Butler.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
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
		buttons[0] = (ImageButton)findViewById(R.id.outerleft);
		buttons[1] = (ImageButton)findViewById(R.id.upperleft);
		buttons[2] = (ImageButton)findViewById(R.id.center);
		buttons[3] = (ImageButton)findViewById(R.id.upperright);
		buttons[4] = (ImageButton)findViewById(R.id.outerright);
		info = new TourInfo(rl,buttons,getApplicationContext());
		initNodes();
		int first = -1;
		while (nodes[++first] == null);
		nodes[first].paint(info);
		progress.dismiss();
		//nodes[idx(R.drawable._j314f)].paint(info);
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
	 * int idx()
	 * @param r - image resource value
	 * @return index in `nodes` of TourNode which shows that image
	 */
	private int idx(int r){
		return(r - R.drawable._a301b);
	}
	
	/**
	 * void create()
	 * Adds a new TourHall to nodes.
	 * @param r  - the image resource value
	 * @param ol - image resource value of outer left button
	 * @param ul - resource of upper left
	 * @param c  - resource of center
	 * @param ur - resource of upper right
	 * @param or - resource of outer right
	 * @param ta - resource of turn around node
	 */
	private void create(int r, int ll, int ul, int c, int ur, int lr, int ta){
		if (ta != -1)
			nodes[idx(r)] = new TourHall(r,
				(ll == -1)? null : nodes[idx(ll)],
				(ul == -1)? null : nodes[idx(ul)],
				(c == -1) ? null : nodes[idx(c)],
				(ur == -1)? null : nodes[idx(ur)],
				(lr == -1)? null : nodes[idx(lr)],
				/*(ta == -1)?*/nodes[idx(ta)]);
		else
			nodes[idx(r)] = new TourHall(r,
				(ll == -1)? null : nodes[idx(ll)],
				(ul == -1)? null : nodes[idx(ul)],
				(c == -1) ? null : nodes[idx(c)],
				(ur == -1)? null : nodes[idx(ur)],
				(lr == -1)? null : nodes[idx(lr)]);
	}
	private void create(int r, int c){ create(r,-1,-1,c,-1,-1,-1); }
	private void create(int r, int c, int ta){ create(r,-1,-1,c,-1,-1,ta); }
	private void create (int r, int ll, int ul, int c, int ur, int lr){ create(r,ll,ul,c,ur,lr,-1); }
	
	/**
	 * void initNodes()
	 * Giant method to link all of the images together. The fun stuff. Right here.
	 */
	private void initNodes(){
		R.drawable D = new R.drawable();
		nodes = new TourNode[numImages];
		/** J BLOCK FORWARD PASS **/
		create(R.drawable._j315f,-1);
		create(R.drawable._j314f,R.drawable._j315f);
		create(R.drawable._j313f,R.drawable._j314f);
		create(R.drawable._j312f,-1,-1,-1,R.drawable._j313f,-1);
		create(R.drawable._j311f,R.drawable._j312f);
		create(R.drawable._j310f,-1,-1,R.drawable._j311f,R.drawable._j311f,-1);
		create(R.drawable._j309f,R.drawable._j310f);
		create(R.drawable._j308f,R.drawable._j309f);
		create(R.drawable._j307f,-1);
		create(R.drawable._j306f,R.drawable._j307f,R.drawable._j307f,-1,R.drawable._j308f,R.drawable._j308f);
		create(R.drawable._j305f,R.drawable._j306f);
		create(R.drawable._j304f,R.drawable._j305f);
		create(R.drawable._j303f,R.drawable._j304f);
		create(R.drawable._j302f,-1);
		create(R.drawable._j301f,R.drawable._j303f,R.drawable._j303f,R.drawable._j303f,R.drawable._j302f,R.drawable._j302f);

		/** D BLOCK FORWARD PASS **/
		create(R.drawable._d343f,-1,-1,-1,/*R.drawable._j312b*/-1,/*R.drawable._j312b*/-1);
		create(R.drawable._d342f,R.drawable._d343f);
		create(R.drawable._d341f,-1,-1,R.drawable._j301f,R.drawable._j301f,R.drawable._d342f);
		create(R.drawable._d340f,R.drawable._d341f);
		create(R.drawable._d339f,R.drawable._d340f);
		create(R.drawable._d338f,R.drawable._d339f);
		create(R.drawable._d337f,R.drawable._d338f);
		create(R.drawable._d336f,R.drawable._d337f);
		create(R.drawable._d335f,/*R.drawable._d306b*/-1,/*R.drawable._d306b*/-1,-1,R.drawable._d341f,R.drawable._d341f);
		create(R.drawable._d334f,R.drawable._d335f);
		create(R.drawable._d333f,R.drawable._d334f);
		create(R.drawable._d332f,R.drawable._d333f);
		create(R.drawable._d331f,R.drawable._d332f);
		create(R.drawable._d330f,R.drawable._d331f);
		create(R.drawable._d329f,R.drawable._d330f);
		create(R.drawable._d328f,R.drawable._d329f,R.drawable._d329f,-1,-1,-1);
		create(R.drawable._d327f,R.drawable._d328f);
		create(R.drawable._d326f,R.drawable._d327f);
		create(R.drawable._d325f,R.drawable._d326f);
		create(R.drawable._d324f,R.drawable._d325f);
		create(R.drawable._d323f,-1,R.drawable._d324f,R.drawable._d324f,-1,-1);
		create(R.drawable._d322f,R.drawable._d323f);
		create(R.drawable._d321f,R.drawable._d322f);
		create(R.drawable._d320f,R.drawable._d321f);
		create(R.drawable._d319f,R.drawable._d320f);
		create(R.drawable._d318f,R.drawable._d319f);
		create(R.drawable._d317f,R.drawable._d336f,R.drawable._d336f,-1,-1,R.drawable._d318f);
		create(R.drawable._d316f,R.drawable._d317f);
		create(R.drawable._d315f,R.drawable._d316f);
		create(R.drawable._d314f,R.drawable._d315f);
		create(R.drawable._d313f,R.drawable._d314f);
		create(R.drawable._d312f,/*R.drawable._d319b*/-1,/*R.drawable._d319b*/-1,-1,R.drawable._d319f,R.drawable._d319f);
		create(R.drawable._d311f,R.drawable._d312f);
		create(R.drawable._d310f,R.drawable._d311f,R.drawable._d311f,-1,-1,-1);
		create(R.drawable._d309f,R.drawable._d310f);
		create(R.drawable._d308f,R.drawable._d309f);
		create(R.drawable._d307f,R.drawable._d308f);
		create(R.drawable._d306f,R.drawable._d307f);
		create(R.drawable._d305f,R.drawable._d306f);
		create(R.drawable._d304f,-1);
		create(R.drawable._d303f,-1,R.drawable._d305f,-1,R.drawable._d304f,-1);
		create(R.drawable._d302f,-1,-1,R.drawable._d313f,-1,R.drawable._d303f);
		create(R.drawable._d301f,R.drawable._d302f);

		/** C BLOCK FORWARD PASS **/
		create(R.drawable._c312f,-1,-1,-1,R.drawable._d301f,R.drawable._d301f);
		create(R.drawable._c311f,R.drawable._c312f);
		create(R.drawable._c310f,-1,R.drawable._c311f,R.drawable._c311f,-1,-1);
		create(R.drawable._c309f,R.drawable._c310f);
		create(R.drawable._c308f,R.drawable._c309f);
		create(R.drawable._c307f,-1,R.drawable._c308f,-1,-1,-1);
		create(R.drawable._c306f,-1,-1,/*R.drawable._c306b*/-1,R.drawable._c307f,R.drawable._c307f);
		create(R.drawable._c305f,R.drawable._c306f);
		create(R.drawable._c304f,R.drawable._c305f);
		create(R.drawable._c303f,R.drawable._c304f);
		create(R.drawable._c302f,R.drawable._c303f);
		create(R.drawable._c301f,-1,-1,-1,R.drawable._c302f,R.drawable._c302f);
		create(R.drawable._c316f,-1,R.drawable._c306f,/*R.drawable._c310b*/-1,/*R.drawable._c310b*/-1,-1);
		create(R.drawable._c315f,R.drawable._c316f);
		create(R.drawable._c314f,R.drawable._c315f);
		create(R.drawable._c313f,-1,-1,-1,R.drawable._c314f,R.drawable._c314f);
		
		/** F BLOCK FORWARD PASS **/
		create(R.drawable._f308f,-1,R.drawable._c313f,R.drawable._c313f,-1,-1);
		create(R.drawable._f307f,R.drawable._f308f);
		create(R.drawable._f306f,R.drawable._f307f);
		create(R.drawable._f305f,R.drawable._f306f);
		create(R.drawable._f304f,R.drawable._f305f);
		create(R.drawable._f303f,R.drawable._f304f);
		create(R.drawable._f302f,R.drawable._f303f);
		create(R.drawable._f301f,R.drawable._f302f,R.drawable._f302f,-1,/*R.drawable._h302b*/-1,/*R.drawable._h302b*/-1);
		
		/** G BLOCK FORWARD PASS **/
		create(R.drawable._g313f,-1);
		create(R.drawable._g312f,R.drawable._g313f);
		create(R.drawable._g311f,R.drawable._g312f);
		create(R.drawable._g310f,R.drawable._g311f);
		create(R.drawable._g309f,R.drawable._g310f);
		create(R.drawable._g308f,R.drawable._g309f);
		create(R.drawable._g307f,R.drawable._g308f);
		create(R.drawable._g306f,R.drawable._g307f);
		create(R.drawable._g305f,R.drawable._g306f);
		create(R.drawable._g304f,R.drawable._g305f);
		create(R.drawable._g303f,R.drawable._g304f);
		create(R.drawable._g302f,-1,-1,R.drawable._g303f,R.drawable._g303f,-1);
		create(R.drawable._g301f,-1,R.drawable._g302f,-1,-1,-1);

		/** H BLOCK FORWARD PASS **/
		create(R.drawable._h338f,-1,R.drawable._g301f,-1,R.drawable._f302f,-1);
		create(R.drawable._h337f,-1,-1,R.drawable._h338f,R.drawable._h338f,-1);
		create(R.drawable._h336f,-1,-1,R.drawable._h337f,R.drawable._h337f,-1);
		create(R.drawable._h335f,-1,-1,R.drawable._h336f,R.drawable._h336f,-1);
		create(R.drawable._h334f,R.drawable._h335f);
		create(R.drawable._h333f,/*R.drawable._h303b*/-1,/*R.drawable._h303b*/-1,-1,R.drawable._h334f,-1);
		create(R.drawable._h332f,R.drawable._h333f);
		create(R.drawable._h331f,R.drawable._h332f);
		create(R.drawable._h330f,R.drawable._h331f);
		create(R.drawable._h329f,-1,R.drawable._h334f,/*R.drawable._h303b*/-1,/*R.drawable._h322b*/-1,/*R.drawable._h322b*/-1);
		create(R.drawable._h328f,R.drawable._h329f);
		create(R.drawable._h327f,R.drawable._h328f);
		create(R.drawable._h326f,R.drawable._h327f);
		create(R.drawable._h325f,R.drawable._h326f);
		create(R.drawable._h324f,R.drawable._h325f);
		create(R.drawable._h323f,R.drawable._h324f);
		create(R.drawable._h322f,R.drawable._h323f);
		create(R.drawable._h321f,R.drawable._h322f);
		create(R.drawable._h320f,R.drawable._h321f);
		create(R.drawable._h319f,-1,-1,-1,-1,R.drawable._h320f);
		create(R.drawable._h318f,R.drawable._h319f);
		create(R.drawable._h317f,R.drawable._h318f);
		create(R.drawable._h316f,R.drawable._h317f);
		create(R.drawable._h315f,R.drawable._h316f);
		create(R.drawable._h314f,-1);
		create(R.drawable._h313f,R.drawable._h314f);
		create(R.drawable._h312f,-1,-1,R.drawable._h313f,R.drawable._h315f,R.drawable._h315f);
		create(R.drawable._h311f,-1,-1,-1,R.drawable._h312f,R.drawable._h312f);
		create(R.drawable._h310f,R.drawable._h311f);
		create(R.drawable._h309f,R.drawable._h310f);
		create(R.drawable._h308f,R.drawable._h309f);
		create(R.drawable._h307f,R.drawable._h308f);
		create(R.drawable._h306f,R.drawable._h307f);
		create(R.drawable._h305f,R.drawable._h306f);
		create(R.drawable._h304f,R.drawable._h305f);
		create(R.drawable._h303f,R.drawable._h304f,R.drawable._h304f,-1,R.drawable._h330f,R.drawable._h330f);
		create(R.drawable._h302f,R.drawable._h303f);
		create(R.drawable._h301f,R.drawable._h302f);
		
		/** E BLOCK FORWARD PASS **/
		create(R.drawable._e311f,-1);
		create(R.drawable._e310f,R.drawable._h301f,R.drawable._h301f,R.drawable._e311f,-1,-1);
		create(R.drawable._e309f,R.drawable._e310f);
		create(R.drawable._e308f,R.drawable._e309f);
		create(R.drawable._e307f,-1,R.drawable._e308f,R.drawable._e308f,-1,-1);
		create(R.drawable._e306f,R.drawable._e307f);
		create(R.drawable._e305f,R.drawable._e306f);
		create(R.drawable._e304f,R.drawable._e305f);
		create(R.drawable._e303f,R.drawable._e304f);
		create(R.drawable._e302f,R.drawable._e303f);
		create(R.drawable._e301f,-1,-1,-1,R.drawable._e302f,R.drawable._e302f);

		/** B BLOCK FORWARD PASS **/
		create(R.drawable._b314f,-1,R.drawable._c301f,R.drawable._c301f,-1,-1);
		create(R.drawable._b313f,R.drawable._b314f);
		create(R.drawable._b312f,R.drawable._b314f);
		create(R.drawable._b311f,-1,-1,R.drawable._b312f,R.drawable._b312f,R.drawable._b312f);
		create(R.drawable._b310f,R.drawable._b311f,-1,R.drawable._b313f,-1,-1);
		create(R.drawable._b309f,R.drawable._b310f);
		create(R.drawable._b308f,R.drawable._b309f,R.drawable._b309f,-1,-1,-1);
		create(R.drawable._b307f,R.drawable._e301f);
		create(R.drawable._b306f,R.drawable._b307f);
		create(R.drawable._b305f,R.drawable._b306f);
		create(R.drawable._b304f,R.drawable._b305f);
		create(R.drawable._b303f,R.drawable._b304f);
		create(R.drawable._b302f,R.drawable._b303f,R.drawable._b303f,-1,R.drawable._b308f,R.drawable._b308f);
		create(R.drawable._b301f,-1,-1,-1,R.drawable._b302f,R.drawable._b302f);

		/** A BLOCK FORWARD PASS **/
		create(R.drawable._a309f,R.drawable._b301f,-1,-1,-1,-1);
		create(R.drawable._a308f,R.drawable._a309f);
		create(R.drawable._a307f,R.drawable._a308f);
		create(R.drawable._a306f,R.drawable._a307f);
		create(R.drawable._a305f,R.drawable._a306f);
		create(R.drawable._a304f,R.drawable._a305f);
		create(R.drawable._a303f,R.drawable._a304f);
		create(R.drawable._a302f,R.drawable._a303f);
		create(R.drawable._a301f,R.drawable._a302f);
	}
}
