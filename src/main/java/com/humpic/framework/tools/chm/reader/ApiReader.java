package com.humpic.framework.tools.chm.reader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.humpic.framework.tools.chm.Config;

public class ApiReader {

	private JavaInfoReader javaInfoReader = new JavaInfoReader();
	private IndexesReader indexesReader = new IndexesReader();
	private Map<String, Object> context = new HashMap();

	public Map getApiContext() throws Exception {
		// add all files
		Set<String> files = getAllFiles(Config.apiLocation, new HashSet());

		// replace anthor
		Iterator it = files.iterator();
		while (it.hasNext()) {
			String file = (String) it.next();
			if (file.endsWith(".html")) {
				AnchorNameManager.addAnchor(new File(Config.apiLocation, file), Config.encoding);
			}
		}

		// add index url
		context.put("indexUrl", getIndexUrl(new File(Config.apiLocation, "index.html")));

		// add title
		String doctitle = getApiTitle(new File(Config.apiLocation, "index.html"));
		context.put("title", doctitle);

		// add packages
		context.put("packages", javaInfoReader.getJavaInfo());

		// add indexes
		File resource = new File(Config.apiLocation, "index-all.html");
		if (resource.exists()) {
			Map<String, String> indexesMap = indexesReader.getIndexes(resource);
			context.put("sortIndexes", sortIndexed(indexesMap));
		} else {
			File resourceDir = new File(Config.apiLocation, "index-files/");
			File resources[] = resourceDir.listFiles();
			Map<String, String> indexesMap = new HashMap();
			for (File res : resources) {
				if (res.isDirectory()) continue;
				indexesMap.putAll(indexesReader.getIndexes(res));
			}
			context.put("sortIndexes", sortIndexed(indexesMap));
		}
		Collection<KeyManager> keyManagers = indexesReader.getKeyManagers();
		context.put("keyManagers", keyManagers);

		// add redir files
		for (KeyManager keyManager : keyManagers) {
			if (keyManager.isRepeat()) {
				files.add(keyManager.getUrl());
			}
		}
		context.put("files", files);

		return context;
	}

	private List<Map.Entry<String, String>> sortIndexed(Map<String, String> indexesMap) {
		List<Map.Entry<String, String>> sortIndexes = new ArrayList(indexesMap.size());
		sortIndexes.addAll(indexesMap.entrySet());
		Collections.sort(sortIndexes, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
			}
		});
		return sortIndexes;
	}

	private Set<String> getAllFiles(File rootdir, Set<String> files) {
		for (File resource : rootdir.listFiles()) {
			if (resource.isDirectory()) {
				getAllFiles(resource, files);
			} else {
				String filename = resource.getAbsolutePath();
				filename = StringUtils.remove(filename, Config.apiLocation.getAbsolutePath() + "\\");
				filename = filename.replace('\\', '/');
				files.add(filename);
			}
		}
		return files;
	}

	private String getApiTitle(File resource) throws IOException {
		Pattern p = Config.style.getApiTitleRegex();
		Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

		if (m.find()) {
			return m.group(1).trim();
		}
		return "Javadoc API";
	}

	private String getIndexUrl(File resource) throws IOException {
		Pattern p = Config.style.getIndexUrlRegex();
		Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

		if (m.find()) {
			return m.group(1).trim();
		}
		return "overview-summary.html";
	}

}
