package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;
// non-compiled with javac: Compilable with Java25
public class InputXpathUnusedTryResourceShouldBeUnnamedSimple {
    void test() {
      try (AutoCloseable a = lock()) { } // warn
      catch (Exception e) {}

      try (AutoCloseable _ = lock()) {}
      catch (Exception e) {}
    }
    AutoCloseable lock() {
      return null;
    }
}

