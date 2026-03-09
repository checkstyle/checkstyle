/*
GoogleNonConstantFieldName

*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test Invalid non-constant field names. */
public class InputGoogleNonConstantFieldNameInvalid {

    int Foo; // violation 'Non-constant field name 'Foo' must start lowercase, be at least 2 chars'

    int f;// violation 'Non-constant field name 'f' must start lowercase, be at least 2 chars'

    int foo_bar; // violation ''foo_bar' .* underscores allowed only between adjacent digits.'

    int foo_Bar; // violation ''foo_Bar' .* underscores allowed only between adjacent digits.'

    public int foo__bar;
    // violation above, ''foo__bar' .* underscores allowed only between adjacent digits.'

    protected int gradle_9_5_1;
    // violation above, ''gradle_9_5_1' .* underscores allowed only between adjacent digits.'

    final int jdk_9_0_392 = 0;
    // violation above, ''jdk_9_0_392' .* underscores allowed only between adjacent digits.'

    protected final int guava_33_4_5 = 0;
    // violation above, ''guava_33_4_5' .* underscores allowed only between adjacent digits.'

    private int a_1;
    // violation above, ''a_1' .* underscores allowed only between adjacent digits.'

    int guava33_4_5_;
    // violation above, ''guava33_4_5_' .* underscores allowed only between adjacent digits.'

    private final int guava33__4_5 = 0;
    // violation above, ''guava33__4_5' .* underscores allowed only between adjacent digits.'

    public final int guava33_4_a = 0;
    // violation above, ''guava33_4_a' .* underscores allowed only between adjacent digits.'

    int _foo; // violation ''_foo' .* underscores allowed only between adjacent digits.'

    int foo_; // violation ''foo_' .* underscores allowed only between adjacent digits.'

    int __foo; // violation ''__foo' .* underscores allowed only between adjacent digits.'

    int FOO; // violation 'Non-constant field name 'FOO' must start lowercase, be at least 2 chars'

    int f$bar;
    // violation above, ''f\$bar' must .* contain only letters, digits or underscores'

    int FO; // violation 'Non-constant field name 'FO' must start lowercase, be at least 2 chars'

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

    int a;// violation 'Non-constant field name 'a' must start lowercase, be at least 2 chars'

    int x;// violation 'Non-constant field name 'x' must start lowercase, be at least 2 chars'

    int z;// violation 'Non-constant field name 'z' must start lowercase, be at least 2 chars'

    int aB; // violation ''aB' must .* avoid single lowercase letter followed by uppercase'

    int xY; // violation ''xY' must .* avoid single lowercase letter followed by uppercase'

    int zA; // violation ''zA' must .* avoid single lowercase letter followed by uppercase'

    int FooBar;
    // violation above, 'Non-constant field name 'FooBar' must start lowercase'

    int XMLParser;
    // violation above, 'Non-constant field name 'XMLParser' must start lowercase'

    int HTTPClient;
    // violation above, 'Non-constant field name 'HTTPClient' must start lowercase'

    int foo_1bar;
    // violation above, ''foo_1bar' .* underscores allowed only between adjacent digits.'

    int foo1_bar;
    // violation above, ''foo1_bar' .* underscores allowed only between adjacent digits.'

    int foo_bar_baz;
    // violation above, ''foo_bar_baz'.* underscores allowed only between adjacent digits.'

    int foo$bar;
    // violation above, ''foo\$bar' must .* contain only letters, digits or underscores'

    int $foo;
    // violation above, ''\$foo' must .* contain only letters, digits or underscores'

    int bar$;
    // violation above, ''bar\$' must .* contain only letters, digits or underscores'

    int foo_1_a;
    // violation above, ''foo_1_a' .* underscores allowed only between adjacent digits.'

    int foo1__2;
    // violation above, ''foo1__2' .* underscores allowed only between adjacent digits.'
}
