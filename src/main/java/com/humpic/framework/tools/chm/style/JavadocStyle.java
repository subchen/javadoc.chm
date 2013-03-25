package com.humpic.framework.tools.chm.style;

import java.util.regex.Pattern;

public class JavadocStyle {

	public Pattern getAnchorNameRegex() {
		return Pattern.compile("<A NAME=\"([^\"]*)\"[^>]*>");
	}

	public Pattern getIndexRegex() {
		return Pattern.compile("<DT><A HREF=\"([^\"]*)\"[^>]*><B>([^<]*)</B></A>");
	}

	public Pattern getJavaClassRegex() {
		return Pattern.compile("<A HREF=\"(([^\"]*)\\.html)\"[^>]*>(?:<I>)?([^<]*)(?:</I>)?</A>");
	}

	public Pattern getJavaFieldRegex() {
		return Pattern.compile("<TD><CODE><B><A HREF=\"([^\"]*)\"[^>]*>([^<]*)</A>");
	}

	public Pattern getIndexUrlRegex() {
		return Pattern.compile("<A HREF=\"([^\"]*)\"[^>]*>");
	}

	public Pattern getApiTitleRegex() {
		return Pattern.compile("<TITLE>([^<]*)</TITLE>");
	}

}
