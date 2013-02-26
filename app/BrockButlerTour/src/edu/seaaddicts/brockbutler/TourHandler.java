/**
 * TourHandler.java
 * Brock Butler
 * Main activity for the tour portion of Brock Butler.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package edu.seaaddicts.brockbutler;

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
	private final int teleID = Menu.FIRST + 2;
	private final int exitID = Menu.FIRST + 3;

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
		buttons[1] = (ImageButton)findViewById(R.id.innerleft);
		buttons[2] = (ImageButton)findViewById(R.id.center);
		buttons[3] = (ImageButton)findViewById(R.id.innerright);
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
		//menu.add(groupID,teleID,teleID,"Teleport!");
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
		case teleID:
			return(true);
		case exitID:
			super.onBackPressed();
			return(true);
		default:
			return(super.onOptionsItemSelected(item));
		}
	}
	
	/**
	 * Overrides the back button to pop previous TourNode from the stack
	 * and goes to that node.
	 */
	@Override
	public void onBackPressed(){
		if (!info.history.empty())
			info.history.pop().paint(info);
	}

	/**
	 * Goes to the node in the tour which is logically turning around.
	 */
	private void turnAround(){
		if (info.current.canTurnAround()){
			info.history.push(info.current);
			info.current.turnAroundNode.paint(info);
		}
	}
	
	/**
	 * @param r - image resource value
	 * @return index in `nodes` of TourNode which shows the image `r`
	 */
	private int idx(int r){
		return(r - R.drawable._a301b);
	}
	
	/**
	 * Adds a new TourHall to nodes.
	 * @param r  - the image resource value
	 * @param ol - image resource value of outer left button
	 * @param ul - resource of inner left
	 * @param c  - resource of center
	 * @param ur - resource of inner right
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
	 * Giant method to link all of the images together. The fun stuff. Right here.
	 */
	@SuppressWarnings("static-access")
	private void initNodes(){
		R.drawable D = new R.drawable();
		nodes = new TourNode[numImages];
		/** J BLOCK FORWARD PASS **/
		create(D._j315f,-1);
		create(D._j314f,D._j315f);
		create(D._j313f,D._j314f);
		create(D._j312f,-1,-1,-1,D._j313f,-1);
		create(D._j311f,D._j312f);
		create(D._j310f,-1,-1,D._j311f,D._j311f,-1);
		create(D._j309f,D._j310f);
		create(D._j308f,D._j309f);
		create(D._j307f,-1);
		create(D._j306f,D._j307f,D._j307f,-1,D._j308f,D._j308f);
		create(D._j305f,D._j306f);
		create(D._j304f,D._j305f);
		create(D._j303f,D._j304f);
		create(D._j302f,-1);
		create(D._j301f,D._j303f,D._j303f,D._j303f,D._j302f,D._j302f);

		/** D BLOCK FORWARD PASS **/
		create(D._d343f,-1,-1,-1,/*D._j312b*/-1,/*D._j312b*/-1);
		create(D._d342f,D._d343f);
		create(D._d341f,-1,-1,D._j301f,D._j301f,D._d342f);
		create(D._d340f,D._d341f);
		create(D._d339f,D._d340f);
		create(D._d338f,D._d339f);
		create(D._d337f,D._d338f);
		create(D._d336f,D._d337f);
		create(D._d335f,/*D._d306b*/-1,/*D._d306b*/-1,-1,D._d341f,D._d341f);
		create(D._d334f,D._d335f);
		create(D._d333f,D._d334f);
		create(D._d332f,D._d333f);
		create(D._d331f,D._d332f);
		create(D._d330f,D._d331f);
		create(D._d329f,D._d330f);
		create(D._d328f,D._d329f,D._d329f,-1,-1,-1);
		create(D._d327f,D._d328f);
		create(D._d326f,D._d327f);
		create(D._d325f,D._d326f);
		create(D._d324f,D._d325f);
		create(D._d323f,-1,D._d324f,D._d324f,-1,-1);
		create(D._d322f,D._d323f);
		create(D._d321f,D._d322f);
		create(D._d320f,D._d321f);
		create(D._d319f,D._d320f);
		create(D._d318f,D._d319f);
		create(D._d317f,D._d336f,D._d336f,-1,-1,D._d318f);
		create(D._d316f,D._d317f);
		create(D._d315f,D._d316f);
		create(D._d314f,D._d315f);
		create(D._d313f,D._d314f);
		create(D._d312f,/*D._d319b*/-1,/*D._d319b*/-1,-1,D._d319f,D._d319f);
		create(D._d311f,D._d312f);
		create(D._d310f,D._d311f,D._d311f,-1,-1,-1);
		create(D._d309f,D._d310f);
		create(D._d308f,D._d309f);
		create(D._d307f,D._d308f);
		create(D._d306f,D._d307f);
		create(D._d305f,D._d306f);
		create(D._d304f,-1);
		create(D._d303f,-1,D._d305f,-1,D._d304f,-1);
		create(D._d302f,-1,-1,D._d313f,-1,D._d303f);
		create(D._d301f,D._d302f);

		/** C BLOCK FORWARD PASS **/
		create(D._c312f,-1,-1,-1,D._d301f,D._d301f);
		create(D._c311f,D._c312f);
		create(D._c310f,-1,D._c311f,D._c311f,-1,-1);
		create(D._c309f,D._c310f);
		create(D._c308f,D._c309f);
		create(D._c307f,-1,D._c308f,-1,-1,-1);
		create(D._c306f,-1,-1,/*D._c306b*/-1,D._c307f,D._c307f);
		create(D._c305f,D._c306f);
		create(D._c304f,D._c305f);
		create(D._c303f,D._c304f);
		create(D._c302f,D._c303f);
		create(D._c301f,-1,-1,-1,D._c302f,D._c302f);
		create(D._c316f,-1,D._c306f,/*D._c310b*/-1,/*D._c310b*/-1,-1);
		create(D._c315f,D._c316f);
		create(D._c314f,D._c315f);
		create(D._c313f,-1,-1,-1,D._c314f,D._c314f);
		
		/** F BLOCK FORWARD PASS **/
		create(D._f308f,-1,D._c313f,D._c313f,-1,-1);
		create(D._f307f,D._f308f);
		create(D._f306f,D._f307f);
		create(D._f305f,D._f306f);
		create(D._f304f,D._f305f);
		create(D._f303f,D._f304f);
		create(D._f302f,D._f303f);
		create(D._f301f,D._f302f,D._f302f,-1,/*D._h302b*/-1,/*D._h302b*/-1);
		
		/** G BLOCK FORWARD PASS **/
		create(D._g313f,-1);
		create(D._g312f,D._g313f);
		create(D._g311f,D._g312f);
		create(D._g310f,D._g311f);
		create(D._g309f,D._g310f);
		create(D._g308f,D._g309f);
		create(D._g307f,D._g308f);
		create(D._g306f,D._g307f);
		create(D._g305f,D._g306f);
		create(D._g304f,D._g305f);
		create(D._g303f,D._g304f);
		create(D._g302f,-1,-1,D._g303f,D._g303f,-1);
		create(D._g301f,-1,D._g302f,-1,-1,-1);

		/** H BLOCK FORWARD PASS **/
		create(D._h338f,D._g301f,D._g301f,-1,D._f302f,D._f302f);
		create(D._h337f,-1,-1,D._h338f,D._h338f,-1);
		create(D._h336f,-1,-1,D._h337f,D._h337f,-1);
		create(D._h335f,-1,-1,D._h336f,D._h336f,-1);
		create(D._h334f,D._h335f);
		create(D._h333f,/*D._h303b*/-1,/*D._h303b*/-1,D._h334f,-1,-1);
		create(D._h332f,D._h333f);
		create(D._h331f,D._h332f);
		create(D._h330f,D._h331f);
		create(D._h329f,-1,D._h334f,/*D._h303b*/-1,/*D._h322b*/-1,/*D._h322b*/-1);
		create(D._h328f,D._h329f);
		create(D._h327f,D._h328f);
		create(D._h326f,D._h327f);
		create(D._h325f,D._h326f);
		create(D._h324f,D._h325f);
		create(D._h323f,D._h324f);
		create(D._h322f,D._h323f);
		create(D._h321f,D._h322f);
		create(D._h320f,D._h321f);
		create(D._h319f,-1,-1,-1,-1,D._h320f);
		create(D._h318f,D._h319f);
		create(D._h317f,D._h318f);
		create(D._h316f,D._h317f);
		create(D._h315f,D._h316f);
		create(D._h314f,-1);
		create(D._h313f,D._h314f);
		create(D._h312f,-1,-1,D._h313f,D._h315f,D._h315f);
		create(D._h311f,-1,-1,-1,D._h312f,D._h312f);
		create(D._h310f,D._h311f);
		create(D._h309f,D._h310f);
		create(D._h308f,D._h309f);
		create(D._h307f,D._h308f);
		create(D._h306f,D._h307f);
		create(D._h305f,D._h306f);
		create(D._h304f,D._h305f);
		create(D._h303f,D._h304f,D._h304f,-1,D._h330f,D._h330f);
		create(D._h302f,D._h303f);
		create(D._h301f,D._h302f);
		
		/** E BLOCK FORWARD PASS **/
		create(D._e311f,-1);
		create(D._e310f,D._h301f,D._h301f,-1,D._e311f,-1);
		create(D._e309f,D._e310f);
		create(D._e308f,D._e309f);
		create(D._e307f,-1,D._e308f,D._e308f,-1,-1);
		create(D._e306f,D._e307f);
		create(D._e305f,D._e306f);
		create(D._e304f,D._e305f);
		create(D._e303f,D._e304f);
		create(D._e302f,D._e303f);
		create(D._e301f,-1,-1,-1,D._e302f,D._e302f);

		/** B BLOCK FORWARD PASS **/
		create(D._b314f,-1,D._c301f,D._c301f,-1,-1);
		create(D._b313f,D._b314f);
		create(D._b312f,D._b314f);
		create(D._b311f,-1,-1,D._b312f,D._b312f,D._b312f);
		create(D._b310f,D._b311f,-1,D._b313f,-1,-1);
		create(D._b309f,D._b310f);
		create(D._b308f,D._b309f,D._b309f,-1,-1,-1);
		create(D._b307f,D._e301f);
		create(D._b306f,D._b307f);
		create(D._b305f,D._b306f);
		create(D._b304f,D._b305f);
		create(D._b303f,D._b304f);
		create(D._b302f,D._b303f,D._b303f,-1,D._b308f,D._b308f);
		create(D._b301f,-1,-1,-1,D._b302f,D._b302f);

		/** A BLOCK FORWARD PASS **/
		create(D._a309f,D._b301f,-1,-1,-1,-1);
		create(D._a308f,D._a309f);
		create(D._a307f,D._a308f);
		create(D._a306f,D._a307f);
		create(D._a305f,D._a306f);
		create(D._a304f,D._a305f);
		create(D._a303f,D._a304f);
		create(D._a302f,D._a303f);
		create(D._a301f,D._a302f);

		/** A BLOCK BACK PASS **/
		create(D._a307b,-1,D._a302f);
		create(D._a306b,D._a307b,D._a303f);
		create(D._a305b,D._a306b,D._a304f);
		create(D._a304b,D._a305b,D._a307f);
		create(D._a303b,D._a304b,D._a308f);
		create(D._a302b,-1,-1,-1,D._a303b,D._a303b,D._a309f);
		create(D._a301b,D._a302b,D._b301f);

		/** B BLOCK BACK PASS **/
		create(D._b317b,D._a301b,D._b301f);
		create(D._b316b,D._b317b,D._b302f);
		create(D._b313b,-1,-1,-1,D._b316b,D._b316b,D._b309f);
		create(D._b312b,D._b313b,D._b313f);
		create(D._b311b,D._b311b,D._b313f);
		create(D._b310b,-1,D._b311b,D._b311b,-1,-1,D._b312f);
		create(D._b309b,-1,D._b312b,D._b312b,-1,D._b310b,D._b314f);
		create(D._b308b,-1,D._b309b,-1,-1,-1,D._b314f);
		create(D._b306b,-1,-1,-1,D._b308b,D._b308b,D._c301f);
		create(D._b305b,D._b308f,D._b308f,-1,D._b317b,D._b317b,D._b303f);
		create(D._b304b,D._b305b,D._b304f);
		create(D._b303b,D._b304b,D._b305f);
		create(D._b302b,D._b303b,D._b306f);
		create(D._b301b,D._b302b,D._e301f);

		/** E BLOCK BACK PASS **/
		create(D._e309b,D._b301b,D._e303f);
		create(D._e308b,D._e309b,D._e304f);
		create(D._e307b,D._e308b,D._e305f);
		create(D._e306b,D._e307b,D._e306f);
		create(D._e305b,D._e306b,D._e307f);
		create(D._e304b,D._e305b,D._e308f);
		create(D._e303b,D._e304b,D._e309f);
		create(D._e302b,D._e303b,D._e311f);
		create(D._e301b,D._e311f,-1,D._e303b,D._e303b,-1,D._h301f);

		/**  H BLOCK BACK PASS **/
		create(D._h326b,D._e301b,D._h302f);
		create(D._h325b,-1,-1,D._h326b,-1,D._h304f,D._h330f);
		create(D._h324b,D._h325b,D._h331f);
		create(D._h323b,D._h324b,D._h332f);
		create(D._h322b,D._h323b,D._h333f);
		create(D._h321b,D._h330f,D._h330f,-1,D._h326b,D._h326b,D._h304f);
		create(D._h320b,D._h321b,D._h305f);
		create(D._h319b,D._h320b,D._h307f);
		create(D._h318b,D._h319b,D._h308f);
		create(D._h317b,D._h318b,D._h309f);
		create(D._h316b,D._h317b,D._h311f);
		create(D._h315b,-1,D._h316b,-1,-1,D._h313f,D._h315f);
		create(D._h314b,D._h315b,D._h316f);
		create(D._h313b,D._h314b,D._h317f);
		create(D._h312b,D._h313b,D._h319f);
		create(D._h311b,D._h312b,D._h322f);
		create(D._h310b,D._h311b,D._h323f);
		create(D._h309b,D._h310b,D._h324f);
		create(D._h308b,D._h309b,D._h325f);
		create(D._h307b,D._h308b,D._h326f);
		create(D._h306b,D._h307b,D._h327f);
		create(D._h305b,D._h306b,D._h328f);
		create(D._h304b,D._h305b,D._h329f);
		create(D._h303b,D._h322b,D._h322b,-1,D._h304b,D._h304b,D._h334f);
		create(D._h302b,D._h303b,D._h335f);
		create(D._h301b,D._h302b,D._h337f);

		/** G BLOCK BACK PASS **/
		create(D._g309b,-1,-1,-1,D._f301f,D._f301f,D._g302f);
		create(D._g308b,D._g309b,D._g309b,-1,-1,-1,D._g303f);
		create(D._g307b,D._g308b,D._g304f);
		create(D._g306b,D._g307b,D._g305f);
		create(D._g305b,D._g306b,D._g306f);
		create(D._g304b,D._g305b,D._g309f);
		create(D._g303b,D._g304b,D._g310f);
		create(D._g302b,D._g303b,D._g311f);
		create(D._g301b,-1,-1,D._g302b,-1,D._g313f,D._g312f);

		/** F BLOCK BACK PASS **/
		create(D._f305b,D._f301f,D._f303f);
		create(D._f304b,D._f305b,D._f304f);
		create(D._f303b,D._f304b,D._f305f);
		create(D._f302b,D._f303b,D._f306f);
		create(D._f301b,D._f302b,D._f308f);

		/** C BLOCK BACK PASS **/
		create(D._c315b,D._b306b,D._c302f);
		create(D._c314b,D._c315b,D._c303f);
		create(D._c313b,D._c314b,D._c304f);
		create(D._c312b,D._c313b,D._c305f);
		create(D._c311b,D._c312b,D._c306f);
		create(D._c310b,D._c311b,D._c306f);
		create(D._c309b,D._f301b,D._f301b,-1,-1,-1,D._c313f);
		create(D._c308b,D._c309b,D._c314f);
		create(D._c307b,D._c308b,D._c315f);
		create(D._c306b,D._c307b,D._c316f);
		create(D._c305b,-1,-1,-1,D._c310b,D._c306b,D._c308f);
		create(D._c304b,D._c305b,D._c309f);
		create(D._c303b,D._c304b,D._c310f);
		create(D._c302b,-1,-1,-1,D._c303b,D._c303b,D._c311f);
		create(D._c301b,D._c302b,D._c302b,-1,-1,-1,D._c312f);

		/** D BLOCK BACK PASS **/
		create(D._d334b,D._c301b,D._d301f);
		create(D._d333b,D._d334b,-1,-1,-1,D._d313f,D._d303f);
		create(D._d332b,D._d333b,D._d303f);
		create(D._d331b,D._d332b,D._d305f);
		create(D._d330b,D._d331b,D._d306f);
		create(D._d329b,D._d330b,D._d307f);
		create(D._d328b,D._d329b,D._d308f);
		create(D._d327b,D._d328b,D._d309f);
		create(D._d326b,-1,-1,-1,D._d327b,D._d327b,D._d310f);
		create(D._d325b,D._d334b,D._d314f);
		create(D._d324b,D._d325b,D._d315f);
		create(D._d323b,D._d324b,D._d317f);
		create(D._d322b,-1,D._d318f,D._d323b,-1,-1,D._d337f);
		create(D._d321b,D._d322b,D._d338f);
		create(D._d320b,D._d321b,D._d340f);
		create(D._d319b,D._d323b,D._d319f);
		create(D._d318b,D._d319b,D._d320f);
		create(D._d317b,D._d318b,D._d321f);
		create(D._d316b,D._d317b,D._d322f);
		create(D._d315b,D._d316b,D._d323f);
		create(D._d314b,D._d315b,D._d323f);
		create(D._d313b,D._d314b,D._d325f);
		create(D._d312b,D._d313b,D._d327f);
		create(D._d311b,D._d312b,D._d329f);
		create(D._d310b,D._d311b,D._d330f);
		create(D._d309b,D._d310b,D._d331f);
		create(D._d308b,D._d309b,D._d333f);
		create(D._d307b,D._d308b,D._d334f);
		create(D._d306b,D._d307b,-1,D._d320b,-1,-1,D._d340f);
		create(D._d305b,D._d307b,-1,D._d306b,-1,-1,D._d341f);
		create(D._d304b,-1,D._d305b,-1,D._d341f,-1,D._d342f);
		create(D._d303b,D._d343f,D._d343f,-1,-1,-1);
		create(D._d302b,D._d306b,-1,-1,-1,-1,D._j301f);
		create(D._d301b,D._d302b,D._j301f);

		/** J BLOCK BACK PASS **/
		create(D._j312b,-1,D._d303b,D._d301b,D._j303f,-1,D._j302f);
		create(D._j311b,-1,D._j302f,-1,D._d301b,-1,D._j304f);
		create(D._j310b,D._j311b,D._j305f);
		create(D._j309b,D._j310b,D._j306f);
		create(D._j308b,-1,-1,D._j308f,D._j309b,-1,D._j307f);
		create(D._j307b,-1,D._j309b,D._j307f,-1,-1,D._j308f);
		create(D._j306b,D._j307b,D._j309f);
		create(D._j305b,-1,D._j306b,D._j306b,-1,-1,D._j312f);
		create(D._j304b,D._j305b,D._j313f);
		create(D._j303b,D._j304b,D._j313f);
		create(D._j302b,D._j303b,D._j314f);
		create(D._j301b,D._j302b,D._j315f);
	}
}
