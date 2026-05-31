/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

import module java.base;

import // violation 'import statement should not be line-wrapped.'
    module
    java.sql;

import java.util.List;

import // violation 'import statement should not be line-wrapped.'
    java.util.Map;

public class InputNoLineWrapModuleImport {
}
