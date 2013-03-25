package com.humpic.framework.tools.chm.reader;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.humpic.framework.tools.chm.Config;

public class IndexesReader {

	private Map<String, KeyManager> keyManagerMaps = new HashMap();

	public Map<String, String> getIndexes(File resource) throws IOException {
		Pattern p = Config.style.getIndexRegex();
		Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

		Map<String, String> indexMaps = new HashMap(500);
		while (m.find()) {
			addIndex(indexMaps, m.group(2), m.group(1));
		}
		return indexMaps;
	}

	private void addIndex(Map<String, String> indexMaps, String text, String url) throws FileNotFoundException {
		String key;
		String urlToUse = url;
		urlToUse = StringUtils.remove(urlToUse, "../");
		urlToUse = StringUtils.remove(urlToUse, "./");

		if (text.indexOf('(') < 0 && url.indexOf('#') < 0) {
			key = text + " class";
		} else {
			key = StringUtils.substringBefore(text, "(");

			String textToUse = StringUtils.substringBefore(urlToUse, ".html");
			textToUse = textToUse.replace('/', '.') + "." + text;
			KeyManager keyManager = keyManagerMaps.get(key);
			String urlRedir = "../" + urlToUse;
			if (keyManager == null) {
				keyManager = new KeyManager(key);
				keyManager.add(textToUse, AnchorNameManager.replaceAnchor(urlRedir));
				keyManagerMaps.put(key, keyManager);
			} else { // repeat
				keyManager.add(textToUse, AnchorNameManager.replaceAnchor(urlRedir));
				// redirect
				urlToUse = keyManager.getUrl();
			}
		}
		indexMaps.put(key, AnchorNameManager.replaceAnchor(urlToUse));
	}

	public Collection<KeyManager> getKeyManagers() {
		return keyManagerMaps.values();
	}

}
