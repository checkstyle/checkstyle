/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/ // violation one line below ''import' should be separated from previous line.'

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;import java.util.List;

import java.util.Calendar;
import java.util.Date;

public class InputEmptyLineSeparatorMultipleImportEmptyClass
{
}
