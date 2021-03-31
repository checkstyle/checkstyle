//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = top
 * groups = {/^javax?\./, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = false
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = true
 */
import static io.netty.handler.codec.http.HttpConstants.COLON; // ok
import static io.netty.handler.codec.http.HttpHeaders.addHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.setHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // violation

public class InputImportOrderEclipseStatic3 {
}
