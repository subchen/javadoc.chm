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
package jerbrick.tools.chm.style;

import java.util.regex.Pattern;

public class Javadoc7Style extends JavadocStyle {

    @Override
    public Pattern getAnchorNameRegex() {
        return Pattern.compile("<a name=\"([^\"]*)\"[^>]*>");
    }

    @Override
    public Pattern getIndexRegex() {
        return Pattern.compile("<dt><span class=\"strong\"><a href=\"([^\"]*)\"[^>]*>([^<]*)</a></span>");
    }

    @Override
    public Pattern getJavaClassRegex() {
        return Pattern.compile("<a href=\"(([^\"]*)\\.html)\"[^>]*>(?:<i>)?([^<]*)(?:</i>)?</a>");
    }

    @Override
    public Pattern getJavaFieldRegex() {
        return Pattern.compile("<td class=\"col[a-zA-Z]+\"><code><strong><a href=\"([^\"]*)\"[^>]*>([^<]*)</a>");
    }

    @Override
    public Pattern getIndexUrlRegex() {
        return Pattern.compile("<a href=\"([^\"]*)\"[^>]*>");
    }

    @Override
    public Pattern getApiTitleRegex() {
        return Pattern.compile("<title>([^<]*)</title>");
    }
}
