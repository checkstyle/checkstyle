/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.net.URL;

public class InputUnusedImportsBug {
   //same as a class name
   private static String URL = "This is a String object";

   public InputUnusedImportsBug() throws Exception {
       URL url = new URL("file://this.is.a.url.object");
   }
}
