package com.example.hello.pojo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImsMetadata {

	@JsonProperty(value = "IMDS_FILE_SIZE")
	String IMDS_FILE_SIZE;

	@JsonProperty(value = "IMDS_DOC_TYPE")
	String IMDS_DOC_TYPE;

	@JsonProperty(value = "IMDS_USERNAME")
	String IMDS_USERNAME;

	@JsonProperty(value = "IMDS_APP")
	String IMDS_APP;

	public ImsMetadata() {
	}

	/**
	 * @param iMDS_FILE_SIZE
	 * @param iMDS_DOC_TYPE
	 * @param iMDS_USERNAME
	 * @param iMDS_APP
	 */
	public ImsMetadata(String IMDS_FILE_SIZE, String IMDS_DOC_TYPE, String IMDS_USERNAME, String IMDS_APP) {
		this.IMDS_FILE_SIZE = IMDS_FILE_SIZE;
		this.IMDS_DOC_TYPE = IMDS_DOC_TYPE;
		this.IMDS_USERNAME = IMDS_USERNAME;
		this.IMDS_APP = IMDS_APP;
	}

	/**
	 * @return the IMDS_FILE_SIZE
	 */
	@JsonGetter(value = "IMDS_FILE_SIZE")
	public String getIMDS_FILE_SIZE() {
		return this.IMDS_FILE_SIZE;
	}

	/**
	 * @param iMDS_FILE_SIZE the iMDS_FILE_SIZE to set
	 */
	@JsonSetter(value = "IMDS_FILE_SIZE")
	public void setIMDS_FILE_SIZE(String IMDS_FILE_SIZE) {
		this.IMDS_FILE_SIZE = IMDS_FILE_SIZE;
	}

	/**
	 * @return the iMDS_DOC_TYPE
	 */
	@JsonGetter(value = "IMDS_DOC_TYPE")
	public String getIMDS_DOC_TYPE() {
		return this.IMDS_DOC_TYPE;
	}

	/**
	 * @param iMDS_DOC_TYPE the iMDS_DOC_TYPE to set
	 */
	@JsonSetter(value = "IMDS_DOC_TYPE")
	public void setIMDS_DOC_TYPE(String IMDS_DOC_TYPE) {
		this.IMDS_DOC_TYPE = IMDS_DOC_TYPE;
	}

	/**
	 * @return the iMDS_USERNAME
	 */
	@JsonGetter(value = "IMDS_USERNAME")
	public String getIMDS_USERNAME() {
		return this.IMDS_USERNAME;
	}

	/**
	 * @param iMDS_USERNAME the iMDS_USERNAME to set
	 */
	@JsonSetter(value = "IMDS_USERNAME")
	public void setIMDS_USERNAME(String IMDS_USERNAME) {
		this.IMDS_USERNAME = IMDS_USERNAME;
	}

	/**
	 * @return the iMDS_APP
	 */
	@JsonGetter(value = "IMDS_APP")
	public String getIMDS_APP() {
		return this.IMDS_APP;
	}

	/**
	 * @param iMDS_APP the iMDS_APP to set
	 */
	@JsonSetter(value = "IMDS_APP")
	public void setIMDS_APP(String IMDS_APP) {
		this.IMDS_APP = IMDS_APP;
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImsMetadata [IMDS_FILE_SIZE=").append(IMDS_FILE_SIZE).append(", IMDS_DOC_TYPE=")
				.append(IMDS_DOC_TYPE).append(", IMDS_USERNAME=").append(IMDS_USERNAME).append(", IMDS_APP=")
				.append(IMDS_APP).append("]");
		return builder.toString();
	}

}
