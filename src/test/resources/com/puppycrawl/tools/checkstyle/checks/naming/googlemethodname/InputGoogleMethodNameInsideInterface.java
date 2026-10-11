/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test methods inside an interface. */
public interface InputGoogleMethodNameInsideInterface {

    void fooBar();
    void parseXml();

    void Bar();
    // violation above """Method name 'Bar' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    default void barBaz() {}

    default void mValue() {}
    // violation above """Method name 'mValue' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    @Test
    default void parseJson_returnsValidData() {}

    @Test
    default void testFoo_bar() {}
}
