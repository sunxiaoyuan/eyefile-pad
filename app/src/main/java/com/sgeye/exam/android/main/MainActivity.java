package com.sgeye.exam.android.main;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.sgeye.exam.android.R;
import com.sgeye.exam.android.modules.graph.GraphDelegate;
import com.sgeye.exam.android.modules.launcher.LauncherDelegate;
import com.simon.margaret.activities.ProxyActivity;
import com.simon.margaret.app.Margaret;
import com.simon.margaret.delegates.MargaretDelegate;

public class MainActivity extends ProxyActivity {

	private static MargaretDelegate FIRST_DELEGATE = null;

	//定义一个变量，来标识是否退出
	private static boolean isExit = false;

	@Override
	public MargaretDelegate setRootDelegate() {
		return new LauncherDelegate();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// 程序退出，释放端口

		super.onStop();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Window级别开启硬件加速
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		// 设置方向
		if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}

		// 设置透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			StatusBarCompat.setStatusBarColor(
					this, Margaret.getApplicationContext().getColor(R.color.white));
		}

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


		// 动态申请权限
//		requestPermissions();
	}

//	private void requestPermissions() {
//		Dexter.withActivity(this)
//				.withPermissions(
//						Manifest.permission.CAMERA,
//						Manifest.permission.READ_EXTERNAL_STORAGE
//				).withListener(new MultiplePermissionsListener() {
//			@Override
//			public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
//
//			@Override
//			public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
//		}).check();
//	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			//利用handler延迟发送更改状态信息
			handler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}


}
