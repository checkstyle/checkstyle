package org.checkstyle.suppressionxpathfilter.noenumtrailingcomma;

public class SuppressionXpathRegressionNoEnumTrailingCommaOne {

    enum Foo3 {
        FOO,
        BAR, //warn
    }
}
