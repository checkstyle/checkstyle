///////////////////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputEmptyFinallyBlocks {
  boolean flag;

  void doSm() {}

  void foo() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception e) {
      /* ignore */
      // violation below ''}' at column 5 should be alone on a line.'
    } finally { // ok
      /* ignore */
    } // ok
  }

  void foo2() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception e) {
      // violation below ''}' at column 5 should be alone on a line.'
    } finally {
    }
  }

  class Inner {
    boolean flag;

    void doSm() {}

    void foo() {
      try {
        if (!flag) {
          doSm();
        }
      } catch (Exception e) {
        /* ignore */
        // violation below ''}' at column 7 should be alone on a line.'
      } finally { // ok
        /* ignore */
      } // ok
    }

    void foo2() {
      try {
        if (!flag) {
          doSm();
        }
      } catch (Exception e) {
        // violation below ''}' at column 7 should be alone on a line.'
      } finally {
      }
    }
  }

  Inner anon =
      new Inner() {
        boolean flag;

        void doSm() {}

        void foo() {
          try {
            if (!flag) {
              doSm();
            }
          } catch (Exception e) {
            /* ignore */
            // violation below ''}' at column 11 should be alone on a line.'
          } finally { // ok
            /* ignore */
          } // ok
        }

        void foo2() {
          try {
            if (!flag) {
              doSm();
            }
          } catch (Exception e) {
            // violation below ''}' at column 11 should be alone on a line.'
          } finally {
          }
        }
      };
}
