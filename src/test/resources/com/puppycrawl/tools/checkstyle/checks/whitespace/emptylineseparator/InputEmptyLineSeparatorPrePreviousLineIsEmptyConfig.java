/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

// Config-only sidecar for testPrePreviousLineIsEmpty. The actual target lives in
// InputEmptyLineSeparatorPrePreviousLineIsEmpty and must stay unchanged so that
// package sits at line 3 with two blank lines above it, keeping the boundary
// check in EmptyLineSeparatorCheck#isPrePreviousLineEmpty covered.
public class InputEmptyLineSeparatorPrePreviousLineIsEmptyConfig {
}
