/*
InvalidJavadocPosition


*/

/** violation */ // violation
/** valid */
@Example
package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PACKAGE)
@interface Example {
}
