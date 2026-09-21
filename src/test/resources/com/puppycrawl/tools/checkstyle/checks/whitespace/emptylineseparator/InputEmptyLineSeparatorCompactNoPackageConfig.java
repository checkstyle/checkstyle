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

// Config-only sidecar for testCompactNoPackage. The actual target lives in
// InputEmptyLineSeparatorCompactNoPackage and must stay unchanged so that
// TYPE-child line numbers remain low enough to keep the boundary check in
// EmptyLineSeparatorCheck#isTwoPrecedingPreviousLinesFromCommentEmpty covered.
public class InputEmptyLineSeparatorCompactNoPackageConfig {
}
