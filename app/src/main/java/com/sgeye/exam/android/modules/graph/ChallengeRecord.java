package com.sgeye.exam.android.modules.graph;

import com.simon.margaret.util.callback.CallbackManager;
import com.simon.margaret.util.callback.CallbackType;
import com.simon.margaret.util.callback.IGlobalCallback;

import java.util.ArrayList;

/**
 * Created by apple on 2019/11/24.
 */

public class ChallengeRecord {
	private int successCount;
	private int failureCount;
	private ArrayList<Integer> retQueue;  // 0:成功 1:失败
	private boolean isSuccess;

	public ChallengeRecord(int successCount, int failureCount) {
		this.successCount = successCount;
		this.failureCount = failureCount;
		this.retQueue = new ArrayList<>();
	}

	public int getSuccessCount() {
		return successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public boolean isSuccess() {
		if (this.failureCount >= 2) {
			return false;
		}
		return true;
	}

	public void addSuccessCount() {
		enqueue(0);
	}

	public void addFailureCount() {
		enqueue(1);
	}

	public void enqueue(int ret) {
		// 检查是否触发成功、失败条件
		// 0, 0, 1
		if (retQueue.size() != 0) {
			if (ret == retQueue.get(retQueue.size() - 1)) {
				// 与上一次结果进行比较，满足触发条件
				if (ret == 0) {
					// 成功
					this.successCount += 1;
				} else {
					// 失败
					this.failureCount += 1;
				}
				// 换行 - true:到下一行 false:回上一行
				shouldChangeLine(ret == 0);
				return;
			}
		}
		retQueue.add(ret);
	}

	private void shouldChangeLine(Boolean ret) {
		// 清除本行本次挑战结果序列
		resetQueue();

		@SuppressWarnings("unchecked")
		final IGlobalCallback<Boolean> callback = CallbackManager
				.getInstance()
				.getCallback(CallbackType.ON_CHANGE_LINE);
		if (callback != null) {
			callback.executeCallback(ret);
		}
	}

	// 换行的时候，需要调用这个方法
	public void resetQueue() {
		this.retQueue.clear();
	}

	public void clear() {
		this.successCount = 0;
		this.failureCount = 0;
		this.retQueue.clear();
		this.isSuccess = false;
	}

}
