package com.jtissdev.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NotInTestCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// Cette condition renvoie FALSE si on détecte qu'on est en train de lancer des tests Surefire
		String surefire = System.getProperty("surefire.test.class.path");
		return surefire == null;
	}
}