/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test methods inside a record. */
public record InputGoogleMethodNameInsideRecord() {

    void fooBar() {}
    void parseXml() {}
    void guava33_4_5() {}

    void Foo() {}
    // violation above 'Method name 'Foo' .* start lowercase'

    void mName() {}
    // violation above 'Method name 'mName' .* have a single lowercase followed by uppercase.'

    @Test
    void testLogin_failsGracefully() {}

    @Test
    void login_fails() {}
}
