/*
UnusedTryResourceShouldBeUnnamed

*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamedForVariable implements AutoCloseable{
 private static int closeCount = 0;
   public static void main(String... args) {
     InputUnusedTryResourceShouldBeUnnamedForVariable v =
            new InputUnusedTryResourceShouldBeUnnamedForVariable();
     // violation below, 'Unused try resource 'v' should be unnamed'
     try (v) {
        assertCloseCount(0);
     }
     // violation below, 'Unused try resource 'v' should be unnamed'
     try (/**@deprecated*/v) {
        assertCloseCount(1);
     }
     try (v.finalWrapper.finalField) {
        assertCloseCount(2);
     }
     catch (Exception ex) {}
     try (new InputUnusedTryResourceShouldBeUnnamedForVariable() { }.finalWrapper.finalField) {
         assertCloseCount(3);
     } catch (Exception ex) {}
     try ((args.length > 0 ? v :
          new InputUnusedTryResourceShouldBeUnnamedForVariable()).finalWrapper.finalField) {
        assertCloseCount(4);
     } catch (Exception ex) {}
     try {
        throw new CloseableException();
     } catch (CloseableException ex) {
         // violation below, 'Unused try resource 'ex' should be unnamed'
         try (ex) {
             assertCloseCount(5);
         }
     }
     assertCloseCount(6);
     // null test cases
     InputUnusedTryResourceShouldBeUnnamedForVariable n = null;
     // violation below, 'Unused try resource 'n' should be unnamed'
     try (n) {}
       // violation below, 'Unused try resource 'n' should be unnamed'
       try (n) {
         throw new Exception();
       } catch (Exception e) {}
       assertCloseCount(6);
       // initialization exception
       InputUnusedTryResourceShouldBeUnnamedForVariable i1 =
           new InputUnusedTryResourceShouldBeUnnamedForVariable();
       // violation below, 'Unused try resource 'i1' should be unnamed'
       try (i1; InputUnusedTryResourceShouldBeUnnamedForVariable i2 =
       // violation above, 'Unused try resource 'i2' should be unnamed'
               new InputUnusedTryResourceShouldBeUnnamedForVariable(true)) {}
       catch (Exception e) {}
       assertCloseCount(7);

       // multiple closures
       InputUnusedTryResourceShouldBeUnnamedForVariable m1 =
             new InputUnusedTryResourceShouldBeUnnamedForVariable();
       try (m1; InputUnusedTryResourceShouldBeUnnamedForVariable m2 = m1;
          InputUnusedTryResourceShouldBeUnnamedForVariable m3 = m2;) {}
       // violation above, 'Unused try resource 'm3' should be unnamed'
       assertCloseCount(10);

       // aliasing of variables keeps equality (bugs 6911256 6964740)
       InputUnusedTryResourceShouldBeUnnamedForVariable a1 =
             new InputUnusedTryResourceShouldBeUnnamedForVariable();
       try (a1; InputUnusedTryResourceShouldBeUnnamedForVariable a2 = a1;) {
         if (a2 != a2)
           throw new RuntimeException("Unexpected inequality.");
       }
       assertCloseCount(12);
       // anonymous class implementing AutoCloseable as variable in twr
       AutoCloseable a = new AutoCloseable() {
         public void close() { };
       };
       // violation below, 'Unused try resource 'a' should be unnamed'
       try (a) {}
       catch (Exception e) {}
    }

    static void assertCloseCount(int expectedCloseCount) {
      if (closeCount != expectedCloseCount)
        throw new RuntimeException("bad closeCount: " + closeCount +
                                 "; expected: " + expectedCloseCount);
    }
    public void close() {
      closeCount++;
    }
    final FinalWrapper finalWrapper = new FinalWrapper();
    static class FinalWrapper {
      public final AutoCloseable finalField = new AutoCloseable() {
        @Override
        public void close() throws Exception {
          closeCount++;
        }
      };
    }
    static class CloseableException extends Exception implements AutoCloseable {
      @Override
      public void close() {
        closeCount++;
      }
    }
    public InputUnusedTryResourceShouldBeUnnamedForVariable(boolean throwException) {
      if (throwException)
        throw new RuntimeException("Initialization exception");
    }
    public InputUnusedTryResourceShouldBeUnnamedForVariable() {
      this(false);
    }
}
