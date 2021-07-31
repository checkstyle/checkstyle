/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

/*violation: for test that there's warning when block comment isn't
  separated from PACKAGE_DEF by line.*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.packageinfo.test3; // violation

//that import is for doing check of PACKAGE_DEF,
//because EmptyLineSeparatorCheck doesn't check last token
import java.lang.System;
