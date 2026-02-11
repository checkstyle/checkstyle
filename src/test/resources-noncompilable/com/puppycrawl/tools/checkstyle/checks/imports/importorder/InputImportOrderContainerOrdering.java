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

// non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static io.netty.handler.codec.http.HttpConstants.COLON;
import static io.netty.handler.codec.http.HttpHeaders.addHeader;
import static io.netty.handler.codec.http.HttpHeaders.setHeader;
import static io.netty.handler.Codec.HTTP.HttpHeaders.tmp.same; // violation 'Wrong lexicographical order for 'io.netty.handler.Codec.HTTP.HttpHeaders.tmp.same' import. Should be before 'io.netty.handler.codec.http.HttpHeaders.setHeader'.'
import static io.netty.handler.Codec.HTTP.HttpHeaders.TKN.same; // violation 'Wrong lexicographical order for 'io.netty.handler.Codec.HTTP.HttpHeaders.TKN.same' import. Should be before 'io.netty.handler.Codec.HTTP.HttpHeaders.tmp.same'.'

public class InputImportOrderContainerOrdering {

}
