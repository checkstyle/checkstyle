/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

public class InputGoogleMethodNameTestMethodsWithoutAnnotation {

    public void test_naming() {}

    public void test_naming_() {}
    // violation above, 'Test method name 'test_naming_' has invalid underscore usage'

    public void testFoo() {}

    public void test_Foo() {}
    // violation above, 'Test method name 'test_Foo' segment must .* start lowercase'

    public void test2() {}

    public void test_2() {}
    // violation above, 'Test method name 'test_2' has invalid underscore usage'

    public void test() {}

    public void Test() {}
    // violation above 'Method name 'Test' must be .* start lowercase'
}
