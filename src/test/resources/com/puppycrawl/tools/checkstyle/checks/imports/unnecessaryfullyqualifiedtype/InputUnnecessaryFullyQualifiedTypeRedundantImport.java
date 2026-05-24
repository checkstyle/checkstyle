/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.Map;

public class InputUnnecessaryFullyQualifiedTypeRedundantImport {

    // violation below, 'Unnecessary fully qualified type - java.util.Map.'
    private java.util.Map<String, String> redundant;

    private Map<String, String> simple;
}
