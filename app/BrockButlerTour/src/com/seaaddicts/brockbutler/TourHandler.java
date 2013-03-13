/**
 * TourHandler.java
 * Brock Butler
 * Main activity for the tour portion of Brock Butler.
 * Created by Taras Mychaskiw 2013-02-20
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */
package com.seaaddicts.brockbutler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TourHandler extends Activity {
	private TourNode[] nodes;
	private final int numImages = R.drawable.j328 - R.drawable._a301b + 1;
	private TourInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_handler);

		RelativeLayout rl = (RelativeLayout)findViewById(R.id.screen);
		ImageButton[] buttons = new ImageButton[5];
		buttons[0] = (ImageButton)findViewById(R.id.outerleft);
		buttons[1] = (ImageButton)findViewById(R.id.innerleft);
		buttons[2] = (ImageButton)findViewById(R.id.center);
		buttons[3] = (ImageButton)findViewById(R.id.innerright);
		buttons[4] = (ImageButton)findViewById(R.id.outerright);
		info = new TourInfo(rl,buttons,getApplicationContext());
		initNodes();
		nodes[idx(R.drawable._a301f)].paint(info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.activity_tour_handler,menu);
		return(true);
	}

	/**
	 * Pops the previous TourNode from the stack and goes to that node.
	 */
	public void goBack(MenuItem item){
		if (!info.history.empty())
			info.history.pop().paint(info);
	}

	/**
	 * Goes to the node in the tour which is logically turning around.
	 */
	public void turnAround(MenuItem item){ 
		if (info.current.canTurnAround()){
			info.history.push(info.current);
			info.current.turnAroundNode.paint(info);
		}
		else
			Toast.makeText(info.context,"No data available",Toast.LENGTH_SHORT).show();
	}

	/**
	 * Teleports the user to the specified block.
	 */
	public void teleport(MenuItem item){
		info.history.push(info.current);
		switch (item.getItemId()){
		case R.id.teleport_a_block: nodes[idx(R.drawable._a301f)].paint(info); return;
		case R.id.teleport_b_block: nodes[idx(R.drawable._b301f)].paint(info); return;
		case R.id.teleport_c_block: nodes[idx(R.drawable._c301f)].paint(info); return;
		case R.id.teleport_d_block: nodes[idx(R.drawable._d301b)].paint(info); return;
		case R.id.teleport_e_block: nodes[idx(R.drawable._e301f)].paint(info); return;
		case R.id.teleport_f_block: nodes[idx(R.drawable._f301f)].paint(info); return;
		case R.id.teleport_g_block: nodes[idx(R.drawable._h338f)].paint(info); return;
		case R.id.teleport_h_block: nodes[idx(R.drawable._f301f)].paint(info); return;
		case R.id.teleport_j_block: nodes[idx(R.drawable._j301f)].paint(info); return;
		}
	}

	/**
	 * Ends the tour and destroys the Activity
	 */
	public void endTour(MenuItem item){
		super.onBackPressed();
	}
	
	/**
	 * Overrides the back button to pop previous TourNode from the stack
	 * and goes to that node.
	 */
	@Override
	public void onBackPressed(){
		goBack(null);
	}

	/**
	 * @param r - image resource value
	 * @return index in `nodes` of TourNode which shows the image `r`
	 */
	private int idx(int r){
		return(r - R.drawable._a301b);
	}

	/**
	 * Adds a new TourRoom to nodes.
	 * @param r - the image resource value
	 * @param roomNumber - the title of the room, such as A323
	 */
	private void room(int r, String roomNumber){
		nodes[idx(r)] = new TourRoom(r,roomNumber);
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
	private void hall(int r, int ll, int ul, int c, int ur, int lr, int ta){
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
	private void hall(int r, int c){ hall(r,-1,-1,c,-1,-1,-1); }
	private void hall(int r, int c, int ta){ hall(r,-1,-1,c,-1,-1,ta); }
	private void hall (int r, int ll, int ul, int c, int ur, int lr){ hall(r,ll,ul,c,ur,lr,-1); }

	/**
	 * Giant method to link all of the images together. The fun stuff. Right here.
	 */
	@SuppressWarnings("static-access")
	private void initNodes(){
		R.drawable D = new R.drawable();
		nodes = new TourNode[numImages];

		/** A BLOCK ROOMS **/
		room(D.a323,"A323");

		/** B BLOCK ROOMS **/
		room(D.b303a,"B303A");
		room(D.b303b,"B303B");
		hall(D.b303,-1,-1,D.b303b,D.b303a,-1);

		/** C BLOCK ROOMS **/
		room(D.c300,"C300");
		room(D.c303,"C303");
		room(D.c304,"C304");
		room(D.c306_1,"C306");
		room(D.c306_2,"C306");
		hall(D.c306_3,D.c306_2,-1,-1,-1,D.c306_1);
		room(D.c308,"C308");
		room(D.c310,"C310");

		/** D BLOCK ROOMS **/
		room(D.d300,"D300");
		room(D.d301,"D301");
		room(D.d303,"D303");
		room(D.d304,"D304");
		room(D.d308,"D308");
		room(D.d309_1,"D309");
		room(D.d309_2,"D309");
		room(D.d310,"D310");
		room(D.d314,"D314");
		room(D.d315,"D315");
		room(D.d316,"D316");
		room(D.d317,"D317");
		room(D.d318,"D318");
		room(D.d319,"D319");
		room(D.d350l,"D350L");

		/** E BLOCK ROOMS **/
		room(D.e302,"E302");
		room(D.e303,"E303");
		room(D.e304,"E304");

		/** F BLOCK ROOMS **/
		/* N/A */
		/** G BLOCK ROOMS **/
		room(D.g301_2,"G301");
		room(D.g301_3,"G301");
		hall(D.g301_1,D.g301_2,-1,-1,-1,D.g301_3);
		room(D.g305,"G305");

		/** H BLOCK ROOMS **/
		room(D.h300,"H300");
		room(D.h302,"H302");
		room(D.h303,"H303");
		room(D.h304a,"H304A");
		hall(D.h304,-1,-1,-1,-1,D.h304a);
		hall(D.h306,/*D.h309a6*/-1,-1,-1,/*D.h306a6*/-1,-1);
		hall(D.h309,-1,/*D.h306a9*/-1,-1,-1,/*D.h309a9*/-1);
		hall(D.h306a6,-1,-1,-1,D.h309,-1);
		hall(D.h306a9,-1,D.h306,-1,-1,-1,D.h306a6);
		hall(D.h309a6,D.h309,-1,-1,-1,-1);
		hall(D.h309a9,D.h306,-1,-1,-1,-1,D.h309a6);
		nodes[idx(D.h306)].setOuterLeftNode(nodes[idx(D.h309a6)]);
		nodes[idx(D.h306)].setInnerRightNode(nodes[idx(D.h306a6)]);
		nodes[idx(D.h309)].setInnerLeftNode(nodes[idx(D.h309a9)]);
		nodes[idx(D.h309)].setOuterRightNode(nodes[idx(D.h306a9)]);
		room(D.h310,"H310");
		room(D.h313,"H313");
		room(D.h315,"H315");
		room(D.h316,"H316");
		room(D.h317,"H317");
		room(D.h318a,"H318A");
		hall(D.h318,-1,-1,-1,D.h318a,-1);
		room(D.h320,"H320");
		room(D.h321,"H321");
		room(D.h322,"H322");
		room(D.h323,"H323");
		room(D.h324,"H324");

		/** J BLOCK ROOMS **/
		room(D.j301,"J301");
		room(D.j310,"J310");
		room(D.j327,"J327");
		room(D.j328,"J328");

		/** J BLOCK FORWARD PASS **/
		hall(D._j315f,-1,-1,-1,-1,D.j301);
		hall(D._j314f,D._j315f);
		hall(D._j313f,D._j314f);
		hall(D._j312f,-1,-1,-1,D._j313f,-1);
		hall(D._j311f,D._j312f);
		hall(D._j310f,D._j311f);
		hall(D._j309f,D._j310f);
		hall(D._j308f,D._j309f);
		hall(D._j307f,-1);
		hall(D._j306f,D._j307f,-1,-1,-1,D._j308f);
		hall(D._j305f,D._j306f);
		hall(D._j304f,-1,D.j327,D._j305f,D.j328,-1);
		hall(D._j303f,D._j304f);
		hall(D._j302f,-1,D.j310,-1,D.j301,-1);
		hall(D._j301f,-1,-1,D._j303f,D._j302f,-1);

		/** D BLOCK FORWARD PASS **/
		hall(D._d343f,/*D._d304b*/-1,-1,-1,/*D._j312b*/-1,-1);
		hall(D._d342f,D._d343f);
		hall(D._d341f,-1,D.g310,D._j301f,D._d342f,-1);
		hall(D._d340f,-1,-1,D._d341f,-1,/*D._d307b*/-1);
		hall(D._d339f,-1,-1,D._d340f,/*D._d307b*/-1,D.d309_2);
		hall(D._d338f,D._d339f);
		hall(D._d337f,-1,-1,D._d338f,D.d309_1,-1);
		hall(D._d336f,D._d337f);
		hall(D._d335f,/*D._d306b*/-1,-1,-1,-1,D._d341f);
		hall(D._d334f,-1,-1,D._d335f,-1,D.d310);
		hall(D._d333f,-1,-1,D._d334f,-1,D.d314);
		hall(D._d332f,D._d333f);
		hall(D._d331f,-1,-1,D._d332f,-1,D.d315);
		hall(D._d330f,-1,-1,D._d331f,-1,D.d316);
		hall(D._d329f,-1,-1,D._d330f,-1,D.d317);
		hall(D._d328f,-1,D._d329f,D.d318,-1,-1);
		hall(D._d327f,D._d328f);
		hall(D._d326f,D.d319,-1,D._d327f,-1,-1);
		hall(D._d325f,D._d326f);
		hall(D._d324f,D._d325f);
		hall(D._d323f,D._d324f);
		hall(D._d322f,D._d323f);
		hall(D._d321f,D._d322f);
		hall(D._d320f,D._d321f);
		hall(D._d319f,D._d320f);
		hall(D._d318f,-1,-1,D._d319f,/*D._d326b*/-1,-1);
		hall(D._d317f,-1,D._d336f,D.d308,D._d318f,-1);
		hall(D._d316f,D.d304,-1,D._d317f,-1,-1);
		hall(D._d315f,D.d303,-1,D._d316f,-1,-1);
		hall(D._d314f,D._d315f);
		hall(D._d313f,D.d301,-1,D._d314f,-1,-1);
		hall(D._d312f,/*D._d319b*/-1,-1,-1,-1,D._d319f);
		hall(D._d311f,D._d312f);
		hall(D._d310f,-1,D._d311f,-1,-1,-1);
		hall(D._d309f,D._d310f);
		hall(D._d308f,D._d309f);
		hall(D._d307f,D._d308f);
		hall(D._d306f,D._d307f);
		hall(D._d305f,D._d306f);
		hall(D._d304f,-1,D.d350l,-1,-1,-1);
		hall(D._d303f,-1,D._d305f,-1,D._d304f,-1);
		hall(D._d302f,D.d300,-1,D._d313f,-1,D._d303f);
		hall(D._d301f,D._d302f);

		/** C BLOCK FORWARD PASS **/
		hall(D._c312f,-1,-1,-1,D._d301f,-1);
		hall(D._c311f,-1,D.c310,D._c312f,-1,-1);
		hall(D._c310f,D._c311f);
		hall(D._c309f,D.c306_3,-1,D._c310f,-1,-1);
		hall(D._c308f,D._c309f);
		hall(D._c307f,-1,D._c308f,-1,-1,-1);
		hall(D._c306f,-1,-1,/*D._c306b*/-1,D._c307f,-1);
		hall(D._c305f,D.c304,-1,D._c306f,-1,-1);
		hall(D._c304f,D.c303,-1,D._c305f,-1,-1);
		hall(D._c303f,D._c304f);
		hall(D._c302f,D._c303f);
		hall(D._c301f,-1,-1,-1,D._c302f,-1);
		hall(D._c316f,-1,D._c306f,/*D._c310b*/-1,-1,-1);
		hall(D._c315f,D._c316f);
		hall(D._c314f,D.c308,-1,D._c315f,-1,-1);
		hall(D._c313f,-1,-1,-1,D._c314f,-1);

		/** F BLOCK FORWARD PASS **/
		hall(D._f308f,D._c313f);
		hall(D._f307f,D._f308f);
		hall(D._f306f,D._f307f);
		hall(D._f305f,D._f306f);
		hall(D._f304f,D._f305f);
		hall(D._f303f,D._f304f);
		hall(D._f302f,D._f303f);
		hall(D._f301f,-1,D._f302f,-1,-1,/*D._h301b*/-1);
		
		/** G BLOCK FORWARD PASS **/
		hall(D._g313f,-1);
		hall(D._g312f,D._g313f);
		hall(D._g311f,D.g305,-1,D._g312f,-1,-1);
		hall(D._g310f,D._g311f);
		hall(D._g309f,D._g310f);
		hall(D._g308f,D._g309f);
		hall(D._g307f,D._g308f);
		hall(D._g306f,D._g307f);
		hall(D._g305f,D.g301_1,-1,D._g306f,-1,-1);
		hall(D._g304f,D._g305f);
		hall(D._g303f,D._g304f);
		hall(D._g302f,-1,-1,D._g303f,-1,-1);
		hall(D._g301f,-1,D._g302f,-1,-1,-1,D._f301f);

		/** H BLOCK FORWARD PASS **/
		hall(D._h338f,-1,D._g301f,-1,D._f302f,-1);
		hall(D._h337f,D._h338f);
		hall(D._h336f,D._h337f);
		hall(D._h335f,D._h336f);
		hall(D._h334f,D._h335f);
		hall(D._h333f,-1,/*D._h303b*/-1,D._h334f,-1,-1);
		hall(D._h332f,D._h333f);
		hall(D._h331f,D._h332f);
		hall(D._h330f,D._h331f);
		hall(D._h329f,-1,D._h334f,/*D._h303b*/-1,-1,/*D._h322b*/-1);
		hall(D._h328f,-1,D.h324,D._h329f,-1,-1);
		hall(D._h327f,D.h323,-1,D._h328f,-1,-1);
		hall(D._h326f,D._h327f);
		hall(D._h325f,D._h326f);
		hall(D._h324f,D.h320,-1,D._h325f,-1,-1);
		hall(D._h323f,-1,-1,D._h324f,D.h322,D.h321);
		hall(D._h322f,D.h318,-1,D._h323f,-1,-1);
		hall(D._h321f,-1,D.h316,D._h322f,-1,D.h317);
		hall(D._h320f,D._h321f);
		hall(D._h319f,-1,-1,-1,-1,D._h320f);
		hall(D._h318f,-1,D.h315,D._h319f,-1,-1);
		hall(D._h317f,D.h313,-1,D._h318f,-1,-1);
		hall(D._h316f,D._h317f);
		hall(D._h315f,D._h316f);
		hall(D._h314f,-1);
		hall(D._h313f,D._h314f);
		hall(D._h312f,-1,D.h310,D._h313f,D._h315f,-1);
		hall(D._h311f,-1,-1,-1,D.h310,D._h312f);
		hall(D._h310f,-1,-1,D._h311f,D.h309,-1);
		hall(D._h309f,-1,-1,D._h310f,-1,D.h306);
		hall(D._h308f,-1,-1,D._h309f,-1,D.h304);
		hall(D._h307f,D.h303,-1,D._h308f,-1,-1);
		hall(D._h306f,D._h307f);
		hall(D._h305f,-1,-1,D._h306f,-1,D.h302);
		hall(D._h304f,-1,D.h300,D._h305f,-1,-1);
		hall(D._h303f,-1,D._h304f,-1,D._h330f,-1);
		hall(D._h302f,D._h303f);
		hall(D._h301f,D._h302f);
		
		/** E BLOCK FORWARD PASS **/
		hall(D._e311f,-1);
		hall(D._e310f,-1,D._h301f,-1,D._e311f,-1);
		hall(D._e309f,D._e310f);
		hall(D._e308f,D._e309f);
		hall(D._e307f,-1,-1,D._e308f,-1,-1);
		hall(D._e306f,D.e304,-1,D._e307f,-1,-1);
		hall(D._e305f,D.e303,-1,D._e306f,-1,-1);
		hall(D._e304f,D._e305f);
		hall(D._e303f,-1,D.e302,D._e304f,-1,-1);
		hall(D._e302f,D._e303f);
		hall(D._e301f,-1,-1,-1,D._e302f,-1);

		/** B BLOCK FORWARD PASS **/
		hall(D._b314f,D._c301f);
		hall(D._b313f,D._b314f);
		hall(D._b312f,D._b314f);
		hall(D._b311f,-1,-1,-1,D._b312f,-1);
		hall(D._b310f,-1,D._b311f,D._b313f,-1,-1);
		hall(D._b309f,D._b310f);
		hall(D._b308f,-1,D._b309f,-1,-1,-1);
		hall(D._b307f,D._e301f);
		hall(D._b306f,D._b307f);
		hall(D._b305f,D._b306f);
		hall(D._b304f,-1,-1,D._b305f,D.b303,-1);
		hall(D._b303f,D._b304f);
		hall(D._b302f,-1,D._b303f,-1,D._b308f,-1);
		hall(D._b301f,-1,-1,-1,D._b302f,-1);

		/** A BLOCK FORWARD PASS **/
		hall(D._a309f,-1,D._b301f,-1,-1,-1);
		hall(D._a308f,D._a309f);
		hall(D._a307f,D._a308f);
		hall(D._a306f,D._a307f);
		hall(D._a305f,-1,-1,D._a306f,-1,D.a323);
		hall(D._a304f,D._a305f);
		hall(D._a303f,D._a304f);
		hall(D._a302f,D._a303f);
		hall(D._a301f,D._a302f);

		/** A BLOCK BACK PASS **/
		hall(D._a307b,-1,D._a302f);
		hall(D._a306b,D._a307b,D._a303f);
		hall(D._a305b,D._a306b,D._a304f);
		hall(D._a304b,-1,D.a323,D._a305b,-1,-1,D._a307f);
		hall(D._a303b,D._a304b,D._a308f);
		hall(D._a302b,-1,-1,-1,D._a303b,-1,D._a309f);
		hall(D._a301b,D._a302b,D._b301f);

		/** B BLOCK BACK PASS **/
		hall(D._b317b,D._a301b,D._b301f);
		hall(D._b316b,-1,D._b317b,-1,D._b303f,-1,D._b302f);
		hall(D._b313b,-1,-1,-1,D._b316b,-1,D._b309f);
		hall(D._b312b,D._b313b,D._b313f);
		hall(D._b311b,D._b313b,D._b311f);
		hall(D._b310b,D._b311b,D._b312f);
		hall(D._b309b,-1,-1,D._b312b,D._b310b,-1,D._b314f);
		hall(D._b308b,-1,D._b309b,-1,-1,-1,D._b314f);
		hall(D._b306b,-1,-1,-1,D._b308b,-1,D._c301f);
		hall(D._b305b,-1,D._b308f,-1,D._b317b,-1,D._b303f);
		hall(D._b304b,D._b305b,D._b304f);
		hall(D._b303b,D.b303,-1,D._b304b,-1,-1,D._b305f);
		hall(D._b302b,D._b303b,D._b306f);
		hall(D._b301b,D._b302b,D._e301f);

		/** E BLOCK BACK PASS **/
		hall(D._e309b,-1,-1,D._b301b,-1,D.e302,D._e303f);
		hall(D._e308b,D._e309b,D._e304f);
		hall(D._e307b,-1,-1,D._e308b,-1,D.e303,D._e305f);
		hall(D._e306b,-1,-1,D._e307b,-1,D.e304,D._e306f);
		hall(D._e305b,D._e306b,D._e307f);
		hall(D._e304b,D._e305b,D._e308f);
		hall(D._e303b,D._e304b,D._e309f);
		hall(D._e302b,D._e303b,D._e311f);
		hall(D._e301b,D._e311f,-1,D._e303b,-1,-1,D._h301f);

		/**  H BLOCK BACK PASS **/
		hall(D._h326b,D._e301b,D._h302f);
		hall(D._h325b,-1,-1,D._h326b,-1,D._h304f,D._h330f);
		hall(D._h324b,D._h325b,D._h331f);
		hall(D._h323b,D._h324b,D._h332f);
		hall(D._h322b,D._h323b,D._h333f);
		hall(D._h321b,D._h330f,-1,-1,-1,D._h326b,D._h304f);
		hall(D._h320b,D.h302,-1,D._h321b,D.h300,-1,D._h305f);
		hall(D._h319b,-1,-1,D._h320b,-1,D.h303,D._h307f);
		hall(D._h318b,-1,D.h304,D._h319b,-1,-1,D._h308f);
		hall(D._h317b,-1,D.h306,D._h318b,-1,-1,D._h309f);
		hall(D._h316b,-1,D.h309,D._h317b,-1,-1,D._h311f);
		hall(D._h315b,-1,D._h316b,-1,D.h310,D._h313f,D._h315f);
		hall(D._h314b,D._h315b,D._h316f);
		hall(D._h313b,-1,-1,D._h314b,D.h313,-1,D._h317f);
		hall(D._h312b,-1,-1,D._h313b,D.h315,-1,D._h319f);
		hall(D._h311b,-1,D.h317,D._h312b,D.h316,D.h318,D._h322f);
		hall(D._h310b,D._h311b,D._h323f);
		hall(D._h309b,-1,-1,D._h310b,-1,D.h320,D._h324f);
		hall(D._h308b,D._h309b,D._h325f);
		hall(D._h307b,D.h322,D.h321,D._h308b,-1,-1,D._h326f);
		hall(D._h306b,-1,-1,D._h307b,D.h323,D.h324,D._h327f);
		hall(D._h305b,D._h306b,D._h328f);
		hall(D._h304b,D._h305b,D._h329f);
		hall(D._h303b,-1,D._h322b,-1,D._h304b,-1,D._h334f);
		hall(D._h302b,D._h303b,D._h335f);
		hall(D._h301b,D._h302b,D._h337f);

		/** G BLOCK BACK PASS **/
		hall(D._g309b,-1,-1,-1,D._f301f,-1,D._g302f);
		hall(D._g308b,-1,D._g309b,-1,-1,-1,D._g303f);
		hall(D._g307b,D._g308b,D._g304f);
		hall(D._g306b,-1,-1,D._g307b,-1,D.g301_1,D._g305f);
		hall(D._g305b,D._g306b,D._g306f);
		hall(D._g304b,D._g305b,D._g309f);
		hall(D._g303b,D._g304b,D._g310f);
		hall(D._g302b,-1,-1,D._g303b,-1,D.g305,D._g311f);
		hall(D._g301b,-1,-1,D._g302b,D._g313f,-1,D._g312f);

		/** F BLOCK BACK PASS **/
		hall(D._f305b,-1,-1,D._f301f,D._g301f,-1,D._f303f);
		hall(D._f304b,D._f305b,D._f304f);
		hall(D._f303b,D._f304b,D._f305f);
		hall(D._f302b,D._f303b,D._f306f);
		hall(D._f301b,-1,-1,-1,D._f302b,-1,D._f308f);

		/** C BLOCK BACK PASS **/
		hall(D._c315b,D._b306b,D._c302f);
		hall(D._c314b,-1,-1,D._c315b,-1,D.c300,D._c303f);
		hall(D._c313b,D._c314b,D._c304f);
		hall(D._c312b,-1,-1,D._c313b,-1,D.c303,D._c305f);
		hall(D._c311b,-1,-1,D._c312b,-1,D.c304,D._c306f);
		hall(D._c310b,D._c311b,D._c306f);
		hall(D._c309b,-1,D._f301b,-1,-1,-1,D._c313f);
		hall(D._c308b,-1,-1,D._c309b,-1,D.c308,D._c314f);
		hall(D._c307b,D._c308b,D._c315f);
		hall(D._c306b,D._c307b,D._c316f);
		hall(D._c305b,-1,-1,-1,D._c310b,D._c306b,D._c308f);
		hall(D._c304b,-1,-1,D._c305b,-1,D.c306_3,D._c309f);
		hall(D._c303b,D._c304b,D._c310f);
		hall(D._c302b,-1,-1,-1,D._c303b,-1,D._c311f);
		hall(D._c301b,-1,D._c302b,D.c310,-1,-1,D._c312f);

		/** D BLOCK BACK PASS **/
		hall(D._d334b,D._c301b,D._d301f);
		hall(D._d333b,D._d334b,-1,D.d300,-1,D._d313f,D._d303f);
		hall(D._d332b,D._d333b,D._d303f);
		hall(D._d331b,-1,-1,D._d304f,D._d332b,-1,D._d305f);
		hall(D._d330b,D._d331b,D._d306f);
		hall(D._d329b,D._d330b,D._d307f);
		hall(D._d328b,D._d329b,D._d308f);
		hall(D._d327b,D._d328b,D._d309f);
		hall(D._d326b,-1,-1,-1,D._d327b,-1,D._d310f);
		hall(D._d325b,-1,D._d303f,D._d334b,D.d300,D.d301,D._d314f);
		hall(D._d324b,-1,-1,D._d325b,-1,D.d303,D._d315f);
		hall(D._d323b,-1,-1,D._d324b,-1,D.d304,D._d317f);
		hall(D._d322b,D.d308,D._d318f,D._d323b,-1,-1,D._d337f);
		hall(D._d321b,D.d309_1,-1,D._d322b,-1,-1,D._d338f);
		hall(D._d320b,D.d309_2,-1,D._d321b,-1,-1,D._d340f);
		hall(D._d319b,-1,D._d326b,D._d323b,D._d336f,-1,D._d319f);
		hall(D._d318b,D._d319b,D._d320f);
		hall(D._d317b,D._d318b,D._d321f);
		hall(D._d316b,D._d317b,D._d322f);
		hall(D._d315b,D._d316b,D._d323f);
		hall(D._d314b,D._d315b,D._d323f);
		hall(D._d313b,-1,-1,D._d314b,-1,D.d319,D._d325f);
		hall(D._d312b,D._d313b,D._d327f);
		hall(D._d311b,D.d317,D.d318,-1,D._d312b,-1,D._d329f);
		hall(D._d310b,D.d316,-1,D._d311b,-1,-1,D._d330f);
		hall(D._d309b,D.d315,-1,D._d310b,-1,-1,D._d331f);
		hall(D._d308b,D.d314,-1,D._d309b,-1,-1,D._d333f);
		hall(D._d307b,D.d310,-1,D._d308b,-1,-1,D._d334f);
		hall(D._d306b,D._d307b,-1,D._d320b,-1,-1,D._d340f);
		hall(D._d305b,-1,D._d307b,D._d306b,-1,-1,D._d341f);
		hall(D._d304b,-1,D._d305b,-1,D._d341f,-1,D._d342f);
		hall(D._d303b,D._d343f,D._d343f,-1,-1,-1);
		hall(D._d302b,D._d342f,D._d306b,-1,-1,D.g310,D._j301f);
		hall(D._d301b,D._d302b,D._j301f);

		/** J BLOCK BACK PASS **/
		hall(D._j312b,-1,D._d303b,D._d301b,D._j303f,-1,D._j302f);
		hall(D._j311b,-1,D._j302f,-1,D._d301b,-1,D._j304f);
		hall(D._j310b,-1,D.j328,D._j311b,-1,D.j327,D._j305f);
		hall(D._j309b,D._j310b,D._j306f);
		hall(D._j308b,-1,-1,D._j308f,D._j309b,-1,D._j307f);
		hall(D._j307b,-1,D._j309b,D._j307f,-1,-1,D._j308f);
		hall(D._j306b,D._j307b,D._j309f);
		hall(D._j305b,-1,D._j306b,-1,-1,-1,D._j312f);
		hall(D._j304b,D._j305b,D._j313f);
		hall(D._j303b,D.j310,-1,D._j304b,-1,-1,D._j313f);
		hall(D._j302b,D._j303b,D._j314f);
		hall(D._j301b,D._j302b,D._j315f);

		/** BUTTON FIXES **/
		//hall(D._d343f,/*D._d304b*/-1,-1,-1,/*D._j312b*/-1,-1);
		nodes[idx(D._d343f)].setOuterLeftNode(nodes[idx(D._d304b)]);
		nodes[idx(D._d343f)].setInnerRightNode(nodes[idx(D._j312b)]);
		//hall(D._d340f,-1,-1,D._d341f,-1,/*D._d307b*/-1);
		nodes[idx(D._d340f)].setOuterRightNode(nodes[idx(D._d307b)]);
		//hall(D._d339f,-1,-1,D._d340f,/*D._d307b*/-1,D.d309_2);
		nodes[idx(D._d339f)].setInnerRightNode(nodes[idx(D._d307b)]);
		//hall(D._d335f,/*D._d306b*/-1,-1,-1,-1,D._d341f);
		nodes[idx(D._d335f)].setOuterLeftNode(nodes[idx(D._d306b)]);
		//hall(D._d318f,-1,-1,D._d319f,/*D._d326b*/-1,-1);
		nodes[idx(D._d318f)].setInnerRightNode(nodes[idx(D._d326b)]);
		//hall(D._d312f,/*D._d319b*/-1,-1,-1,-1,D._d319f);
		nodes[idx(D._d312f)].setOuterLeftNode(nodes[idx(D._d319b)]);
		//hall(D._c306f,-1,-1,/*D._c306b*/-1,D._c307f,-1);
		nodes[idx(D._c306f)].setCenterNode(nodes[idx(D._c306b)]);
		//hall(D._c316f,-1,D._c306f,/*D._c310b*/-1,-1,-1);
		nodes[idx(D._c316f)].setCenterNode(nodes[idx(D._c310b)]);
		//hall(D._f301f,-1,D._f302f,-1,-1,/*D._h301b*/-1);
		nodes[idx(D._f301f)].setInnerRightNode(nodes[idx(D._h301b)]);
		//hall(D._h333f,-1,/*D._h303b*/-1,D._h334f,-1,-1);
		nodes[idx(D._h333f)].setInnerLeftNode(nodes[idx(D._h303b)]);
		//hall(D._h329f,-1,D._h334f,/*D._h303b*/-1,-1,/*D._h322b*/-1);
		nodes[idx(D._h329f)].setCenterNode(nodes[idx(D._h303b)]);
		nodes[idx(D._h329f)].setOuterRightNode(nodes[idx(D._h322b)]);

		/** TURN AROUND FIXES **/
		nodes[idx(D._a301f)].setTurnAroundNode(nodes[idx(D._a307b)]);
		nodes[idx(D._a305f)].setTurnAroundNode(nodes[idx(D._a305b)]);
		nodes[idx(D._a306f)].setTurnAroundNode(nodes[idx(D._a305b)]);

		nodes[idx(D._b307f)].setTurnAroundNode(nodes[idx(D._b302b)]);
		nodes[idx(D._b308f)].setTurnAroundNode(nodes[idx(D._b317b)]);
		nodes[idx(D._b310f)].setTurnAroundNode(nodes[idx(D._b313b)]);

		nodes[idx(D._c307f)].setTurnAroundNode(nodes[idx(D._c310b)]);

		nodes[idx(D._d302f)].setTurnAroundNode(nodes[idx(D._d334b)]);
		nodes[idx(D._d311f)].setTurnAroundNode(nodes[idx(D._d326b)]);
		nodes[idx(D._d312f)].setTurnAroundNode(nodes[idx(D._d326b)]);
		nodes[idx(D._d313f)].setTurnAroundNode(nodes[idx(D._d325b)]);
		nodes[idx(D._d316f)].setTurnAroundNode(nodes[idx(D._d324b)]);
		nodes[idx(D._d318f)].setTurnAroundNode(nodes[idx(D._d319b)]);
		nodes[idx(D._d324f)].setTurnAroundNode(nodes[idx(D._d313b)]);
		nodes[idx(D._d326f)].setTurnAroundNode(nodes[idx(D._d313b)]);
		nodes[idx(D._d328f)].setTurnAroundNode(nodes[idx(D._d312b)]);
		nodes[idx(D._d332f)].setTurnAroundNode(nodes[idx(D._d309b)]);
		nodes[idx(D._d335f)].setTurnAroundNode(nodes[idx(D._d307b)]);
		nodes[idx(D._d336f)].setTurnAroundNode(nodes[idx(D._d322b)]);
		nodes[idx(D._d339f)].setTurnAroundNode(nodes[idx(D._d306b)]);

		nodes[idx(D._e302f)].setTurnAroundNode(nodes[idx(D._e309b)]);
		nodes[idx(D._e310f)].setTurnAroundNode(nodes[idx(D._e303b)]);

		nodes[idx(D._f302f)].setTurnAroundNode(nodes[idx(D._f305b)]);
		nodes[idx(D._f307f)].setTurnAroundNode(nodes[idx(D._f302b)]);

		nodes[idx(D._g307f)].setTurnAroundNode(nodes[idx(D._g305b)]);
		nodes[idx(D._g308f)].setTurnAroundNode(nodes[idx(D._g304b)]);
		nodes[idx(D._g313f)].setTurnAroundNode(nodes[idx(D._g301b)]);

		nodes[idx(D._h303f)].setTurnAroundNode(nodes[idx(D._h326b)]);
		nodes[idx(D._h306f)].setTurnAroundNode(nodes[idx(D._h320b)]);
		nodes[idx(D._h310f)].setTurnAroundNode(nodes[idx(D._h317b)]);
		nodes[idx(D._h312f)].setTurnAroundNode(nodes[idx(D._h316b)]);
		nodes[idx(D._h318f)].setTurnAroundNode(nodes[idx(D._h313b)]);
		nodes[idx(D._h320f)].setTurnAroundNode(nodes[idx(D._h311b)]);
		nodes[idx(D._h321f)].setTurnAroundNode(nodes[idx(D._h311b)]);
		nodes[idx(D._h336f)].setTurnAroundNode(nodes[idx(D._h302b)]);
		nodes[idx(D._h338f)].setTurnAroundNode(nodes[idx(D._h301b)]);

		nodes[idx(D._j303f)].setTurnAroundNode(nodes[idx(D._j311b)]);
		nodes[idx(D._j310f)].setTurnAroundNode(nodes[idx(D._j306b)]);
		nodes[idx(D._j311f)].setTurnAroundNode(nodes[idx(D._j305b)]);
	}
}
