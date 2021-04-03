package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

/* Config:
 *
 * ignoreAnnotationElementDefaults = false
 */

/**
 * ignoreAnnotationElementDefaults = false
 */
@interface InputMagicNumberAnnotationElement {
	int value() default 10; // violation
	int[] value2() default {11}; // violation
}
