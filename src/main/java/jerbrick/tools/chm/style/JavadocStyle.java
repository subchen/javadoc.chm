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

public class JavadocStyle {

    public Pattern getAnchorNameRegex() {
        return Pattern.compile("<A NAME=\"([^\"]*)\"[^>]*>");
    }

    public Pattern getIndexRegex() {
        return Pattern.compile("<DT><A HREF=\"([^\"]*)\"[^>]*><B>([^<]*)</B></A>");
    }

    public Pattern getJavaClassRegex() {
        return Pattern.compile("<A HREF=\"(([^\"]*)\\.html)\"[^>]*>(?:<I>)?([^<]*)(?:</I>)?</A>");
    }

    public Pattern getJavaFieldRegex() {
        return Pattern.compile("<TD><CODE><B><A HREF=\"([^\"]*)\"[^>]*>([^<]*)</A>");
    }

    public Pattern getIndexUrlRegex() {
        return Pattern.compile("<A HREF=\"([^\"]*)\"[^>]*>");
    }

    public Pattern getApiTitleRegex() {
        return Pattern.compile("<TITLE>([^<]*)</TITLE>");
    }

}
