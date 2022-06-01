package com.example.hello.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImsMetadataInfo {

	String className;

	ImsMetadata metaData;

	
	
	/**
	 * 
	 */
	public ImsMetadataInfo() {}
	
	
	

	/**
	 * @param className
	 * @param metaData
	 */
	public ImsMetadataInfo(String className, ImsMetadata metaData) {
		this.className = className;
		this.metaData = metaData;
	}




	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the metaData
	 */
	public ImsMetadata getMetaData() {
		return metaData;
	}

	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(ImsMetadata metaData) {
		this.metaData = metaData;
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
