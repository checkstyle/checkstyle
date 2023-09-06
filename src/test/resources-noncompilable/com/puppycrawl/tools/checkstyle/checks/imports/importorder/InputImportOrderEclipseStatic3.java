/*
ImportOrder
option = top
groups = /^javax?\\./,org
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
import static io.netty.handler.codec.http.HttpConstants.COLON;
import static io.netty.handler.codec.http.HttpHeaders.addHeader;
import static io.netty.handler.codec.http.HttpHeaders.setHeader;
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // violation 'Wrong order for .* import.'

public class InputImportOrderEclipseStatic3 {
}
