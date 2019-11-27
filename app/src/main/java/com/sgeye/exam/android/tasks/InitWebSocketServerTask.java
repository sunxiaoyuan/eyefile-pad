package com.sgeye.exam.android.tasks;


import com.sgeye.exam.android.server.ServerManager;
import com.sgeye.exam.android.toast.CallNativeToastEvent;
import com.simon.margaret.app.Margaret;
import com.simon.margaret.util.callback.CallbackManager;
import com.simon.margaret.util.launchstarter.task.MainTask;

/**
 * Created by apple on 2019/11/19.
 */

public class InitWebSocketServerTask extends MainTask {

	@Override
	public void run() {

		// 启动server
		ServerManager serverManager = new ServerManager();
		serverManager.Start(10086);


	}


}
