package com.jellysoft.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class PhoneBaseController {

	public enum BackType {
		SUCCESS("成功"), 
		LOGIN_EMPTY("账号或者密码未输入请重试。"),
		LOGIN_ERROR("账号或者密码错误，请重试。"),
		LOGIN_LOCKED( "您的账号已被锁定，请联系管理员申诉解锁。" )

		;
		private String message;

		BackType() {
			this("");
		}

		BackType(String value) {
			this.message = value;
		}

		public String getMessage() {
			return message;
		}

		public int index() {
			return this.ordinal();
		}

	}

	protected String backData(BackType backType, Map<String, Object> data) {

		JSONObject object = new JSONObject();
		object.put("code", backType.index());
		object.put("msg", backType.getMessage());
		if (data != null)
			object.put("data", data);
		return object.toJSONString();

	}

	protected String backData(BackType backType) {

		JSONObject object = new JSONObject();
		object.put("code", backType.index());
		object.put("msg", backType.getMessage());
		return object.toJSONString();

	}

}
