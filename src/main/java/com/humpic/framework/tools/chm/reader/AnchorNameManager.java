package com.humpic.framework.tools.chm.reader;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.humpic.framework.tools.chm.Config;

public class AnchorNameManager {

	public static void addAnchor(File file, String encoding) throws IOException {
		String html = FileUtils.readFileToString(file, encoding);
		html = html.replace('$', '\uFFE5');

		Pattern p = Config.style.getAnchorNameRegex();
		Matcher m = p.matcher(html);

		int findCount = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			findCount++;
			String anchor = m.group(1);
			// String oldAnchor = m.group();
			String oldAnchor = "<A HH=\"1\" NAME=\"" + anchor + "\">";
			String newAnchor = "<A HH=\"1\" NAME=\"" + getNewAnchorName(anchor) + "\"></A>";
			m.appendReplacement(sb, newAnchor + oldAnchor);
		}
		m.appendTail(sb);

		System.out.println("addAnchor(" + findCount + ") : " + file);

		if (findCount > 0) {
			html = sb.toString().replace('\uFFE5', '$');
			FileUtils.writeStringToFile(file, html, encoding);
		}
		html = null;
	}

	public static String replaceAnchor(String url) {
		String anchor = StringUtils.substringAfter(url, "#");
		if ("".equals(anchor)) return url;
		return StringUtils.substringBefore(url, "#") + "#" + getNewAnchorName(anchor);
	}

	public static String getNewAnchorName(String anchor) {
		return "HH_" + Math.abs(anchor.hashCode());
	}
}
