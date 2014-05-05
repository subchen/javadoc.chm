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
package jetbrick.tools.chm.reader;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jetbrick.tools.chm.Config;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class IndexesReader {

    private Map<String, KeyManager> keyManagerMaps = new HashMap<String, KeyManager>(256);

    public Map<String, String> getIndexes(File resource) throws IOException {
        Pattern p = Config.style.getIndexRegex();
        Matcher m = p.matcher(FileUtils.readFileToString(resource, Config.encoding));

        Map<String, String> indexMaps = new HashMap<String, String>(1024);
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

        if (url.indexOf('#') < 0) {
            key = text + " class";
        } else {
            key = StringUtils.substringBefore(text, "(");
            if (text.indexOf('(') != -1) {
                key = key + "()";
            }

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
