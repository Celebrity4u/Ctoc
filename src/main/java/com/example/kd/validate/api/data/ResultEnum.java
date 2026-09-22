/**
 * Copyright 2014-现在 国防科技大学
 */
package com.example.kd.validate.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
	// 成功
	SUCCESS(0, "成功"),
	//异常，但是可以放行 201-299
	NO_ROOM(201, "退出时找不到用户房间"),
	// 强提醒 600- 699
	ERROR_Application(602, "想定棋子或载荷id不唯一"),
	ERROR_ScenerioID(603, "想定Id重复"),
	ERROR_PLAYBACK(604, "在线推演中不能查看回放"),
	ERROR_INPORT(605, "任务已导入过"),
	ERROR_TOKEN(701, "token无效或已失效"),
	ERROR_LOCK(702, "账号已被管理员锁定"),
	ERROR_CONFLICK(802, "指令冲突"),
	CREATE_ROOM(803, "创建房间房间错误"),
	PLANNING(805, "筹划中"),
	// 错误，包括调试的错误提示，弱提醒
	ERROR(-1, "失败");

	private Integer code;

	private String desc;

}
