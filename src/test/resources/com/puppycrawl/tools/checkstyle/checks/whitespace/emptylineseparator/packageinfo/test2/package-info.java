/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

/**OK: for test allowing to place javadoc before PACKAGE_DEF.*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.packageinfo.test2;

//that import is for doing check of PACKAGE_DEF,
//because EmptyLineSeparatorCheck doesn't check last token
import java.lang.System;
