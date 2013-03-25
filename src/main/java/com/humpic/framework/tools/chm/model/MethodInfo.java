package com.humpic.framework.tools.chm.model;

public class MethodInfo {

	private String name;
	private String fullName;
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}
}
