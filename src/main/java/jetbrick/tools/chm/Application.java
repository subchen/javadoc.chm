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
package jetbrick.tools.chm;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import jetbrick.tools.chm.reader.ApiReader;
import jetbrick.tools.chm.style.*;
import org.apache.commons.io.FileUtils;

public class Application {

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            Config.apiLocation = new File(args[0]);
        }
        if (args.length > 1) {
            Config.encoding = args[1];
        }

        if (new File(Config.apiLocation, "resources/fonts").exists()) {
            Config.style = new Javadoc8Style();
        } else if (new File(Config.apiLocation, "resources/background.gif").exists()) {
            Config.style = new Javadoc7Style();
        } else {
            Config.style = new JavadocStyle();
        }

        clean();

        ApiReader api = new ApiReader();
        Map<String, Object> apiContext = api.getApiContext();

        TemplateWriter writer = new TemplateWriter();
        writer.apply(apiContext);
    }

    private static void clean() throws IOException {
        File f = new File(Config.apiLocation, "htmlhelp.hhp");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "htmlhelp.hhc");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "htmlhelp.hhk");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "build.bat");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "hhc.exe");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "hha.dll");
        if (f.exists()) f.delete();
        f = new File(Config.apiLocation, "redirs/");
        if (f.exists()) FileUtils.deleteDirectory(f);
    }

}
