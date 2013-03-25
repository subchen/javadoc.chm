package com.humpic.framework.tools.chm;

import java.io.File;
import java.nio.charset.Charset;
import com.humpic.framework.tools.chm.style.JavadocStyle;

public class Config {

	public static File apiLocation = new File(".");
	public static String encoding = Charset.defaultCharset().name();

	public static JavadocStyle style = new JavadocStyle();

}
