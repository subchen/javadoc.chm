package com.humpic.framework.tools.chm;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import com.humpic.framework.tools.chm.reader.ApiReader;
import com.humpic.framework.tools.chm.style.Javadoc7Style;

public class Application {

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			Config.apiLocation = new File(args[0]);
		}
		if (args.length > 1) {
			Config.encoding = args[1];
		}

		if (new File(Config.apiLocation, "resources/background.gif").exists()) {
			Config.style = new Javadoc7Style();
		}

		clean();

		ApiReader api = new ApiReader();
		Map apiContext = api.getApiContext();

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
