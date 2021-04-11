//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON; // ok
import static io.netty.handler.codec.http.HttpHeaders.addHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.setHeader; // ok
import static io.netty.handler.Codec.HTTP.HttpHeaders.tmp.same; // violation
import static io.netty.handler.Codec.HTTP.HttpHeaders.TKN.same; // violation

/*
 * Config:
 * option = under
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = true
 */
public class InputImportOrderContainerOrdering {

}
