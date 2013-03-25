package com.humpic.framework.tools.chm.reader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.humpic.framework.tools.chm.Config;
import com.humpic.framework.tools.chm.model.*;

public class JavaInfoReader {

	public List<PackageInfo> getJavaInfo() throws IOException {
		return getAllPackages(new File(Config.apiLocation, "package-list"));
	}

	private List<PackageInfo> getAllPackages(File resource) throws IOException {
		List<String> lines = FileUtils.readLines(resource, Config.encoding);

		Map<String, PackageInfo> packageMaps = new HashMap();
		List<PackageInfo> packages = new ArrayList(50);
		for (String line : lines) {
			line = line.trim();
			if (line.length() != 0) {
				PackageInfo p = new PackageInfo();
				p.setName(line);
				p.setUrl(line.replace('.', '/') + "/package-summary.html");
				packageMaps.put(p.getName(), p);
				packages.add(p);
			}
		}

		// add all classes to package
		List<ClassInfo> classes = getAllClasses(new File(Config.apiLocation, "allclasses-frame.html"));
		for (ClassInfo classinfo : classes) {
			PackageInfo p = (PackageInfo) packageMaps.get(classinfo.getPackageName());
			p.addClass(classinfo);
		}

		return packages;
	}

	private List<ClassInfo> getAllClasses(File resource) throws IOException {
		Pattern p = Config.style.getJavaClassRegex();
		Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

		List<ClassInfo> classes = new ArrayList(50);
		while (m.find()) {
			ClassInfo info = new ClassInfo();
			info.setFullName(m.group(2).replace('/', '.'));
			info.setUrl(m.group(1));
			info.setShortName(m.group(3));
			classes.add(info);

			System.out.println("class: " + info.getUrl());
			addClassFieldMethod(info);
		}
		return classes;
	}

	private void addClassFieldMethod(ClassInfo classinfo) throws IOException {
		File file = new File(Config.apiLocation, classinfo.getUrl());
		String html = FileUtils.readFileToString(file, Config.encoding);

		Pattern p = Config.style.getJavaFieldRegex();
		Matcher m = p.matcher(html);

		while (m.find()) {
			String name = m.group(2);
			String url = m.group(1);
			boolean isMethod = url.indexOf("(") > 0;
			String fullName = StringUtils.substringAfter(url, "#");

			url = StringUtils.remove(url, "../");
			url = StringUtils.remove(url, "./");
			String anchor = StringUtils.substringAfter(url, "#");
			url = StringUtils.substringBefore(url, "#") + "#" + AnchorNameManager.getNewAnchorName(anchor);

			if (isMethod) {
				MethodInfo info = new MethodInfo();
				info.setName(name);
				info.setFullName(fullName);
				info.setUrl(url);
				classinfo.addMethod(info);
			} else {
				FieldInfo info = new FieldInfo();
				info.setName(name);
				info.setUrl(url);
				classinfo.addField(info);
			}
		}
	}
}
