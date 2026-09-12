/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

// violation 2 lines below 'Javadoc comment at column 52 has parse error.'
// Details: no viable alternative at input '}' while parsing REFERENCE
/** <ul><li>a' {@link EntityEntry} (by way of {@link #;}</li></ul> */
class InputAbstractJavadocNumberFormatException{}
