package com.sgeye.exam.android.modules.graph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sgeye.exam.android.R;

import java.util.List;
import java.util.Random;

import static android.widget.RelativeLayout.TRUE;

/**
 * Created by apple on 2019/11/23.
 */

public class LineControl {

	private RelativeLayout mLineRelativeLayout;
	private ImageView mIndicator;
	private Context mContext;
	private List<CommandType> mDirections;
	private int mCurIndex = 0;
	private int mOffset = 0;
	private int mContainerWidthLarge = 0;
	private int mContainerHeightLarge = 0;
	private RelativeLayout mImageContainer;


	public LineType getType() {
		return mType;
	}

	private LineType mType;

	public RelativeLayout getLineRelativeLayout() {
		return mLineRelativeLayout;
	}

	public LineControl(Context context, int index) {
		mContext = context;

		mType = LineType.getLineTypeByIndex(index);

		// 根据布局文件加载Line
		int resId = LineType.getResIdByIndex(index);
		mLineRelativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(resId, null);

		//组件绘制前监听
		ViewTreeObserver vto = mLineRelativeLayout.getViewTreeObserver();
		vto.addOnPreDrawListener(() -> {
			mContainerHeightLarge = mLineRelativeLayout.getMeasuredHeight();
			mContainerWidthLarge = mLineRelativeLayout.getMeasuredWidth();
			return true;
		});

		// 获取方向数组（ = ImageView的个数）
		mDirections = LineType.getCommandTypesByIndex(index);
		// 获取图片容器 RelativeLayout
		mImageContainer = (RelativeLayout) mLineRelativeLayout.getChildAt(2);

		ViewTreeObserver vtoImageContainer = mImageContainer.getViewTreeObserver();
		vtoImageContainer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				int imageContainerWidth = mImageContainer.getMeasuredWidth();
				mOffset = imageContainerWidth / mDirections.size();

				// 随机移动
				nextFirst();

				mImageContainer.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});

		// 获取指示器ImageView
		mIndicator = (ImageView) mImageContainer.getChildAt(1);
		// 自身隐藏
		mLineRelativeLayout.setAlpha(0);

	}

	public void addToParent(ViewGroup view) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		view.addView(mLineRelativeLayout, params);
	}

	public void changeViewBigger(boolean isBigger) {
		ViewGroup imageContainer = (ViewGroup) mImageContainer.getChildAt(0);
		for (int i = 0; i < imageContainer.getChildCount(); i++) {
			View imageView = imageContainer.getChildAt(i);
			ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
			if (isBigger) {
				layoutParams.height = (int) (layoutParams.height * 1.25);
			} else {
				layoutParams.height = (int) (layoutParams.height * 0.8);
			}
			imageView.setLayoutParams(layoutParams);
		}
	}

	// 检查指令是否正确
	public boolean check(CommandType type) {
		boolean isCorrect = true;
		// 判断是否正确
		isCorrect = type == mDirections.get(mCurIndex);
		// 无论是否正确，都需要移动指示器
		next();
		return isCorrect;
	}

	// 移动指示器
	private void next() {
		int min = 0;
		int max = mDirections.size();
		int newPosition = new Random().nextInt(max - min) + min;
		int offsetX = (newPosition - mCurIndex) * mOffset;

		// 移动指示器位置
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIndicator.getLayoutParams();
		layoutParams.leftMargin = mIndicator.getLeft() + offsetX;
		mIndicator.setLayoutParams(layoutParams);

		// 更新位置索引
		mCurIndex = newPosition;
	}

	// 移动指示器
	private void nextFirst() {
		int min = 0;
		int max = mDirections.size();
		int newPosition = new Random().nextInt(max - min) + min;
		int offsetX = (newPosition - mCurIndex) * mOffset;

		// 移动指示器位置
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIndicator.getLayoutParams();
		layoutParams.leftMargin =  offsetX + mOffset/2;
		layoutParams.topMargin = 20;
		mIndicator.setLayoutParams(layoutParams);
		mIndicator.setScaleType(ImageView.ScaleType.MATRIX);

		// 更新位置索引
		mCurIndex = newPosition;
	}


	private int getNewPosition(int min, int max, int old) {
		int newPosition = new Random().nextInt(max - min) + min;
		if (newPosition == old) {
			return getNewPosition(min, max, old);
		} else {
			return newPosition;
		}
	}


}
