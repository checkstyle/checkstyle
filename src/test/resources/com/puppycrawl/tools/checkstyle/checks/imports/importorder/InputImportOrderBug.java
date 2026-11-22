/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.net.URL;

public class InputImportOrderBug {
   //same as a class name
   private static String URL = "This is a String object";

   public InputImportOrderBug() throws Exception {
       URL url = new URL("file://this.is.a.url.object");
   }
}
