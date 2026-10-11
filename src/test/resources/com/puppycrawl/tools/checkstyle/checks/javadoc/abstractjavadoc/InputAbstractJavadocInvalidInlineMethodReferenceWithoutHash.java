/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

// Details: no viable alternative at input 'java.util.List.add' while parsing REFERENCE
// violation 4 lines below 'Javadoc comment at column 10 has parse error.'
/**
 * {@link java.util.List.add}
 * {@link java.util.List#add(Object)}
 * {@link java.util.List.add(Object)}
 */
class InputAbstractJavadocInvalidInlineMethodReferenceWithoutHash {
}
