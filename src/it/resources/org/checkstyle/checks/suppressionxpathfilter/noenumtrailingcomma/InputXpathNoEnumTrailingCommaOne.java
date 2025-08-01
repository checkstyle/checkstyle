package org.checkstyle.checks.suppressionxpathfilter.noenumtrailingcomma;

public class InputXpathNoEnumTrailingCommaOne {

    enum Foo3 {
        FOO,
        BAR, //warn
    }
}
