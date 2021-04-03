//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE; // ok
import static io.netty.handler.codec.http.HttpHeaders.Names.addDate; // ok

/*
 * Config:
 * option = top
 * groups = {java, org}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = true
 */
public class InputImportOrderEclipseStaticRepetition {
}
