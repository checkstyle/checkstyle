/*
ImportOrder
option = above
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

import org.antlr.v4.runtime.*;

import java.util.Set; // violation 'Extra separation in import group before 'java.util.Set''
// violation below 'Extra separation in import group before 'java.lang.Math.*''
import static java.lang.Math.*; // violation 'Import 'java.lang.Math.*' violates the configured relative order between static and non-static imports.'
import static org.antlr.v4.runtime.CommonToken.*; // violation 'Import statement for 'org.antlr.v4.runtime.CommonToken.*' violates the configured import group order.'

public class InputImportOrderStaticOnDemandGroupOrderBottom3
{

}
