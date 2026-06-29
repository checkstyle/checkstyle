/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * {@link java.util.List.add}
 * {@link java.util.List#add(Object)}
 * {@link java.util.List.add(Object)}
 */
// violation 2 lines above 'Javadoc comment at column 10 has parse error.'
// Details: no viable alternative at input 'java.util.List.add' while parsing REFERENCE
class InputAbstractJavadocInvalidInlineMethodReferenceWithoutHash {
}
