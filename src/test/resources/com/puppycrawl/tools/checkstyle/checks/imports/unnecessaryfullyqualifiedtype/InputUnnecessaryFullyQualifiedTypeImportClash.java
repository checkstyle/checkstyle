/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.Date;

public class InputUnnecessaryFullyQualifiedTypeImportClash {

    // 'java.util.Date' is imported, so this 'java.sql.Date' must stay qualified
    // even though it is never referenced by its simple name
    private java.sql.Date sqlDate;
}
