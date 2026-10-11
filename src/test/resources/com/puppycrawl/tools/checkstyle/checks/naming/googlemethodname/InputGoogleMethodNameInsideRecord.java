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
    // violation above """Method name 'Foo' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void mName() {}
    // violation above """Method name 'mName' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    @Test
    void testLogin_failsGracefully() {}

    @Test
    void login_fails() {}
}
