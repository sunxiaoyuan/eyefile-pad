package com.sgeye.exam.android.server;

import com.blankj.utilcode.util.ToastUtils;
import com.simon.margaret.observer.ObserverManager;
import com.simon.margaret.util.callback.CallbackManager;
import com.simon.margaret.util.callback.CallbackType;
import com.simon.margaret.util.log.MargaretLogger;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by apple on 2019/11/21.
 */

public class ServerSocket extends WebSocketServer {

	private ServerManager _serverManager;
	private OnChangeLineListener lineListener;

	public ServerSocket(ServerManager serverManager, int port) throws UnknownHostException {
		super(new InetSocketAddress((port)));

		_serverManager = serverManager;
	}

	// 客户端建立连接
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		MargaretLogger.i("TAG", "Some one Connected...");
		ToastUtils.showShort("Some one Connected...");
//		ObserverManager.getInstance().notifyObserver("onOpen:true");
	}

	// 客户端断开连接
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		_serverManager.UserLeave(conn);
		ObserverManager.getInstance().notifyObserver("onOpen:false");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		MargaretLogger.i("TAG", "OnMessage:" + message.toString());
//		ToastUtils.showShort(message.toString());

		// 用于测试server状态
		if (message.equals("hello")) {
			_serverManager.SendMessageToUser(conn, "What?");
		}

		// 这里是在子线程
		// 转发消息
		ObserverManager.getInstance().notifyObserver(message);

		// 回传用户切换行
		CallbackManager.getInstance().addCallback(CallbackType.ON_SEND_BACK_MSG, args -> {
			Boolean ret = (Boolean) args;
			if (ret) {
				// 下一行
				_serverManager.SendMessageToUser(conn, "control:true");
			} else {
				// 上一行
				_serverManager.SendMessageToUser(conn, "control:false");
			}
		});

		// 回传用户测试结果
		CallbackManager.getInstance().addCallback(CallbackType.ON_SEND_BACK_CHECK_RESULT, args -> {
			String ret = (String) args;  // "0.1"
			_serverManager.SendMessageToUser(conn, "result:" + ret);
		});

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		MargaretLogger.i("TAG", "Socket Exception:" + ex.toString());
	}

	@Override
	public void onStart() {

	}

}
