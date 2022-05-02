/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

//for test there's warning when single-line comment isn't separated from PACKAGE_DEF by line
//violation is expected after the next line. Starting * on next comment is to kill mutation
//*because utility doesn't check javadoc but any comment with starting *.
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.packageinfo.test4; // violation

//that import is for doing check of PACKAGE_DEF,
//because EmptyLineSeparatorCheck doesn't check last token
import java.lang.System;
