/*
ImportOrder
option = top
groups = org, java
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

import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // ok

import org.antlr.v4.runtime.*; // violation

import java.util.Set; // violation
import org.junit.Test; // violation

public class InputImportOrderStaticOnDemandGroupOrder2
{

}
