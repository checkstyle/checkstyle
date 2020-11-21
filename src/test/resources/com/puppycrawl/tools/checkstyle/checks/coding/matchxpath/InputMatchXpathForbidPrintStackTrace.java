package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.io.IOException;

/* Config:
 *
 * default
 */
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
         // ok
      }
   }
}
