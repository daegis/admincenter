package com.hnair.wallet.admincenter.model;

import java.io.Serializable;


/**
 * ProcessorResponseId Entity.
 */
public class ProcessorResponseId implements Serializable{
	
	//列信息
	private Long id;
	
	private Short requestType;
	
	private String responseId;
	
	private Long userId;
	
	private java.util.Date createTime;
	
	private java.util.Date lastModifiedTime;
	
	private Short status;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setRequestType(Short value) {
		this.requestType = value;
	}
	
	public Short getRequestType() {
		return this.requestType;
	}
		
		
	public void setResponseId(String value) {
		this.responseId = value;
	}
	
	public String getResponseId() {
		return this.responseId;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setLastModifiedTime(java.util.Date value) {
		this.lastModifiedTime = value;
	}
	
	public java.util.Date getLastModifiedTime() {
		return this.lastModifiedTime;
	}
		
		
	public void setStatus(Short value) {
		this.status = value;
	}
	
	public Short getStatus() {
		return this.status;
	}
		
}

