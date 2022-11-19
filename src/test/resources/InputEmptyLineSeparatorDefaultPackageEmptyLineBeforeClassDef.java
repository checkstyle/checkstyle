/*
com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck
requireEmptyLineAfterBlockStart = true
allowNoEmptyLineBeforeBlockEnd = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/
class InputEmptyLineSeparatorDefaultPackageEmptyLineBeforeClassDef {
}
class Class2 { // violation
}
