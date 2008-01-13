package com.puppycrawl.tools.checkstyle.imports;

import java.net.URL;

public class InputImportBug {

   private static String URL = "This is a String object";

   public InputImportBug() throws Exception {
       URL url = new URL("file://this.is.a.url.object");
   }
}
