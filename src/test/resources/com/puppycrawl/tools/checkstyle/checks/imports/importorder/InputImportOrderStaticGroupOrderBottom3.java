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
// violation below 'Extra separation in import group before 'java.lang.Math.PI'
import static java.lang.Math.PI; // violation 'Extra separation in import group before 'java.lang.Math.PI''  // violation 'Import 'java.lang.Math.PI' violates the configured relative order between static and non-static imports.'
import static org.antlr.v4.runtime.Recognizer.EOF; // violation 'Import statement for 'org.antlr.v4.runtime.Recognizer.EOF' violates the configured import group order.'

public class InputImportOrderStaticGroupOrderBottom3
{

}
