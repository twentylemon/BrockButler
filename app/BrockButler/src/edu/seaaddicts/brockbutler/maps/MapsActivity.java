package edu.seaaddicts.brockbutler.maps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.seaaddicts.brockbutler.R;
import edu.seaaddicts.brockbutler.TouchImageView;

public class MapsActivity extends Activity {
	private static final String tag = "MapsActivity";

	private Bitmap mMapBitmap;
	private TextView mTemp;
	private Button stop;
	private Button start;
	private Button resume;

	private Handler mHandler;
	private TouchImageView mMapImage;
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
				case MapsHandler.MAPS_REQUEST_UPDATE:
					Log.d("MAIN HANDLER", "YAYAAA!!!");
					break;
				case MapsHandler.THREAD_UPDATE_POSITION:
					Log.d(tag,
							"-----+++++ Got THREAD_UPDATE_POSITION message. +++++-----");
					break;
				default:
					Log.d(tag,
							"-----+++++ Got THREAD_UPDATE_POSITION message. +++++-----");
					mTemp.setText("#" + msg.what);
					break;
				}
			}
		};
		mMapsHandler = new MapsHandler(mHandler);
	}

	private void init() {
		mMapImage = (TouchImageView) findViewById(R.id.imgv_map);
		mMapImage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(MapsHandler.MAPS_REQUEST_UPDATE);
			}
		});
		start = (Button) findViewById(R.id.btnstart);
		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(MapsHandler.THREAD_REQUEST_START);
			}
		});
		stop = (Button) findViewById(R.id.btnstop);
		stop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapsHandler.sendEmptyMessage(MapsHandler.THREAD_REQUEST_PAUSE);
			}
		});

		resume = (Button) findViewById(R.id.btnresume);
		resume.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mMapsHandler
						.sendEmptyMessage(MapsHandler.THREAD_REQUEST_RESUME);
			}
		});

        mMapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mch_maps);
        mMapImage.setImageBitmap(mMapBitmap);
        Log.d("----+++++ TouchImageView +++++----","(" + mMapImage.getX() + "," + mMapImage.getY() + ")");
	}

	@Override
	public void onBackPressed() {
		mMapsHandler.sendEmptyMessage(MapsHandler.THREAD_REQUEST_PAUSE);
		mMapsHandler = null;
		super.onBackPressed();
	}
}
