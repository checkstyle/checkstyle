/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

public class InputUnnecessaryFullyQualifiedTypeSamePackage {

    // violation below, 'Unnecessary fully qualified type - .*unnecessaryfullyqualifiedtype.Helper.'
    private com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype.Helper a;

    private Helper b;

    // violation below, 'Unnecessary fully qualified type - java.lang.Runnable.'
    private java.lang.Runnable task;
}
