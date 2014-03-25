/**
 * javadoc.chm
 * http://subchen.github.io/javadoc.chm/
 * 
 * Copyright 2010-2014 Guoqiang Chen. All rights reserved.
 * Email: subchen@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jerbrick.tools.chm.reader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jerbrick.tools.chm.Config;
import jerbrick.tools.chm.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class JavaInfoReader {

    public List<PackageInfo> getJavaInfo() throws IOException {
        return getAllPackages(new File(Config.apiLocation, "package-list"));
    }

    private List<PackageInfo> getAllPackages(File resource) throws IOException {
        List<String> lines = FileUtils.readLines(resource, Config.encoding);

        Map<String, PackageInfo> packageMaps = new HashMap<String, PackageInfo>(32);
        List<PackageInfo> packages = new ArrayList<PackageInfo>(50);
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
            PackageInfo p = packageMaps.get(classinfo.getPackageName());
            p.addClass(classinfo);
        }

        return packages;
    }

    private List<ClassInfo> getAllClasses(File resource) throws IOException {
        Pattern p = Config.style.getJavaClassRegex();
        Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

        List<ClassInfo> classes = new ArrayList<ClassInfo>(128);
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

            url = StringUtils.remove(url, "../");
            url = StringUtils.remove(url, "./");

            String anchor = StringUtils.substringAfter(url, "#");
            String linkUrl = StringUtils.substringBefore(url, "#") + "#" + AnchorNameManager.getNewAnchorName(anchor);

            if (Config.style.isMethod(url)) {
                MethodInfo info = new MethodInfo();
                info.setName(name);
                info.setFullName(Config.style.getMethodFullName(url));
                info.setUrl(linkUrl);
                classinfo.addMethod(info);
            } else {
                FieldInfo info = new FieldInfo();
                info.setName(name);
                info.setUrl(linkUrl);
                classinfo.addField(info);
            }
        }
    }
}
