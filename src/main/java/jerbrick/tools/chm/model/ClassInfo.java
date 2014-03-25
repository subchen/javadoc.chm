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
package jerbrick.tools.chm.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ClassInfo {

    private String fullName;
    private String shortName;
    private String url;
    private List<FieldInfo> fields = new ArrayList<FieldInfo>();
    private List<MethodInfo> methods = new ArrayList<MethodInfo>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getPackageName() {
        return StringUtils.remove(this.fullName, "." + this.shortName);
    }

    public void addField(FieldInfo info) {
        fields.add(info);
    }

    public void addMethod(MethodInfo info) {
        methods.add(info);
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

}
