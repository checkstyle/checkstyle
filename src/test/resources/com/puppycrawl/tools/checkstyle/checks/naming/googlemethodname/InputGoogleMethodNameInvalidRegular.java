/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

/** Test invalid regular method names. */
public class InputGoogleMethodNameInvalidRegular {
    void Foo() {}
    // violation above """Method name 'Foo' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void f() {}
    // violation above """Method name 'f' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void foo_bar() {}
    // violation above """Method name 'foo_bar' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void foo_Bar() {}
    // violation above """Method name 'foo_Bar' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void foo__bar() {}
    // violation above """Method name 'foo__bar' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void gradle_9_5_1() {}
    // violation above """Method name 'gradle_9_5_1' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void jdk_9_0_392() {}
    // violation above """Method name 'jdk_9_0_392' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void guava_33_4_5() {}
    // violation above """Method name 'guava_33_4_5' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void a_1() {}
    // violation above """Method name 'a_1' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void guava33_4_5_() {}
    // violation above """Method name 'guava33_4_5_' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void guava33__4_5() {}
    // violation above """Method name 'guava33__4_5' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void guava33_4_a() {}
    // violation above """Method name 'guava33_4_a' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void _foo() {}
    // violation above """Method name '_foo' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void foo_() {}
    // violation above """Method name 'foo_' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void __foo() {}
    // violation above """Method name '__foo' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void FOO() {}
    // violation above """Method name 'FOO' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void transferMoney_deductsFromSource() {}
    // violation above """Method name 'transferMoney_deductsFromSource' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void foo_bar_baz() {}
    // violation above """Method name 'foo_bar_baz' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void foo123_456_() {}
    // violation above """Method name 'foo123_456_' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void fO() {}
    // violation above """Method name 'fO' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void xY_z() {}
    // violation above """Method name 'xY_z' has invalid underscore usage,
    // underscores only allowed between adjacent digits."""

    void mName() {}
    // violation above """Method name 'mName' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""

    void fooBAR() {}
    // violation above """Method name 'fooBAR' must be more than a character, start lowercase,
    // and not have a single lowercase followed by uppercase, or consecutive uppercase."""
}
