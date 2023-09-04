/*
ImportOrder
option = top
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = false
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = true
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON;
import static io.netty.handler.codec.http.HttpHeaders.addHeader;
import static io.netty.handler.codec.http.HttpHeaders.setHeader;
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate;
import static io.netty.handler.codec.http.HttpHeaders.NAMES.DATE;

public class InputImportOrderEclipseStaticCaseSensitive {
}
