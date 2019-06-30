/*violation: for test that there's warning when block comment isn't
  separated from PACKAGE_DEF by line.*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.packageinfo.test3;

//that import is for doing check of PACKAGE_DEF,
//because EmptyLineSeparatorCheck doesn't check last token
import java.lang.System;
