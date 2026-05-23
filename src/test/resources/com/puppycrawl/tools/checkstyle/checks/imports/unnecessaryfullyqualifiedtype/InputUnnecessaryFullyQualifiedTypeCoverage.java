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

    // 'java.util.logging.Logger' must stay qualified because the same-package
    // type 'Logger' is also referenced by its simple name
    private java.util.logging.Logger qualifiedLogger;

    private Logger samePackageLogger;

    private Pattern pattern;
}
