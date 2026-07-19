/*
MethodCount
maxTotal = 1
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF,ENUM_CONSTANT_DEF,ENUM_DEF,INTERFACE_DEF,ANNOTATION_DEF,METHOD_DEF,RECORD_DEF,COMPACT_COMPILATION_UNIT


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCount5 { // violation 'Total number of methods is 2 (max allowed is 1).'
    void method1() {
    }

    private @interface Generates {}

    void method2() {
    }
}
