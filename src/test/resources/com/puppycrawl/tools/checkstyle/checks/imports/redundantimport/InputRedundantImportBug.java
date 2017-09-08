package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import java.net.URL;

public class InputRedundantImportBug {
   //same as a class name
   private static String URL = "This is a String object";

   public InputRedundantImportBug() throws Exception {
       URL url = new URL("file://this.is.a.url.object");
   }
}
