/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/** <ul><li>a' {@link EntityEntry} (by way of {@link #;}</li></ul> */
// violation above 'Javadoc comment at column 52 has parse error.'
// Details: mismatched input ';' expecting MEMBER while parsing REFERENCE
class InputAbstractJavadocNumberFormatException{}
