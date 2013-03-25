package com.humpic.framework.tools.chm.model;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {

	private String name;
	private String url;
	private List classes = new ArrayList();

	public List getClasses() {
		return classes;
	}

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

	public void addClass(ClassInfo classinfo) {
		classes.add(classinfo);
	}
}
