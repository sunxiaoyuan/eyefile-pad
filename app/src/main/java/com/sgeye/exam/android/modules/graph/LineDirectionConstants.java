package com.sgeye.exam.android.modules.graph;

import java.util.Arrays;
import java.util.List;

/**
 * Created by apple on 2019/11/23.
 */

public class LineDirectionConstants {

	public final static List<CommandType> DIRECTION_01 =
			Arrays.asList(CommandType.LEFT, CommandType.DOWN);
	public final static List<CommandType> DIRECTION_012 =
			Arrays.asList(CommandType.UP, CommandType.RIGHT);
	public final static List<CommandType> DIRECTION_015 =
			Arrays.asList(CommandType.RIGHT, CommandType.DOWN);
	public final static List<CommandType> DIRECTION_02 =
			Arrays.asList(CommandType.LEFT, CommandType.UP, CommandType.RIGHT);
	public final static List<CommandType> DIRECTION_025 =
			Arrays.asList(CommandType.UP, CommandType.LEFT, CommandType.DOWN);
	public final static List<CommandType> DIRECTION_03 =
			Arrays.asList(CommandType.DOWN, CommandType.RIGHT, CommandType.UP, CommandType.LEFT);
	public final static List<CommandType> DIRECTION_04 =
			Arrays.asList(CommandType.LEFT, CommandType.UP, CommandType.RIGHT, CommandType.DOWN);
	public final static List<CommandType> DIRECTION_05 =
			Arrays.asList(CommandType.DOWN, CommandType.LEFT, CommandType.DOWN, CommandType.RIGHT, CommandType.UP);
	public final static List<CommandType> DIRECTION_06 =
			Arrays.asList(CommandType.LEFT, CommandType.UP, CommandType.DOWN, CommandType.LEFT, CommandType.DOWN, CommandType.RIGHT);
	public final static List<CommandType> DIRECTION_08 =
			Arrays.asList(CommandType.DOWN, CommandType.LEFT, CommandType.RIGHT, CommandType.UP, CommandType.RIGHT, CommandType.UP, CommandType.DOWN);
	public final static List<CommandType> DIRECTION_10 =
			Arrays.asList(CommandType.LEFT, CommandType.DOWN, CommandType.RIGHT, CommandType.UP, CommandType.RIGHT, CommandType.DOWN, CommandType.LEFT, CommandType.UP);
	public final static List<CommandType> DIRECTION_12 =
			Arrays.asList(CommandType.RIGHT, CommandType.UP, CommandType.LEFT, CommandType.DOWN, CommandType.UP, CommandType.RIGHT, CommandType.DOWN, CommandType.LEFT);
	public final static List<CommandType> DIRECTION_15 =
			Arrays.asList(CommandType.DOWN, CommandType.LEFT, CommandType.RIGHT, CommandType.UP, CommandType.LEFT, CommandType.DOWN, CommandType.RIGHT, CommandType.UP);
	public final static List<CommandType> DIRECTION_20 =
			Arrays.asList(CommandType.RIGHT, CommandType.DOWN, CommandType.UP, CommandType.LEFT, CommandType.UP, CommandType.RIGHT, CommandType.LEFT, CommandType.DOWN);
}
