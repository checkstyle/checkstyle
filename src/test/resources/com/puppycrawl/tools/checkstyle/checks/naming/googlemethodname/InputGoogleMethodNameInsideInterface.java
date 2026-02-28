/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test methods inside an interface. */
public interface InputGoogleMethodNameInsideInterface {

    void fooBar();
    void parseXml();

    void Bar(); // violation 'Method name 'Bar' must start with a lowercase letter'

    default void barBaz() {}

    default void mValue() {}
    // violation above, 'Method name 'mValue'.* avoid single lowercase letter followed by uppercase'

    @Test
    default void parseJson_returnsValidData() {}

    @Test
    default void testFoo_bar() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'
}
