/*
MatchXpath
query = //LITERAL_CATCH//METHOD_CALL[.//IDENT[@text = 'printStackTrace']]/..
message.matchxpath.match = printStackTrace() method calls are forbidden


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.io.IOException;

public class InputMatchXpathForbidPrintStackTrace {
   public void test() {
      try {
         throw new IOException();
      } catch (IOException e) {
         e.printStackTrace(); // violation
      }
   }

   public void test2() {
      try {
         throw new IOException();
      } catch (IOException e) {
      }
   }
}
