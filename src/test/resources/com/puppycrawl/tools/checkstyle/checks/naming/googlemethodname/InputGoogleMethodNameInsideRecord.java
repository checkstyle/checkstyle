/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test methods inside a record. */
public record InputGoogleMethodNameInsideRecord(String name, int age) {

    void fooBar() {}
    void parseXml() {}
    void guava33_4_5() {}

    void Foo() {} // violation 'Method name 'Foo' must start with a lowercase letter'

    void mName() {}
    // violation above, 'Method name 'mName' .* avoid single lowercase letter followed by uppercase'

    @Test
    void testLogin_failsGracefully() {}

    @Test
    void login_fails() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'
}
