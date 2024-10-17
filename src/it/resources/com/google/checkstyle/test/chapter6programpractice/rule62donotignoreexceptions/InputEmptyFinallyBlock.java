///////////////////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter6programpractice.rule62donotignoreexceptions;

class InputEmptyFinallyBlock {
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
      /* ignore */
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
        /* ignore */
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
            /* ignore */
            // violation below ''}' at column 11 should be alone on a line.'
          } finally {
          }
        }
      };

  void bar1() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception expected) {
    }
  }

  void bar2() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception expected) {
    }
  }
}
