package edu.seaaddicts.brockbutler.maps;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MapsHandler extends Handler {

	private static final int MAP_REQUEST_UPDATE = 1;
	private static final int MAP_REQUEST_LOCATION = 2;
	private static final int MAP_REQUEST_DIRECTIONS = 3;
	private static final int MAP_REQUEST_START = 4;
	private static final int MAP_REQUEST_STOP = 5;

	private Handler mHandler;
	private Handler mMainHandler;
	private boolean mIsMapRunning = false;

	public MapsHandler(Looper myLooper) {
		super(myLooper);
		Log.d("EXTERNAL HANDLER", "-----++++++CREATING HANDLE++++++------");
		mMainHandler = new Handler(Looper.getMainLooper());
		mIsMapRunning = false;
	}

	public MapsHandler(Handler my) {
		Log.d("EXTERNAL HANDLER", "-----++++++CREATING HANDLE++++++------");
		mMainHandler = my;
		mIsMapRunning = false;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case MAP_REQUEST_UPDATE:
			Log.d("EXTERNAL HANDLER", "RECEIVED!!!");
			mMainHandler.sendEmptyMessage(1);
			break;
		case MAP_REQUEST_LOCATION:
			break;
		case MAP_REQUEST_DIRECTIONS:
			break;
		case MAP_REQUEST_START:
			if (!mIsMapRunning) {
				mWorker.start();
				mIsMapRunning = true;
			}
			break;
		case MAP_REQUEST_STOP:
			if (mIsMapRunning) {
				mIsMapRunning = false;
			}
			break;
		default:
			break;
		}
	}

	private Thread mWorker = new Thread() {
		int count = 1;
		@Override
		public void run() {
			mIsMapRunning = true;
			Log.d("RECEIVER HANDLE THREAD------", "STARTED!!!!!");

			while (mIsMapRunning) {
				mMainHandler.sendEmptyMessage(count);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count += 1;
			}
			try {
				this.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("RECEIVER HANDLE THREAD------", "STOPPED!!!!!");
		}
	};
}
