////////////////////////////////////////////////////////////////////////////////
// Annotation for use by package definitions
// Created: 2005
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Annotation for use by package definitions
 * @author Michael Studman
 */
@Target(value=ElementType.PACKAGE)
public @interface MyAnnotation
{
}