package com.MicroUserService.Payload;

import org.springframework.http.HttpStatus;


public class ApiExceptionResponse {
	private String msg;
	private boolean sucess;
	private HttpStatus status;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	
}
