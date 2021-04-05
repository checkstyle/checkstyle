//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON; // ok
import static io.netty.handler.codec.http.HttpHeaders.addHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.setHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate; // ok
import static io.netty.handler.codec.http.HttpHeaders.NAMES.DATE; // ok

/*
 * Config:
 * option = top
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = false
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = true
 */
public class InputImportOrderEclipseStaticCaseSensitive {
}
