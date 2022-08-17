package com.hutu.hutunote.common;


import lombok.Data;

import java.io.Serializable;


@SuppressWarnings("serial")
@Data
public class Result<T> implements Serializable {
	/**
	 * 状态码
	 */
	private String code = CodeConstant.SUCCESS;
	/**
	 * 提示信息
	 */
	private String message = "success";

	private T data;

	private Result() {}

	private Result(T data) {
		this.data = data;
	}

	private Result(String code, String msg) {
		this.code = code;
		this.message = msg;
	}


	public static <T> Result<T> build() {
		return new Result<T>();
	}

	/**
	 *  成功时候的调用
	 * */
	public static <T> Result<T> success(T data){
		return new Result<T>(data);
	}

	public Result<T> code(String code) {
		this.code = code;
		return this;
	}
	public Result<T> message(String message) {
		this.message = message;
		return this;
	}
	public Result<T> data(T data) {
		this.data = data;
		return this;
	}
	public Result<T> fail() {
		this.code = CodeConstant.FAILURE;
		this.message = "fail";
		return this;
	}
	public static <T> Result<T> fail(String message) {
		return new Result(CodeConstant.FAILURE, message);
	}

}

