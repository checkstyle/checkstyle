/*
GoogleNonConstantFieldName

*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test Invalid non-constant field names. */
public class InputGoogleNonConstantFieldNameInvalid {

    int Foo; // violation 'Non-constant field name 'Foo' must start with a lowercase letter'

    int f;// violation 'Non-constant field name 'f' must start with a lowercase letter, min 2 chars'

    int foo_bar; // violation 'Non-constant field name 'foo_bar' has invalid underscore usage'

    int foo_Bar; // violation 'Non-constant field name 'foo_Bar' has invalid underscore usage'

    int foo__bar;
    // violation above, 'Non-constant field name 'foo__bar' has invalid underscore usage'

    int gradle_9_5_1;
    // violation above, 'Non-constant field name 'gradle_9_5_1' has invalid underscore usage'

    int jdk_9_0_392;
    // violation above, 'Non-constant field name 'jdk_9_0_392' has invalid underscore usage'

    int guava_33_4_5;
    // violation above, 'Non-constant field name 'guava_33_4_5' has invalid underscore usage'

    int a_1;
    // violation above, 'Non-constant field name 'a_1' has invalid underscore usage'

    int guava33_4_5_;
    // violation above, 'Non-constant field name 'guava33_4_5_' has invalid underscore usage'

    int guava33__4_5;
    // violation above, 'Non-constant field name 'guava33__4_5' has invalid underscore usage'

    int guava33_4_a;
    // violation above, 'Non-constant field name 'guava33_4_a' has invalid underscore usage'

    int _foo; // violation 'Non-constant field name '_foo' has invalid underscore usage'

    int foo_; // violation 'Non-constant field name 'foo_' has invalid underscore usage'

    int __foo; // violation 'Non-constant field name '__foo' has invalid underscore usage'

    int FOO; // violation 'Non-constant field name 'FOO' must start with a lowercase letter'

    int f$bar;
    // violation above, ''f\$bar' must .* contain only letters, digits, or underscores.'

    int fO; // violation 'Non-constant field name 'fO' must start with a lowercase letter'

    int mField; // violation, ''mField' must .* avoid single lowercase letter followed by uppercase'

    int pValue;
    // violation above, ''pValue' must .* avoid single lowercase letter followed by uppercase'

    int sInstance;
    // violation above, ''sInstance' must .* avoid single lowercase letter followed by uppercase'

    int bFlag;
    // violation above, ''bFlag' must .* avoid single lowercase letter followed by uppercase'

    int nCount;
    // violation above, ''nCount' must .* avoid single lowercase letter followed by uppercase'

    int iIndex;
    // violation above, ''iIndex' must .* avoid single lowercase letter followed by uppercase'

    int a;// violation 'Non-constant field name 'a' must start with a lowercase letter, min 2 chars'

    int x;// violation 'Non-constant field name 'x' must start with a lowercase letter, min 2 chars'

    int z;// violation 'Non-constant field name 'z' must start with a lowercase letter, min 2 chars'

    int aB; // violation ''aB' must .* avoid single lowercase letter followed by uppercase'

    int xY; // violation ''xY' must .* avoid single lowercase letter followed by uppercase'

    int zA; // violation ''zA' must .* avoid single lowercase letter followed by uppercase'

    int FooBar;
    // violation above, 'Non-constant field name 'FooBar' must start with a lowercase letter'

    int XMLParser;
    // violation above, 'Non-constant field name 'XMLParser' must start with a lowercase letter'

    int HTTPClient;
    // violation above, 'Non-constant field name 'HTTPClient' must start with a lowercase letter'

    int foo_1bar;
    // violation above, 'Non-constant field name 'foo_1bar' has invalid underscore usage'

    int foo1_bar;
    // violation above, 'Non-constant field name 'foo1_bar' has invalid underscore usage'

    int foo_bar_baz;
    // violation above, 'Non-constant field name 'foo_bar_baz' has invalid underscore usage'

    int foo$bar;
    // violation above, ''foo\$bar' must .* contain only letters, digits, or underscores.'

    int $foo;
    // violation above, ''\$foo' must .* contain only letters, digits, or underscores.'

    int bar$;
    // violation above, ''bar\$' must .* contain only letters, digits, or underscores.'

    int foo_1_a;
    // violation above, 'Non-constant field name 'foo_1_a' has invalid underscore usage'

    int foo1__2;
    // violation above, 'Non-constant field name 'foo1__2' has invalid underscore usage'
}
