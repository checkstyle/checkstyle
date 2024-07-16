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
    } // ok
    finally {
      /* ignore */
    } // ok
  }

  void foo2() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception e) {
    } // violation above 'Empty catch block.'
    finally {
    } // violation above 'Empty finally block.'
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
      } // ok
      finally {
        /* ignore */
      } // ok
    }

    void foo2() {
      try {
        if (!flag) {
          doSm();
        }
      } catch (Exception e) {
      } // violation above 'Empty catch block.'
      finally {
      } // violation above 'Empty finally block.'
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
          } // ok
          finally {
            /* ignore */
          } // ok
        }

        void foo2() {
          try {
            if (!flag) {
              doSm();
            }
          } catch (Exception e) {
          } // violation above 'Empty catch block.'
          finally {
          } // violation above 'Empty finally block.'
        }
      };
}

interface InterfaceEndingWithSemiColon2 {
  public void fooEmpty();
}
; // ok until #7541
