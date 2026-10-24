/*
NoEnumTrailingComma


*/

package org.checkstyle.suppressionxpathfilter.coding.noenumtrailingcomma;

public class InputXpathNoEnumTrailingCommaInnerClass {
    class Inner {
        enum Foo {
            ONE,
            TWO, // violation 'Enum should not contain trailing comma.'
        }
    }
}
