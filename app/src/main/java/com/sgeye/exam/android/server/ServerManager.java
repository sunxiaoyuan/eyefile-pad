package com.sgeye.exam.android.server;

import android.util.Log;

import com.simon.margaret.util.log.MargaretLogger;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by apple on 2019/11/21.
 */

public class ServerManager {

	private ServerSocket serverSocket = null;
	private Map<WebSocket, String> userMap = new HashMap<WebSocket, String>();

	public ServerManager() {

	}

	public void UserLogin(String userName, WebSocket socket) {
		if (userName != null || socket != null) {
			userMap.put(socket, userName);
			Log.i("TAG", "LOGIN:" + userName);
			SendMessageToAll(userName + "...Login...");
		}
	}

	public void UserLeave(WebSocket socket) {
		if (userMap.containsKey(socket)) {
			String userName = userMap.get(socket);
			Log.i("TAG", "Leave:" + userName);
			userMap.remove(socket);
			SendMessageToAll(userName + "...Leave...");
		}
	}

	public void SendMessageToUser(WebSocket socket, String message) {
		if (socket != null) {
			socket.send(message);
		}
	}


	public void SendMessageToUser(String userName, String message) {
		Set<WebSocket> ketSet = userMap.keySet();
		for (WebSocket socket : ketSet) {
			String name = userMap.get(socket);
			if (name != null) {
				if (name.equals(userName)) {
					socket.send(message);
					break;
				}
			}
		}
	}

	public void SendMessageToAll(String message) {
		Set<WebSocket> ketSet = userMap.keySet();
		for (WebSocket socket : ketSet) {
			String name = userMap.get(socket);
			if (name != null) {
				socket.send(message);
			}
		}
	}

	public boolean Start(int port) {

		if (port < 0) {
			MargaretLogger.i("TAG", "Port error...");
			return false;
		}

//		InetSocketAddress socketAddress = null;
//		// 检测当前端口是否有服务占用
//		boolean isInUse = true;
//		if (isInUse) {
//			// 复用端口
//
//		} else {
//			// 新建
//			socketAddress = new InetSocketAddress(port);
//		}

		MargaretLogger.i("TAG", "Start ServerSocket...");

		WebSocketImpl.DEBUG = false;
		try {
			serverSocket = new ServerSocket(this, port);
			serverSocket.start();
			MargaretLogger.i("TAG", "Start ServerSocket Success...");
			return true;
		} catch (Exception e) {
			MargaretLogger.i("TAG", "Start Failed...");
			e.printStackTrace();
			return false;
		}
	}

	public boolean Stop() {
		try {
			serverSocket.stop();
			MargaretLogger.i("TAG", "Stop ServerSocket Success...");
			return true;
		} catch (Exception e) {
			MargaretLogger.i("TAG", "Stop ServerSocket Failed...");
			e.printStackTrace();
			return false;
		}
	}

}
