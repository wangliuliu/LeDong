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
	private boolean showBkg; // 是否显示背景
	private int choose; // 当前选中首字母的位置
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

	private void initAlphaView() {//��ʼ��A-Z
		showBkg = false;
		choose = -1;
		setImageResource(R.drawable.alpha_normal);//����A-Z��ͼƬ
		alphaDrawable = getDrawable();//getDrawable()��ImageView��ķ���,���ظÿռ����ͼƬ����������ΪDrawable.���Ϊ���򷵻�null
		
		ALPHAS = new String[28];
		ALPHAS[0] = " "; // " "代表搜索
		ALPHAS[27] = "#";
		for (int i = 0; i < 26; i++) {
			ALPHAS[i + 1] = String.valueOf((char) (65 + i));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (showBkg) {
			setImageResource(R.drawable.alpha_pressed);//�����ĸͼƬ�ױ��
			alphaDrawable = getDrawable();

			alphaDrawable.setBounds(0, 0, getWidth(), getHeight());//left��top��right��bottom
		} else {
			setImageResource(R.drawable.alpha_normal);//�����ĸͼƬ��Ϊ͸��
			alphaDrawable = getDrawable();

			alphaDrawable.setBounds(0, 0, getWidth(), getHeight());//left��top��right��bottom
		}

		canvas.save();
		alphaDrawable.draw(canvas);
		canvas.restore();
	}

	@Override
	//View�ķ���
	public boolean dispatchTouchEvent(MotionEvent event) {
		final float y = event.getY();//����¼��������y����
		final int oldChoose = choose;//֮ǰ��ѡ��
		final int c = (int) (y / getHeight() * 28);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://����View�������ͼƬ
			//Log.d("MainActivity", "clicked");
			showBkg = true;
			if (oldChoose != c && listener != null) {//���ε�������ϴβ�ͬ
				if (c >= 0 && c < ALPHAS.length) {
					listener.OnAlphaChanged(ALPHAS[c], c);
					choose = c;
				}
			}
			invalidate();//ò�ƻ����onDraw()����
			break;

		case MotionEvent.ACTION_MOVE://�����¼�
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < ALPHAS.length) {
					listener.OnAlphaChanged(ALPHAS[c], c);
					choose = c;
				}
			}
			invalidate();
			break;

		case MotionEvent.ACTION_UP://��DOWN��Ӧ����ʾ̧��
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	// 设置事件
	public void setOnAlphaChangedListener(OnAlphaChangedListener listener) {
		this.listener = listener;
	}

	// 事件接口
	public interface OnAlphaChangedListener {
		public void OnAlphaChanged(String s, int index);
	}

}
