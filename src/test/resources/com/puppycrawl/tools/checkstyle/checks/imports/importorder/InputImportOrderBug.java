/*
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="option" value="top"/>
      <property name="groups" value="java, javax, org, com"/>
    </module>
  </module>
</module>
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
