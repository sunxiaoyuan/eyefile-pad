package com.sgeye.exam.android.modules.graph;

import com.sgeye.exam.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2019/11/22.
 */

public enum LineType {
	LINE_01(0, "0.1", R.layout.delegate_line_01, LineDirectionConstants.DIRECTION_01),
	LINE_012(1, "0.12", R.layout.delegate_line_012, LineDirectionConstants.DIRECTION_012),
	LINE_015(2, "0.15", R.layout.delegate_line_015, LineDirectionConstants.DIRECTION_015),
	LINE_02(3, "0.2", R.layout.delegate_line_02, LineDirectionConstants.DIRECTION_02),
	LINE_025(4, "0.25", R.layout.delegate_line_025, LineDirectionConstants.DIRECTION_025),
	LINE_03(5, "0.3", R.layout.delegate_line_03, LineDirectionConstants.DIRECTION_03),
	LINE_04(6, "0.4", R.layout.delegate_line_04, LineDirectionConstants.DIRECTION_04),
	LINE_05(7, "0.5", R.layout.delegate_line_05, LineDirectionConstants.DIRECTION_05),
	LINE_06(8, "0.6", R.layout.delegate_line_06, LineDirectionConstants.DIRECTION_06),
	LINE_08(9, "0.8", R.layout.delegate_line_08, LineDirectionConstants.DIRECTION_08),
	LINE_10(10, "1.0", R.layout.delegate_line_10, LineDirectionConstants.DIRECTION_10),
	LINE_12(11, "1.2", R.layout.delegate_line_12, LineDirectionConstants.DIRECTION_12),
	LINE_15(12, "1.5", R.layout.delegate_line_15, LineDirectionConstants.DIRECTION_15),
	LINE_20(13, "2.0", R.layout.delegate_line_20, LineDirectionConstants.DIRECTION_20);

	private final int index;
	private final String line;
	private final int ResId;
	private final List<CommandType> commandTypes;

	LineType(int index, String line, int resId, List<CommandType> commandTypes) {
		this.index = index;
		this.line = line;
		ResId = resId;
		this.commandTypes = commandTypes;
	}

	public int getResId() {
		return ResId;
	}

	public int getIndex() {
		return index;
	}

	public String getLine() {
		return line;
	}

	public List<CommandType> getCommandTypes() {
		return commandTypes;
	}

	public static int getIndexByLine(String line) {
		int index = 0;
		for (LineType type :
				LineType.values()) {
			if (type.getLine().equalsIgnoreCase(line)) {
				index = type.getIndex();
			}
		}
		return index;
	}

	public static int getResIdByIndex(int index) {
		int resId = 0;
		for (LineType type :
				LineType.values()) {
			if (type.getIndex() == index) {
				resId = type.getResId();
			}
		}
		if (resId == 0) {
			// 如果找不到对应的，就显示0.1的资源
			return LineType.LINE_01.getResId();
		}
		return resId;
	}

	public static LineType getLineTypeByIndex(int index) {
		LineType lineType = null;
		for (LineType type :
				LineType.values()) {
			if (type.getIndex() == index) {
				lineType = type;
			}
		}
		return lineType;
	}

	public static List<CommandType> getCommandTypesByIndex(int index) {
		List<CommandType> commandTypes = new ArrayList<>();
		for (LineType type :
				LineType.values()) {
			if (type.getIndex() == index) {
				commandTypes = type.getCommandTypes();
			}
		}
		return commandTypes;
	}

}