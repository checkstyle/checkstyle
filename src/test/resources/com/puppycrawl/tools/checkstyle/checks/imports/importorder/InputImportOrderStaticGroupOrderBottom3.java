/*
ImportOrder
option = above
groups = org, java, sun
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import org.antlr.v4.runtime.*;

import java.util.Set; // violation 'Extra separation in import group before 'java.util.Set''

import static java.lang.Math.PI; // 2 violations
import static org.antlr.v4.runtime.Recognizer.EOF; // violation 'Wrong order for 'org.antlr.v4.runtime.Recognizer.EOF' import.'

public class InputImportOrderStaticGroupOrderBottom3
{

}
