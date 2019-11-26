package com.sgeye.exam.android.modules.launcher;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.sgeye.exam.android.AppConstants;
import com.sgeye.exam.android.R;
import com.sgeye.exam.android.R2;
import com.sgeye.exam.android.modules.graph.GraphDelegate;
import com.sgeye.exam.android.util.qrcode.ZXingUtils;
import com.simon.margaret.app.Margaret;
import com.simon.margaret.delegates.MargaretDelegate;
import com.simon.margaret.observer.ObserverListener;
import com.simon.margaret.observer.ObserverManager;
import com.simon.margaret.util.ip.IpUtil;
import com.simon.margaret.util.storage.MargaretPreference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by apple on 2019/11/20.
 */

public class LauncherDelegate extends MargaretDelegate implements ObserverListener {


	@BindView(R2.id.tv_launcher_ip)
	TextView ipTV;

	@BindView(R2.id.iv_launcher_qrcode)
	ImageView qrcodeImageView;

	@Override
	public Object setLayout() {
		return R.layout.delegate_launcher;
	}

	@Override
	public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
		ObserverManager.getInstance().add(this);
	}

	@Override
	public void onDestroy() {
		ObserverManager.getInstance().remove(this);
		super.onDestroy();
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
		// 刷新ip以及二维码
		refreshIP();
	}

	// 刷新ip、二维码
	@OnClick(R2.id.btn_launcher_refresh)
	public void refreshIP() {
		// 更新
		String ipAddress = IpUtil.getIpAddress(Margaret.getApplicationContext());
		ipTV.setText(ipAddress);
		// 生成二维码
		Bitmap bitmap = ZXingUtils.createQRImage(ipAddress, 400, 400);
		qrcodeImageView.setImageBitmap(bitmap);
		ToastUtils.showShort("ip地址已刷新");
	}

	@Override
	public void observerUpData(Object msg) {
		String note = (String) msg;
		String[] split = note.split(":");
		if (split[0].equals("onOpen")) {
			if ("true".equals(split[1])) {
				// 需要判断，如果当前已经有了检查也就不能再创建一个

				GraphDelegate delegate = new GraphDelegate();
				start(delegate);


			}
		}
	}
}
