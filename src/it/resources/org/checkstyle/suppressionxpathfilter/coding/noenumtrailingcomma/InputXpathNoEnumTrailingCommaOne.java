/*
NoEnumTrailingComma


*/

package org.checkstyle.suppressionxpathfilter.coding.noenumtrailingcomma;

public class InputXpathNoEnumTrailingCommaOne {

    enum Foo3 {
        FOO,
        BAR, // violation 'Enum should not contain trailing comma.'
    }
}
