/*
UnusedTryResourceShouldBeUnnamed


*/

// non-compiled with javac: Compilable with Java25
package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;

public class InputXpathUnusedTryResourceShouldBeUnnamedSimple {
    void test() {
      try (AutoCloseable a = lock()) { } // violation 'Unused try resource .* should be unnamed\.'
      catch (Exception e) {}

      try (AutoCloseable _ = lock()) {}
      catch (Exception e) {}
    }
    AutoCloseable lock() {
      return null;
    }
}
