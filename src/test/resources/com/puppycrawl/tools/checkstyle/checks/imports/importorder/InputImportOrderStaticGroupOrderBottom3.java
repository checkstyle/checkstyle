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

import org.antlr.v4.runtime.*; // ok

import java.util.Set; // violation

import static java.lang.Math.PI; // 2 violations
import static org.antlr.v4.runtime.Recognizer.EOF; // violation

public class InputImportOrderStaticGroupOrderBottom3
{

}
