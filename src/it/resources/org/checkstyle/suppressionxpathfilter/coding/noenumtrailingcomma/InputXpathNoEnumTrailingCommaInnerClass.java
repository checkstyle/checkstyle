package org.checkstyle.suppressionxpathfilter.coding.noenumtrailingcomma;

public class InputXpathNoEnumTrailingCommaInnerClass {
    class Inner {
        enum Foo {
            ONE,
            TWO, //warn
        }
    }
}
