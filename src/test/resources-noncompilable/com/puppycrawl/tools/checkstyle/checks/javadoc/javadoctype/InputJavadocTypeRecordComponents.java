//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/* Config:
 *
 * scope = protected
 * allowMissingParamTags = false
 * allowUnknownTags = false
 */

/**
 * My new record.
 *
 * @param value Sponge Bob rules the world!
 */
public record InputJavadocTypeRecordComponents(String value) { // ok
}
