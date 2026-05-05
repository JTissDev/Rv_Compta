package com.jtissdev_API.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface for Group Test.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestGroup {
	String value(); // Le nom du bloc (ex: "RÉFÉRENTIEL", "PCP")
}