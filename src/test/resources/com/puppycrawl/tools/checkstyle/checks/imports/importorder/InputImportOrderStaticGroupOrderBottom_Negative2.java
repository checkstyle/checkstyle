/*
ImportOrder
option = top
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

import static java.lang.Math.PI; // violation 'Wrong order for 'java.lang.Math.PI' import.'
import static org.antlr.v4.runtime.Recognizer.EOF;

import java.util.Set;

public class InputImportOrderStaticGroupOrderBottom_Negative2
{

}
