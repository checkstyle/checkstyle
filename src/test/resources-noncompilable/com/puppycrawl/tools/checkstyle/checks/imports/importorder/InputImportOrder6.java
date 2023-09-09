/*
ImportOrder
option = (default)under
groups = awt, jar, .util., .jar., .util, jarInputStream
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import static javax.swing.WindowConstants.*;
import java.util.jar.JarInputStream; // violation 'Wrong order for 'java.util.jar.JarInputStream' import.'

public class InputImportOrder6 {
}
