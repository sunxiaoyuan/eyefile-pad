package com.sgeye.exam.android.modules.graph;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.sgeye.exam.android.AppConstants;
import com.sgeye.exam.android.R;
import com.sgeye.exam.android.R2;
import com.simon.margaret.delegates.MargaretDelegate;
import com.simon.margaret.observer.ObserverListener;
import com.simon.margaret.observer.ObserverManager;
import com.simon.margaret.util.callback.CallbackManager;
import com.simon.margaret.util.callback.CallbackType;
import com.simon.margaret.util.callback.IGlobalCallback;
import com.simon.margaret.util.storage.MargaretPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by apple on 2019/11/21.
 */
/*
 * 1.退出策略
 * 	 直到最后一行都成功，视力为2.0
 *   在同一行失败两次，视力为上一行数值
 * 2.连续错误两次视为挑战失败，返回上一行测试
 * 3.连续答对两次视为挑战成功，进入下一行测试
 * */

public class GraphDelegate extends MargaretDelegate implements ObserverListener {

	// 挑战记录
	HashMap<LineType, ChallengeRecord> mChallengeRecord = new HashMap<LineType, ChallengeRecord>();

	@BindView(R2.id.rl_graph_container)
	RelativeLayout graphContainer;

	private ArrayList<LineControl> mLineArr = new ArrayList<>();
	private int mCurrentLine = 9;

	@BindView(R2.id.tv_graph_distance)
	TextView graphDistanceTV;

	@Override
	public Object setLayout() {
		return R.layout.delegate_graph;
	}

	@SuppressLint("InvalidWakeLockTag")
	@Override
	public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
		ObserverManager.getInstance().add(this);

		MargaretPreference.addCustomAppProfile(AppConstants.PREFERENCE_GRAPH_ON_SHOW, "true");

		// 绘制界面
		initView();

		// 控制换行
		CallbackManager.getInstance().addCallback(CallbackType.ON_CHANGE_LINE, args -> {
			Boolean ret = (Boolean) args;

			Handler mainHandler = new Handler(Looper.getMainLooper());
			mainHandler.post(() -> {
				if (!ret) {
					// 上一行显示 or 退出
					// 获取当前行的挑战记录
					ChallengeRecord record = mChallengeRecord.get(LineType.getLineTypeByIndex(mCurrentLine));
					if (record.getFailureCount() >= 2) {
						// 退出测试，返回结果：上一行的视力数值
						if (mCurrentLine > 0) {
							sendCheckResultToPhone(mLineArr.get(mCurrentLine - 1).getType().getLine());
						} else if (mCurrentLine == 0){
							sendCheckResultToPhone(mLineArr.get(mCurrentLine).getType().getLine());
						}
					} else {
						// 显示上一行进行测试
						if (mCurrentLine > 0) {
							mLineArr.get(mCurrentLine).getLineRelativeLayout().setAlpha(0);
							mLineArr.get(mCurrentLine - 1).getLineRelativeLayout().setAlpha(1);
							mCurrentLine -= 1;
							// 通知手机已经换行，更新UI （true - 下一行 / false - 上一行）
							sendMsgToPhone(ret);
						}
					}
				} else {
					// 下一行显示 or 退出
					if (mCurrentLine < mLineArr.size() - 1) {
						mLineArr.get(mCurrentLine).getLineRelativeLayout().setAlpha(0);
						mLineArr.get(mCurrentLine + 1).getLineRelativeLayout().setAlpha(1);
						mCurrentLine += 1;
						// 通知手机已经换行，更新UI （true - 下一行 / false - 上一行）
						sendMsgToPhone(ret);
					} else {
						// 已经在最后一行，退出测试，返回结果：最后一行的数值
						sendCheckResultToPhone(mLineArr.get(mCurrentLine).getType().getLine());
					}
				}

			});
		});
	}

	private void sendCheckResultToPhone(String line) {
		@SuppressWarnings("unchecked") final IGlobalCallback<String> callback = CallbackManager
				.getInstance()
				.getCallback(CallbackType.ON_SEND_BACK_CHECK_RESULT);
		if (callback != null) {
			callback.executeCallback(line);
			// 清空挑战结果，回到0.8
		}
	}

	// 通知手机已经换行
	private void sendMsgToPhone(boolean ret) {
		@SuppressWarnings("unchecked") final IGlobalCallback<Boolean> callback = CallbackManager
				.getInstance()
				.getCallback(CallbackType.ON_SEND_BACK_MSG);
		if (callback != null) {
			callback.executeCallback(ret);
		}
	}

	private void initView() {
		initLineList();
		addLineToContainer();
	}

	private void addLineToContainer() {
		for (LineControl lineControl :
				mLineArr) {
			lineControl.addToParent(graphContainer);
		}
		// 展示第10行 - 0.8
		mLineArr.get(mCurrentLine).getLineRelativeLayout().setAlpha(1);
	}

	private void initLineList() {
		for (int i = 0; i < 14; i++) {
			LineControl lineControl = new LineControl(_mActivity, i);
			mLineArr.add(lineControl);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		_mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);

		float screenDensity = ScreenUtils.getScreenDensity();
		int screenDensityDpi = ScreenUtils.getScreenDensityDpi();
		int screenWidth = ScreenUtils.getScreenWidth();

		RelativeLayout lineRelativeLayout = mLineArr.get(0).getLineRelativeLayout();
		TextView textView = lineRelativeLayout.findViewById(R.id.tv_01_0);
		int width = textView.getWidth();

		initChallengeRecord();
	}

	// 初始化挑战记录表
	private void initChallengeRecord() {
		mChallengeRecord.put(LineType.LINE_01, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_012, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_015, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_02, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_025, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_03, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_04, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_05, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_06, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_08, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_10, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_12, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_15, new ChallengeRecord(0, 0));
		mChallengeRecord.put(LineType.LINE_20, new ChallengeRecord(0, 0));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ObserverManager.getInstance().remove(this);
		MargaretPreference.addCustomAppProfile(AppConstants.PREFERENCE_GRAPH_ON_SHOW, "false");
	}

	@OnClick(R2.id.btn_graph_back)
	public void back() {
		// 返回上一页
		pop();
	}

	// --------------------  手机端命令控制代码 ---------------------- //

	@Override
	public void observerUpData(Object msg) {

		String note = (String) msg;
		String[] split = note.split(":");
		if (split[0].equals("onOpen")) {
			if ("false".equals(split[1])) {
				// 主线程更新UI
				_mActivity.runOnUiThread(() -> {
					// on
					pop();
				});
			}
		} else if ("line".equals(split[0])) {
			// 接收换行控制命令
			handleLineCommand(split[1]);
		} else if ("control".equals(split[0])) {
			// 接收方向控制命令
			handleControlCommand(split[1]);
		} else if ("clear".equals(split[0])) {
			// 接收清除挑战信息命令
			Iterator iter = mChallengeRecord.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				ChallengeRecord record = (ChallengeRecord) entry.getValue();
				record.clear();
			}
		} else if ("size".equals(split[0])) {
			boolean isBigger = "true".equals(split[1]);
			hangleViewSizeChange(isBigger);
		}
	}

	// 接受改变距离命令
	private void hangleViewSizeChange(boolean isBigger) {
		_mActivity.runOnUiThread(() -> {
			graphDistanceTV.setText(isBigger ? "5m" : "2.5m");
			for (LineControl control :
					mLineArr) {
				control.changeViewBigger(isBigger);
			}
		});
	}

	// 接收换行控制命令
	private void handleLineCommand(final String line) {
		// 主线程更新UI
		_mActivity.runOnUiThread(() -> {
			// 上一行隐藏
			mLineArr.get(mCurrentLine).getLineRelativeLayout().setAlpha(0);
			// 当前行展示
			int receivedIndex = LineType.getIndexByLine(line);
			mLineArr.get(receivedIndex).getLineRelativeLayout().setAlpha(1);
			// 同时需要清空当前显示行的挑战结果队列
			ChallengeRecord record = mChallengeRecord.get(LineType.getLineTypeByIndex(receivedIndex));
			record.resetQueue();
			mCurrentLine = receivedIndex;
		});
	}

	// 接收方向控制命令
	private void handleControlCommand(String command) {
		_mActivity.runOnUiThread(() -> {
			// 转换成CommandType
			CommandType commandType = CommandType.getCommandTypeByString(command);
			// 获取当前行
			LineControl lineControl = mLineArr.get(mCurrentLine);
			// 获取当前LineType
			LineType type = lineControl.getType();
			// 获取当前行的挑战结果
			ChallengeRecord record = mChallengeRecord.get(type);
			// 检查指令是否正确
			boolean ret = lineControl.check(commandType);
			if (ret) {
				// correct
				record.addSuccessCount();
			} else {
				// wrong
				record.addFailureCount();
			}
		});
	}

}
