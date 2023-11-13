package org.example;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {

	private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);

	public static void main(String[] args) throws LifecycleException {
		String webAppLocation = "webapps/";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.addWebapp("/", new File(webAppLocation).getAbsolutePath());
		log.info("configuring app with baseDir: {}", new File("./" + webAppLocation).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}