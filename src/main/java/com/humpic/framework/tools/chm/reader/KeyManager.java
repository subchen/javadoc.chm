package com.humpic.framework.tools.chm.reader;

import java.util.*;
import org.apache.commons.lang.StringUtils;

public class KeyManager {
	private String key;
	private List<String> pairs = new ArrayList();

	public KeyManager(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void add(String name, String url) {
		String text;
		if (name.indexOf('(') >= 0) {
			text = StringUtils.replace(name, key + "(", "<b>" + key + "</b>(");
		} else {
			text = StringUtils.replace(name, "." + key, ".<b>" + key + "</b>");
		}
		text = "<a href='" + url + "'>" + text + "</a>";
		pairs.add(text);
	}

	public boolean isRepeat() {
		return pairs.size() > 1;
	}

	public List<String> getPairs() {
		return pairs;
	}

	public String getUrl() {
		return "redirs/" + key + "_" + Math.abs(key.hashCode()) + ".html";
	}

	public void sort() {
		Collections.sort(pairs);
	}
}
