package com.humpic.framework.tools.chm.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ClassInfo {

	private String fullName;
	private String shortName;
	private String url;
	private List fields = new ArrayList();
	private List methods = new ArrayList();

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public String getPackageName() {
		return StringUtils.remove(this.fullName, "." + this.shortName);
	}

	public void addField(FieldInfo info) {
		fields.add(info);
	}

	public void addMethod(MethodInfo info) {
		methods.add(info);
	}

	public List getFields() {
		return fields;
	}

	public List getMethods() {
		return methods;
	}

}
