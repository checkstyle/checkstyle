/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT
skipAnnotations = (default)true


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

import module java.base;

// violation below 'import statement should not be line-wrapped.'
import
    module
    java.sql;

import java.util.List;

// violation below 'import statement should not be line-wrapped.'
import
    java.util.Map;

@Deprecated
public class InputNoLineWrapModuleImport {
}
