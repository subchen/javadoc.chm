/**
 * javadoc.chm
 * http://subchen.github.io/javadoc.chm/
 * 
 * Copyright 2010-2013 Guoqiang Chen. All rights reserved.
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class ApiReader {
    private JavaInfoReader javaInfoReader = new JavaInfoReader();
    private IndexesReader indexesReader = new IndexesReader();
    private Map<String, Object> context = new HashMap<String, Object>();

    public Map<String, Object> getApiContext() throws Exception {
        // add all files
        Set<String> files = getAllFiles(Config.apiLocation, new HashSet<String>());

        // replace anthor
        Iterator<String> it = files.iterator();
        while (it.hasNext()) {
            String file = it.next();
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
            Map<String, String> indexesMap = new HashMap<String, String>();
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
        List<Map.Entry<String, String>> sortIndexes = new ArrayList<Map.Entry<String, String>>(indexesMap.size());
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
