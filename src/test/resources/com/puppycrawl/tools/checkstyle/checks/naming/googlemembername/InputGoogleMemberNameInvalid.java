/*
GoogleMemberName

*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test Invalid member names. */
public class InputGoogleMemberNameInvalid {

    int Foo; // violation 'Member name 'Foo' must start with a lowercase letter, min 2 chars'

    int f; // violation 'Member name 'f' must start with a lowercase letter, min 2 chars'

    int foo_bar; // violation 'Member name 'foo_bar' has invalid underscore usage'

    int foo_Bar; // violation 'Member name 'foo_Bar' has invalid underscore usage'

    int foo__bar;
    // violation above, 'Member name 'foo__bar' has invalid underscore usage'

    int gradle_9_5_1;
    // violation above, 'Member name 'gradle_9_5_1' has invalid underscore usage'

    int jdk_9_0_392;
    // violation above, 'Member name 'jdk_9_0_392' has invalid underscore usage'

    int guava_33_4_5;
    // violation above, 'Member name 'guava_33_4_5' has invalid underscore usage'

    int a_1;
    // violation above, 'Member name 'a_1' has invalid underscore usage'

    int guava33_4_5_;
    // violation above, 'Member name 'guava33_4_5_' has invalid underscore usage'

    int guava33__4_5;
    // violation above, 'Member name 'guava33__4_5' has invalid underscore usage'

    int guava33_4_a;
    // violation above, 'Member name 'guava33_4_a' has invalid underscore usage'

    int _foo; // violation 'Member name '_foo' has invalid underscore usage'

    int foo_; // violation 'Member name 'foo_' has invalid underscore usage'

    int __foo; // violation 'Member name '__foo' has invalid underscore usage'

    int FOO; // violation 'Member name 'FOO' must start with a lowercase letter, min 2 chars'

    int f$bar; // violation 'Member name 'f\$bar' must start with a lowercase letter, min 2 chars'

    int fO; // violation 'Member name 'fO' must start with a lowercase letter, min 2 chars'

    int mField;
    // violation above, 'Member name 'mField' must start with a lowercase letter, min 2 chars'

    int pValue;
    // violation above, 'Member name 'pValue' must start with a lowercase letter, min 2 chars'

    int sInstance;
    // violation above, 'Member name 'sInstance' must start with a lowercase letter, min 2 chars'

    int bFlag;
    // violation above, 'Member name 'bFlag' must start with a lowercase letter, min 2 chars'

    int nCount;
    // violation above, 'Member name 'nCount' must start with a lowercase letter, min 2 chars'

    int iIndex;
    // violation above, 'Member name 'iIndex' must start with a lowercase letter, min 2 chars'

    int a; // violation 'Member name 'a' must start with a lowercase letter, min 2 chars'

    int x; // violation 'Member name 'x' must start with a lowercase letter, min 2 chars'

    int z; // violation 'Member name 'z' must start with a lowercase letter, min 2 chars'

    int aB; // violation 'Member name 'aB' must start with a lowercase letter, min 2 chars'

    int xY; // violation 'Member name 'xY' must start with a lowercase letter, min 2 chars'

    int zA; // violation 'Member name 'zA' must start with a lowercase letter, min 2 chars'

    int FooBar;
    // violation above, 'Member name 'FooBar' must start with a lowercase letter, min 2 chars'

    int XMLParser;
    // violation above, 'Member name 'XMLParser' must start with a lowercase letter, min 2 chars'

    int HTTPClient;
    // violation above, 'Member name 'HTTPClient' must start with a lowercase letter, min 2 chars'

    int foo_1bar;
    // violation above, 'Member name 'foo_1bar' has invalid underscore usage'

    int foo1_bar;
    // violation above, 'Member name 'foo1_bar' has invalid underscore usage'

    int foo_bar_baz;
    // violation above, 'Member name 'foo_bar_baz' has invalid underscore usage'

    int foo$bar;
    // violation above, 'Member name 'foo\$bar' must start with a lowercase letter, min 2 chars'

    int $foo;
    // violation above, 'Member name '\$foo' must start with a lowercase letter, min 2 chars'

    int bar$;
    // violation above, 'Member name 'bar\$' must start with a lowercase letter, min 2 chars'

    int foo_1_a;
    // violation above, 'Member name 'foo_1_a' has invalid underscore usage'

    int foo1__2;
    // violation above, 'Member name 'foo1__2' has invalid underscore usage'
}
