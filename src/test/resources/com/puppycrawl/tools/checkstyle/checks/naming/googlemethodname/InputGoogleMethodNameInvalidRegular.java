/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

/** Test invalid regular method names. */
public class InputGoogleMethodNameInvalidRegular {
    void Foo() {} // violation 'Method name 'Foo' is not valid per Google Java Style Guide.'

    void f() {} // violation 'Method name 'f' is not valid per Google Java Style Guide.'

    void foo_bar() {} // violation 'Method name 'foo_bar' is not valid per Google Java Style Guide.'

    void foo_Bar() {} // violation 'Method name 'foo_Bar' is not valid per Google Java Style Guide.'

    void foo__bar() {}
    // violation above, 'Method name 'foo__bar' is not valid per Google Java Style Guide.'

    void gradle_9_5_1() {}
    // violation above, 'Method name 'gradle_9_5_1' is not valid per Google Java Style Guide.'

    void jdk_9_0_392() {}
    // violation above, 'Method name 'jdk_9_0_392' is not valid per Google Java Style Guide.'

    void guava_33_4_5() {}
    // violation above, 'Method name 'guava_33_4_5' is not valid per Google Java Style Guide.'

    void a_1() {} // violation 'Method name 'a_1' is not valid per Google Java Style Guide.'

    void guava33_4_5_() {}
    // violation above, 'Method name 'guava33_4_5_' is not valid per Google Java Style Guide.'

    void guava33__4_5() {}
    // violation above, 'Method name 'guava33__4_5' is not valid per Google Java Style Guide.'

    void guava33_4_a() {}
    // violation above, 'Method name 'guava33_4_a' is not valid per Google Java Style Guide.'

    void _foo() {} // violation 'Method name '_foo' is not valid per Google Java Style Guide.'

    void foo_() {} // violation 'Method name 'foo_' is not valid per Google Java Style Guide.'

    void __foo() {} // violation 'Method name '__foo' is not valid per Google Java Style Guide.'

    void FOO() {} // violation 'Method name 'FOO' is not valid per Google Java Style Guide.'

    void f$oo() {} // violation 'Method name 'f\$oo' is not valid per Google Java Style Guide.'

    void transferMoney_deductsFromSource() {}
    // violation above, 'Method name .* is not valid per Google Java Style Guide.'

    void foo_bar_baz() {}
    // violation above, 'Method name 'foo_bar_baz' is not valid per Google Java Style Guide.'

    void foo123_456_() {}
    // violation above, 'Method name 'foo123_456_' is not valid per Google Java Style Guide.'

    void fO() {} // violation 'Method name 'fO' is not valid per Google Java Style Guide.'

    void xY_z() {} // violation 'Method name 'xY_z' is not valid per Google Java Style Guide.'
}
