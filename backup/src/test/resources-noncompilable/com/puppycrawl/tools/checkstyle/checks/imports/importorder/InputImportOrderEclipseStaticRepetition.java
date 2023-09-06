/*
ImportOrder
option = top
groups = java, org
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = true
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate; // ok

public class InputImportOrderEclipseStaticRepetition {
}
