package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;

public class InputXpathUnusedTryResourceShouldBeUnnamedNested {
    void test() {
      try (AutoCloseable a = lock()) {
        System.out.println(a);
        try (AutoCloseable b = lock()) { } // warn
        catch (Exception e) {}
      }
      catch (Exception e) {}
      try (AutoCloseable _ = lock()) {}
      catch (Exception e) {}
    }
    AutoCloseable lock() {
      return null;
    }
}
