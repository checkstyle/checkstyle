/*
ImportOrder
option = bottom
groups = org, java
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import org.antlr.v4.runtime.*;

import static java.lang.Math.PI;
import static org.antlr.v4.runtime.Recognizer.EOF;

import java.util.Set; // violation 'Wrong order for 'java.util.Set' import.'

public class InputImportOrderStaticGroupOrderBottom_Negative1
{

}
