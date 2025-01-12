/*
ImportOrder
option = (default)under
groups = /awt/, /jar/, /jar.*.JarInputStream/, /jar.*.JarInput/
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button;

import java.util.jar.Some;

import java.util.jar.JarInputStream;

import java.util.jar.JarInputMy;

public class InputImportOrder6 {
}
