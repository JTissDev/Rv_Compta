package com.jtissdev_API.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class create a banner for log files
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.5
 */
public class AppBanner {
	private static final Logger logger = LoggerFactory.getLogger(AppBanner.class);

	public static void display() {
		Properties props = new Properties();
		try (InputStream is = AppBanner.class.getClassLoader().getResourceAsStream("app-info.properties")) {
			if (is != null) {
				props.load(is);
			}
		} catch (Exception e) {
			logger.error("Impossible de charger les infos du projet");
		}

		String name = props.getProperty("app.name", "Rv_Compta");
		String version = props.getProperty("app.version", "Unknown");
		String description = props.getProperty("app.description", "");
		String org = props.getProperty("app.org", "JTissDev");
		String devs = props.getProperty("app.devs", "jtiss");

		logger.info("");
		logger.info("################################################################################");
		logger.info("## 🚀 STARTING APPLICATION : {}", name.toUpperCase());
		logger.info("## ----------------------------------------------------------------------------");
		logger.info("## Version       : {}", version);
		logger.info("## Description   : {}", description);
		logger.info("## Organization  : {}", org);
		logger.info("## Developed by  : {}", devs);
		logger.info("## Status        : RUNNING");
		logger.info("################################################################################");
		logger.info("");
	}
}
