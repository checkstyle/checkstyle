package org.checkstyle.suppressionxpathfilter.noenumtrailingcomma;

public class InputXpathNoEnumTrailingCommaOne {

    enum Foo3 {
        FOO,
        BAR, //warn
    }
}
