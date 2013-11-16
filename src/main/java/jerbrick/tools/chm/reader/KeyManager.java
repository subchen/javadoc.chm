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

import java.util.*;
import org.apache.commons.lang.StringUtils;

public class KeyManager {
    private String key;
    private List<String> pairs = new ArrayList<String>();

    public KeyManager(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void add(String name, String url) {
        String text;
        if (name.indexOf('(') >= 0) {
            text = StringUtils.replace(name, key + "(", "<b>" + key + "</b>(");
        } else {
            text = StringUtils.replace(name, "." + key, ".<b>" + key + "</b>");
        }
        text = "<a href='" + url + "'>" + text + "</a>";
        pairs.add(text);
    }

    public boolean isRepeat() {
        return pairs.size() > 1;
    }

    public List<String> getPairs() {
        return pairs;
    }

    public String getUrl() {
        return "redirs/" + key + "_" + Math.abs(key.hashCode()) + ".html";
    }

    public void sort() {
        Collections.sort(pairs);
    }
}
