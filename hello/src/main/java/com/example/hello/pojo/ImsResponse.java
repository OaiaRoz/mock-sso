package com.example.hello.pojo;

import java.io.Serializable;

public class ImsResponse implements Serializable {
	
	String status;
	String msg;
	String payload;
	
	
	/**
	 * 
	 */
	public ImsResponse() {}
	
	/**
	 * @param status
	 * @param msg
	 * @param payload
	 */
	public ImsResponse(String status, String msg, String payload) {
		this.status = status;
		this.msg = msg;
		this.payload = payload;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImsResponse [status=").append(status).append(", msg=").append(msg).append(", payload=")
				.append(payload).append("]");
		return builder.toString();
	}
}
