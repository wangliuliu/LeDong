package com.example.baidumapmotiontrack.view;

import com.example.baidumapmotiontrack.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;



public class AlphaView extends ImageView {
	private Drawable alphaDrawable;
	private boolean showBkg; // 鏄惁鏄剧ず鑳屾櫙
	private int choose; // 褰撳墠閫変腑棣栧瓧姣嶇殑浣嶇疆
	private String[] ALPHAS;
	private OnAlphaChangedListener listener;

	public AlphaView(Context context) {
		super(context);
		initAlphaView();
	}

	public AlphaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAlphaView();
	}

	public AlphaView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAlphaView();
	}

	private void initAlphaView() {//初始化A-Z
		showBkg = false;
		choose = -1;
		setImageResource(R.drawable.alpha_normal);//纵向A-Z的图片
		alphaDrawable = getDrawable();//getDrawable()是ImageView类的方法,返回该空间里的图片，返回类型为Drawable.如果为空则返回null
		
		ALPHAS = new String[28];
		ALPHAS[0] = " "; // " "浠ｈ〃鎼滅储
		ALPHAS[27] = "#";
		for (int i = 0; i < 26; i++) {
			ALPHAS[i + 1] = String.valueOf((char) (65 + i));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (showBkg) {
			setImageResource(R.drawable.alpha_pressed);//侧边字母图片底变灰
			alphaDrawable = getDrawable();

			alphaDrawable.setBounds(0, 0, getWidth(), getHeight());//left、top、right、bottom
		} else {
			setImageResource(R.drawable.alpha_normal);//侧边字母图片底为透明
			alphaDrawable = getDrawable();

			alphaDrawable.setBounds(0, 0, getWidth(), getHeight());//left、top、right、bottom
		}

		canvas.save();
		alphaDrawable.draw(canvas);
		canvas.restore();
	}

	@Override
	//View的方法
	public boolean dispatchTouchEvent(MotionEvent event) {
		final float y = event.getY();//获得事件触发点的y坐标
		final int oldChoose = choose;//之前的选择
		final int c = (int) (y / getHeight() * 28);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://按下View，即点击图片
			//Log.d("MainActivity", "clicked");
			showBkg = true;
			if (oldChoose != c && listener != null) {//本次点击点与上次不同
				if (c >= 0 && c < ALPHAS.length) {
					listener.OnAlphaChanged(ALPHAS[c], c);
					choose = c;
				}
			}
			invalidate();//貌似会调用onDraw()方法
			break;

		case MotionEvent.ACTION_MOVE://滑动事件
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < ALPHAS.length) {
					listener.OnAlphaChanged(ALPHAS[c], c);
					choose = c;
				}
			}
			invalidate();
			break;

		case MotionEvent.ACTION_UP://与DOWN对应，表示抬起
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	// 璁剧疆浜嬩欢
	public void setOnAlphaChangedListener(OnAlphaChangedListener listener) {
		this.listener = listener;
	}

	// 浜嬩欢鎺ュ彛
	public interface OnAlphaChangedListener {
		public void OnAlphaChanged(String s, int index);
	}

}
