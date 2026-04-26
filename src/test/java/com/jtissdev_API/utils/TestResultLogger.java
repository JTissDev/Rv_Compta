package com.jtissdev_API.utils;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLogger implements BeforeAllCallback, AfterTestExecutionCallback {
	private static final Logger logger = LoggerFactory.getLogger(TestResultLogger.class);

	// S'exécute UNE SEULE FOIS au début de la classe de test
	@Override
	public void beforeAll(ExtensionContext context) {
		String className = context.getRequiredTestClass().getSimpleName();
		logger.info("");
		logger.info("================================================================================");
		logger.info("🚀 DÉBUT DE LA SÉQUENCE : {}", className);
		logger.info("================================================================================");
	}

	// S'exécute après CHAQUE méthode de test
	@Override
	public void afterTestExecution(ExtensionContext context) {
		String testName = context.getDisplayName();
		boolean testFailed = context.getExecutionException().isPresent();

		if (testFailed) {
			// Log en ERROR pour que le highlight Logback le mette en rouge dans la console
			logger.error("❌ ÉCHEC : [{}] - Raison : {}", testName,
					context.getExecutionException().get().getMessage());
		} else {
			logger.info("✅ SUCCÈS : [{}]", testName);
		}
	}
}