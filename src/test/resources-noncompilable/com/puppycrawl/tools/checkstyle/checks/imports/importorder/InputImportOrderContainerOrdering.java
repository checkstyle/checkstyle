/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = true
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON; // ok
import static io.netty.handler.codec.http.HttpHeaders.addHeader; // ok
import static io.netty.handler.codec.http.HttpHeaders.setHeader; // ok
import static io.netty.handler.Codec.HTTP.HttpHeaders.tmp.same; // violation
import static io.netty.handler.Codec.HTTP.HttpHeaders.TKN.same; // violation

public class InputImportOrderContainerOrdering {

}
