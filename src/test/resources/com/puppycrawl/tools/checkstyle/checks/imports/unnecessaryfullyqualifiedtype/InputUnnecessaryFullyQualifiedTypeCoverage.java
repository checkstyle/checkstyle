/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.regex.*;

public class InputUnnecessaryFullyQualifiedTypeCoverage {

    // violation below, 'Unnecessary fully qualified type - java.lang.String.'
    private java.lang.String first;

    // violation below, 'Unnecessary fully qualified type - java.lang.String.'
    private java.lang.String second;

    // a different 'Foo' is also referenced by simple name with no import, so it
    // resolves to a same-package type and the qualification is required
    private com.example.Foo qualifiedFoo;

    private Foo simpleFoo;

    private Pattern pattern;
}
