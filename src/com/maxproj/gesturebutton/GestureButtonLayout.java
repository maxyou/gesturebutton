package com.maxproj.gesturebutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class GestureButtonLayout extends FrameLayout {
	Context mContext;

	public interface OnOverLayerTouchDownListener {
		void onOverLayerTouchDown();
	}

	public interface OnOverLayerTouchUpListener {
		void onOverLayerTouchUp();
	}

	public interface OnOverLayerTouchMoveListener {
		void onOverLayerTouchMove(float x, float y);
	}

	OnOverLayerTouchDownListener mTouchDownListener;
	OnOverLayerTouchUpListener mTouchUpListener;
	OnOverLayerTouchMoveListener mTouchMoveListener;

	public void setOverLayerTouchDownListener(OnOverLayerTouchDownListener l) {
		mTouchDownListener = l;
	}

	public void setOverLayerTouchUpListener(OnOverLayerTouchUpListener l) {
		mTouchUpListener = l;
	}

	public void setOverLayerTouchMoveListener(OnOverLayerTouchMoveListener l) {
		mTouchMoveListener = l;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			processEvent(event);// 私下处理event

			/**
			 * 可以在这里判断MOVE的距离
			 * 如果超出阀值，可以停止传播，使得下层的View得不到event
			 * 可以考虑添加回调函数，把这个逻辑放在GestureButtonShow.java里面
			 */
			super.dispatchTouchEvent(event); // 继续传播event

			return true; // 本层也继续接收event
		}
		return super.dispatchTouchEvent(event);
	}

	private boolean processEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			touchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			touchUp(event);
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	private void touchDown(MotionEvent event) {
		MyLog.d(MyLog.DEBUG, "touchDown()!");

		mTouchDownListener.onOverLayerTouchDown();
	}

	private void touchMove(MotionEvent event) {
		MyLog.d(MyLog.DEBUG, "touchMove()");

		mTouchMoveListener.onOverLayerTouchMove(event.getX(), event.getY());
	}

	private void touchUp(MotionEvent event) {
		MyLog.d(MyLog.DEBUG, "touchUp()!");

		mTouchUpListener.onOverLayerTouchUp();
	}

	public GestureButtonLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		MyLog.d(MyLog.DEBUG, "GestureButtonLayout(3)");
		mContext = context;

	}

	public GestureButtonLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		MyLog.d(MyLog.DEBUG, "GestureButtonLayout(2)");
		mContext = context;

	}

	public GestureButtonLayout(Context context) {
		super(context);

		MyLog.d(MyLog.DEBUG, "GestureButtonLayout(1)");
		mContext = context;

	}
}
