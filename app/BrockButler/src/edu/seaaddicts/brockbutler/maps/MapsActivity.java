package edu.seaaddicts.brockbutler.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;

public class MapsActivity extends Activity {
	private static final int MAP_REQUEST_UPDATE = 1;

	final static int POSITION_UPDATE = 0;
	final static int WIFI_DOWN = 1;
	final static int DESTINATION_ARRIVAL = 2;
	private TextView mTemp;
	private Button stop;
	private Button start;

	Handler mHandler;
	private ImageButton img;
	private MapsHandler mMapsHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		init();
		mTemp = (TextView) findViewById(R.id.txtv_count);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MAP_REQUEST_UPDATE:
					Log.d("MAIN HANDLER", "YAYAAA!!!");
					break;
				default:
					Log.d("MAIN HANDLER", "UPDATING!!!");
					mTemp.setText("#" + msg.what);
					break;
				}
			}
		};
		mMapsHandler = new MapsHandler(mHandler);
	}

	private void init() {
		img = (ImageButton) findViewById(R.id.imgv_map);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(1);
			}
		});
		start = (Button) findViewById(R.id.btnstart);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(4);
			}
		});
		stop = (Button) findViewById(R.id.btnstop);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(5);
			}
		});
	}

}
