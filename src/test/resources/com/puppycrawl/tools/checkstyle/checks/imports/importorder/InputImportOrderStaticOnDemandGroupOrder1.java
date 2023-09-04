/*
ImportOrder
option = top
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

import static java.lang.Math.*;
import static org.antlr.v4.runtime.CommonToken.*;

import org.antlr.v4.runtime.*; // violation 'Extra separation in import group before 'org.antlr.v4.runtime.*''

import java.util.Set; // violation 'Extra separation in import group before 'java.util.Set''
import org.junit.Test; // violation 'Wrong order for 'org.junit.Test' import.'

public class InputImportOrderStaticOnDemandGroupOrder1
{

}
