package com.sgeye.exam.android.tasks;


import com.sgeye.exam.android.toast.CallNativeToastEvent;
import com.simon.margaret.app.Margaret;
import com.simon.margaret.util.launchstarter.task.MainTask;

/**
 * Created by apple on 2019/11/19.
 */

public class InitAppConfigTask extends MainTask {

	@Override
	public void run() {

		Margaret.init(mContext)
				.withApiHost("")
				.withWebHost("http://test.eyefile.cn")
                .withWebEvent("callNativeToast", new CallNativeToastEvent())
				.withJavascriptInterface("margaret")
				.configure();

	}


}
