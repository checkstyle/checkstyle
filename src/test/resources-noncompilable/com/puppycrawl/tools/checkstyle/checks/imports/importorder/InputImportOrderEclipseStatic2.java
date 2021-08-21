/*
ImportOrder
option = top
groups = /^javax?\\./,org
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = false
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON; // ok
import static io.netty.handler.codec.http.HttpHeaders.addHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.setHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate; // violation
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // ok

public class InputImportOrderEclipseStatic2 {
}
