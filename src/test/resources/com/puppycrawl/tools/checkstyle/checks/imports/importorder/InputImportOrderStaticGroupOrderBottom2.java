/*
ImportOrder
option = bottom
groups = org, java
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import org.antlr.v4.runtime.*; // ok

import java.util.Set; // violation

import static java.lang.Math.PI; // violation
import static org.antlr.v4.runtime.Recognizer.EOF; // ok

public class InputImportOrderStaticGroupOrderBottom2
{

}
