package edu.seaaddicts.brockbutler.maps;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MapsHandler extends Handler {

	public static final int MAPS_REQUEST_UPDATE = 0x001;
	public static final int MAPS_REQUEST_LOCATION_EXISTS = 0x002;
	public static final int MAPS_REQUEST_DIRECTION = 0x003;

	public static final int MAPS_SEND_POSITION = 0x004;
	public static final int MAPS_SEND_DIRECTIONS = 0x005;

	public static final int MAPS_ERROR_NO_LOCATION = 0x006;
	public static final int MAPS_ERROR_NO_WIFI = 0x007;

	public static final int THREAD_REQUEST_START = 0x008;
	public static final int THREAD_REQUEST_STOP = 0x009;
	public static final int THREAD_REQUEST_PAUSE = 0x010;
	public static final int THREAD_REQUEST_RESUME = 0x011;

	public static final int THREAD_UPDATE_POSITION = 0x012;

	private static final String tag = "MapsHandler";
	private Handler mMainHandler;
	private Thread mMapsThread;

	private Object mPauseLock;
	private boolean mIsPaused;
	private boolean mIsFinished;

	public MapsHandler(Looper main) {
		super(main);
		Log.d(tag, "-----++++++ Creating Handler from Looper ++++++------");
		mMainHandler = new Handler(main);
		mIsPaused = true;
		init();
	}

	public MapsHandler(Handler main) {
		Log.d(tag, "-----++++++ Creating Handler. ++++++------");
		mMainHandler = main;
		mIsPaused = true;
		init();
	}

	private void init() {
		mPauseLock = new Object();

		// Set up Maps Thread
		mMapsThread = new Thread() {

			int count = 20;

			@Override
			public void run() {
				Log.d(tag, "Thread started.");

				while (!mIsFinished) {
					// do your stuff here

					mMainHandler.sendEmptyMessage(count);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count += 1;
					synchronized (mPauseLock) {
						while (mIsPaused) {
							try {
								mPauseLock.wait();
								Log.d(tag,
										"-----++++++ Thread paused. ++++++------");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {

		case MAPS_REQUEST_UPDATE:
			Log.d(tag, "-----+++++ Sending update to MapsActivity. +++++-----");
			mMainHandler.sendEmptyMessage(MAPS_SEND_POSITION);
			break;

		case MAPS_REQUEST_LOCATION_EXISTS:
			Log.d(tag, "-----+++++ Checking for location. +++++-----");

			// Runs Thomas' code to check for existence.

			// if (mDoesExist)
			// send information
			// else
			// mMainHandler.sendEmptyMessage(MAPS_ERROR_NO_LOCATION);
			break;

		case MAPS_REQUEST_DIRECTION:
			Log.d(tag, "-----+++++ Getting directions. +++++-----");
			mMainHandler.sendEmptyMessage(MAPS_SEND_DIRECTIONS);
			break;
		case THREAD_REQUEST_START:
			if (!mMapsThread.isAlive()) {
				Log.d(tag, "-----+++++ Starting thread. +++++-----");
				mMapsThread.start();
				mIsPaused = false;
			}
			break;
		case THREAD_REQUEST_PAUSE:
			if (!mIsPaused) {
				synchronized (mPauseLock) {
					Log.d(tag, "-----+++++ Pausing thread. +++++-----");
					mIsPaused = true;
				}
			}
			break;
		case THREAD_REQUEST_RESUME:
			if (mIsPaused) {
				synchronized (mPauseLock) {
					Log.d(tag, "-----+++++ Resuming thread. +++++-----");
					mIsPaused = false;
					mPauseLock.notifyAll();
				}
			}
			break;
		default:
			break;
		}
	}
}
