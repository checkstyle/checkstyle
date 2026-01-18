/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

/** Test invalid regular method names. */
public class InputGoogleMethodNameInvalidRegular {
    void Foo() {} // violation 'Method name 'Foo' must start with a lowercase letter, min 2 chars'

    void f() {} // violation 'Method name 'f' must start with a lowercase letter, min 2 chars'

    void foo_bar() {} // violation 'Method name 'foo_bar' has invalid underscore usage'

    void foo_Bar() {} // violation 'Method name 'foo_Bar' has invalid underscore usage'

    void foo__bar() {}
    // violation above, 'Method name 'foo__bar' has invalid underscore usage'

    void gradle_9_5_1() {}
    // violation above, 'Method name 'gradle_9_5_1' has invalid underscore usage'

    void jdk_9_0_392() {}
    // violation above, 'Method name 'jdk_9_0_392' has invalid underscore usage'

    void guava_33_4_5() {}
    // violation above, 'Method name 'guava_33_4_5' has invalid underscore usage'

    void a_1() {} // violation 'Method name 'a_1' has invalid underscore usage'

    void guava33_4_5_() {}
    // violation above, 'Method name 'guava33_4_5_' has invalid underscore usage'

    void guava33__4_5() {}
    // violation above, 'Method name 'guava33__4_5' has invalid underscore usage'

    void guava33_4_a() {}
    // violation above, 'Method name 'guava33_4_a' has invalid underscore usage'

    void _foo() {} // violation 'Method name '_foo' has invalid underscore usage'

    void foo_() {} // violation 'Method name 'foo_' has invalid underscore usage'

    void __foo() {} // violation 'Method name '__foo' has invalid underscore usage'

    void FOO() {} // violation 'Method name 'FOO' must start with a lowercase letter, min 2 chars'

    void f$bar() {} // violation 'Method name 'f\$bar' must start with a lowercase letter'

    void transferMoney_deductsFromSource() {}
    // violation above, 'Method name .* has invalid underscore usage'

    void foo_bar_baz() {}
    // violation above, 'Method name 'foo_bar_baz' has invalid underscore usage'

    void foo123_456_() {}
    // violation above, 'Method name 'foo123_456_' has invalid underscore usage'

    void fO() {}
    // violation above 'Method name 'fO' .* avoid single lowercase letter followed by uppercase'

    void xY_z() {} // violation 'Method name 'xY_z' has invalid underscore usage'

    void mName() {}
    // violation above, 'Method name 'mName' .* avoid single lowercase letter followed by uppercase'
}
