package com.humpic.framework.tools.chm.style;

import java.util.regex.Pattern;

public class Javadoc7Style extends JavadocStyle {

	@Override
	public Pattern getAnchorNameRegex() {
		return Pattern.compile("<a name=\"([^\"]*)\"[^>]*>");
	}

	@Override
	public Pattern getIndexRegex() {
		return Pattern.compile("<dt><span class=\"strong\"><a href=\"([^\"]*)\"[^>]*>([^<]*)</a></span>");
	}

	@Override
	public Pattern getJavaClassRegex() {
		return Pattern.compile("<a href=\"(([^\"]*)\\.html)\"[^>]*>(?:<i>)?([^<]*)(?:</i>)?</a>");
	}

	@Override
	public Pattern getJavaFieldRegex() {
		return Pattern.compile("<td class=\"col[a-zA-Z]+\"><code><strong><a href=\"([^\"]*)\"[^>]*>([^<]*)</a>");
	}

	@Override
	public Pattern getIndexUrlRegex() {
		return Pattern.compile("<a href=\"([^\"]*)\"[^>]*>");
	}

	@Override
	public Pattern getApiTitleRegex() {
		return Pattern.compile("<title>([^<]*)</title>");
	}
}
