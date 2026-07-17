/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test methods inside an interface. */
public interface InputGoogleMethodNameInsideInterface {

    void fooBar();
    void parseXml();

    void Bar(); // violation 'Method name 'Bar' must be more than a character, start lowercase'

    default void barBaz() {}

    default void mValue() {}
    // violation above, ''mValue' must .* not have a single lowercase followed by uppercase'

    @Test
    default void parseJson_returnsValidData() {}

    @Test
    default void testFoo_bar() {}
}
