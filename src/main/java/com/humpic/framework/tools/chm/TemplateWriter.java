package com.humpic.framework.tools.chm;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import com.humpic.framework.tools.chm.reader.KeyManager;

public class TemplateWriter {
	private final VelocityEngine ve;

	public TemplateWriter() {
		ve = new VelocityEngine();
		ve.init();
	}

	public void apply(Map context) throws Exception {
		context.put("encoding", Config.encoding);

		// write redir html.
		Collection<KeyManager> keyManagers = (Collection<KeyManager>) context.get("keyManagers");
		for (KeyManager keyManager : keyManagers) {
			if (keyManager.isRepeat()) {
				keyManager.sort();
				context.put("keyManager", keyManager);
				renderTemplate(keyManager.getUrl(), context, "/template/redir.vm");
			}
		}

		// write project files.
		renderTemplate("htmlhelp.hhp", context, "/template/hhp.vm");
		renderTemplate("htmlhelp.hhk", context, "/template/hhk.vm");
		renderTemplate("htmlhelp.hhc", context, "/template/hhc.vm");
		renderTemplate("build.bat", context, "/template/build.vm");

		// write hhc.exe, hha.dll
		outputBinaryFile("hhc.exe", "/bin/hhc.exe");
		outputBinaryFile("hha.dll", "/bin/hha.dll");
	}

	private void renderTemplate(String fileName, Map context, String templateName) throws Exception {
		File file = new File(Config.apiLocation, fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println(file);

		String templateContent = IOUtils.toString(getClass().getResourceAsStream(templateName), "utf-8");

		VelocityContext ctx = new VelocityContext(context);
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), Config.encoding);
		ve.evaluate(ctx, writer, "", templateContent);
		writer.close();
	}

	private void outputBinaryFile(String fileName, String templateName) throws Exception {
		File file = new File(Config.apiLocation, fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println(file);

		InputStream is = getClass().getResourceAsStream(templateName);
		OutputStream os = new FileOutputStream(file);
		IOUtils.copy(is, os);
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(os);
	}
}
