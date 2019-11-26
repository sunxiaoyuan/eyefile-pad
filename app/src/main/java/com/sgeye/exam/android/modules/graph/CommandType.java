package com.sgeye.exam.android.modules.graph;

import com.sgeye.exam.android.R;

/**
 * Created by apple on 2019/11/22.
 */

public enum CommandType {
	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	CENTER("center");

	private final String command;

	CommandType( String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public static CommandType getCommandTypeByString(String command) {
		CommandType mtype = UP;
		for (CommandType type :
				CommandType.values()) {
			if (type.getCommand().equalsIgnoreCase(command)) {
				mtype = type;
			}
		}
		return mtype;
	}

}
