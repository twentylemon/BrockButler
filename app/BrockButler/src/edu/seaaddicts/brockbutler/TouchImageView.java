package edu.seaaddicts.brockbutler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class TouchImageView extends ImageView {
	
	private static final double MAGIC_RATIO = 1.34;

	Matrix mMatrixMap;

	float[] values = new float[9];

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF last = new PointF();
	PointF start = new PointF();
	float minScale = 1f;
	float maxScale = 8f;
	float[] m;

	int viewWidth, viewHeight;
	static final int CLICK = 3;
	float saveScale = 1f;
	protected float origWidth, origHeight;
	int oldMeasuredWidth, oldMeasuredHeight;
	
	float fixTransX;
	float fixTransY;

	ScaleGestureDetector mScaleDetector;

	Context mContext;

	public TouchImageView(Context context) {
		super(context);
		sharedConstructing(context);
	}

	public TouchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructing(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		p.setStrokeWidth(10);
		canvas.setMatrix(mMatrixMap);
		float f[] = convertDimensions(1348, 876);
		canvas.drawLine(f[0], f[1], (float)(f[0]+172*1.34), f[1], p);
		float f2[] = convertDimensions(1520, 876);
		canvas.drawLine(f2[0], f2[1], (float)(f2[0]+186*1.34), (float)(f2[1]-1.34*178), p);
	}

	private void sharedConstructing(Context context) {
		super.setClickable(true);
		this.mContext = context;
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		mMatrixMap = new Matrix();
		m = new float[9];
		setImageMatrix(mMatrixMap);
		setScaleType(ScaleType.MATRIX);

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mScaleDetector.onTouchEvent(event);
				PointF curr = new PointF(event.getX(), event.getY());

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					last.set(curr);
					start.set(last);
					mode = DRAG;
					break;

				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						float deltaX = curr.x - last.x;
						float deltaY = curr.y - last.y;
						fixTransX = getFixDragTrans(deltaX, viewWidth,
								origWidth * saveScale);
						fixTransY = getFixDragTrans(deltaY, viewHeight,
								origHeight * saveScale);
						mMatrixMap.postTranslate(fixTransX, fixTransY);
						fixTrans();
						last.set(curr.x, curr.y);
					}
					break;

				case MotionEvent.ACTION_UP:
					mode = NONE;
					int xDiff = (int) Math.abs(curr.x - start.x);
					int yDiff = (int) Math.abs(curr.y - start.y);

					if (xDiff < CLICK && yDiff < CLICK)
						performClick();
					break;

				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				}

				setImageMatrix(mMatrixMap);
				invalidate();
				return true; // indicate event was handled
			}

		});
	}

	public void setMaxZoom(float x) {
		maxScale = x;
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = detector.getScaleFactor();
			float origScale = saveScale;
			saveScale *= mScaleFactor;
			if (saveScale > maxScale) {
				saveScale = maxScale;
				mScaleFactor = maxScale / origScale;
			} else if (saveScale < minScale) {
				saveScale = minScale;
				mScaleFactor = minScale / origScale;
			}

			if (origWidth * saveScale <= viewWidth
					|| origHeight * saveScale <= viewHeight)
				mMatrixMap.postScale(mScaleFactor, mScaleFactor, viewWidth / 2,
						viewHeight / 2);
			else
				mMatrixMap.postScale(mScaleFactor, mScaleFactor,
						detector.getFocusX(), detector.getFocusY());
			fixTrans();
			return true;
		}
	}

	void fixTrans() {
		mMatrixMap.getValues(m);
		float transX = m[Matrix.MTRANS_X];
		float transY = m[Matrix.MTRANS_Y];

		fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
		fixTransY = getFixTrans(transY, viewHeight, origHeight
				* saveScale);

		if (fixTransX != 0 || fixTransY != 0)
			mMatrixMap.postTranslate(fixTransX, fixTransY);
	}

	float getFixTrans(float trans, float viewSize, float contentSize) {
		float minTrans, maxTrans;

		if (contentSize <= viewSize) {
			minTrans = 0;
			maxTrans = viewSize - contentSize;
		} else {
			minTrans = viewSize - contentSize;
			maxTrans = 0;
		}

		if (trans < minTrans)
			return -trans + minTrans;
		if (trans > maxTrans)
			return -trans + maxTrans;
		return 0;
	}

	float getFixDragTrans(float delta, float viewSize, float contentSize) {
		if (contentSize <= viewSize) {
			return 0;
		}
		return delta;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);

		// Rescales image on rotation
		if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
				|| viewWidth == 0 || viewHeight == 0)
			return;
		oldMeasuredHeight = viewHeight;
		oldMeasuredWidth = viewWidth;

		if (saveScale == 1) {
			// Fit to screen.
			float scale;

			Drawable drawable = getDrawable();
			if (drawable == null || drawable.getIntrinsicWidth() == 0
					|| drawable.getIntrinsicHeight() == 0)
				return;
			int bmWidth = drawable.getIntrinsicWidth();
			int bmHeight = drawable.getIntrinsicHeight();

			Log.d("bmSize", "bmWidth: " + bmWidth + " bmHeight : " + bmHeight);

			float scaleX = (float) viewWidth / (float) bmWidth;
			float scaleY = (float) viewHeight / (float) bmHeight;
			scale = Math.min(scaleX, scaleY);
			mMatrixMap.setScale(scale, scale);

			// Center the image
			float redundantYSpace = (float) viewHeight
					- (scale * (float) bmHeight);
			float redundantXSpace = (float) viewWidth
					- (scale * (float) bmWidth);
			redundantYSpace /= (float) 2;
			redundantXSpace /= (float) 2;

			mMatrixMap.postTranslate(redundantXSpace, redundantYSpace);

			origWidth = viewWidth - 2 * redundantXSpace;
			origHeight = viewHeight - 2 * redundantYSpace;
			setImageMatrix(mMatrixMap);
		}
		fixTrans();
	}
	
	float [] convertDimensions(float x, float y) {
		float f[] = new float[2];
		f[0] = (float) MAGIC_RATIO*x;
		f[1] = (float) MAGIC_RATIO*y;
		return f;
	}
}