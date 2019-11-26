package com.sgeye.exam.android.modules.graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sgeye.exam.android.R;
import com.simon.margaret.app.Margaret;
import com.simon.margaret.delegates.MargaretDelegate;

import static android.widget.RelativeLayout.TRUE;

/**
 * Created by apple on 2019/11/22.
 */

public class LineFactory {

	private static int COLOR_WHITE =
			Margaret.getApplicationContext().getResources().getColor(R.color.white);
	private static int COLOR_DARK_GREY =
			Margaret.getApplicationContext().getResources().getColor(R.color.numberGray);


	public static RelativeLayout getLineInstance(Context context, int index) {
		int resId = LineType.getResIdByIndex(index);
		return (RelativeLayout) LayoutInflater.from(context).inflate(resId, null);
	}


//	public static RelativeLayout getLineInstance(Context context, String lineType) {
//
//		// 外层容器
//		RelativeLayout container = new RelativeLayout(context);
//		container.setBackgroundColor(COLOR_WHITE);
//		RelativeLayout.LayoutParams containerLayoutParams = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		containerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//		containerLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
//		container.setLayoutParams(containerLayoutParams);
//
//		// 左侧文字
//		TextView leftTextView = new TextView(context);
//		RelativeLayout.LayoutParams leftTextViewLayoutParams = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT
//		);
//		leftTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
//		leftTextViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
//		leftTextViewLayoutParams.setMargins(20, 0, 0, 0);
//		leftTextView.setLayoutParams(leftTextViewLayoutParams);
//		leftTextView.setText(lineType);
//		leftTextView.setTextColor(COLOR_DARK_GREY);
//		leftTextView.setTextSize(20);
//		container.addView(leftTextView);
//
//		// 右侧文字
//		TextView rightTextView = new TextView(context);
//		RelativeLayout.LayoutParams rightTextViewLayoutParams = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT
//		);
//		rightTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
//		rightTextViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
//		rightTextViewLayoutParams.setMargins(0, 0, 20, 0);
//		rightTextView.setLayoutParams(rightTextViewLayoutParams);
//		rightTextView.setText(lineType);
//		rightTextView.setTextColor(COLOR_DARK_GREY);
//		rightTextView.setTextSize(20);
//		container.addView(rightTextView);
//
//		// 绘制图片
//		LinearLayout imageContainer = new LinearLayout(context);
//		imageContainer.setBackgroundColor(COLOR_WHITE);
//		imageContainer.setOrientation(LinearLayout.HORIZONTAL);
//		RelativeLayout.LayoutParams imageContainerLayoutParams = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.WRAP_CONTENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		imageContainerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
//		imageContainerLayoutParams.addRule(RelativeLayout.END_OF, TRUE);
//		imageContainer.setLayoutParams(imageContainerLayoutParams);
//
//		return null;
//	}


}
