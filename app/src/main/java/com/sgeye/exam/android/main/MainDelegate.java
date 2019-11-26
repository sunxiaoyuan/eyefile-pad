package com.sgeye.exam.android.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sgeye.exam.android.R;
import com.simon.margaret.delegates.MargaretDelegate;

import butterknife.OnClick;

/**
 * Created by apple on 2019/8/22.
 */

public class MainDelegate extends MargaretDelegate {


	@OnClick(R.id.main_test_btn)
	void testClick(View view) {
		// 处理点击事件

	}

	@Override
	public Object setLayout() {
		return R.layout.delagate_main;
	}

	@Override
	public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

	}

}
