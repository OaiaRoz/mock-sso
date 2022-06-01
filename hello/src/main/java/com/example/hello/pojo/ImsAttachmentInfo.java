package com.example.hello.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImsAttachmentInfo {
	
	String fileName;
	
	ImsMetadataInfo metaDataInfo;

	/**
	 * 
	 */
	public ImsAttachmentInfo() {}

	/**
	 * @param fileName
	 * @param metaDataInfo
	 */
	public ImsAttachmentInfo(String fileName, ImsMetadataInfo metaDataInfo) {
		this.fileName = fileName;
		this.metaDataInfo = metaDataInfo;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the metaDataInfo
	 */
	public ImsMetadataInfo getMetaDataInfo() {
		return metaDataInfo;
	}

	/**
	 * @param metaDataInfo the metaDataInfo to set
	 */
	public void setMetaDataInfo(ImsMetadataInfo metaDataInfo) {
		this.metaDataInfo = metaDataInfo;
	}
	
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
	
	

}
