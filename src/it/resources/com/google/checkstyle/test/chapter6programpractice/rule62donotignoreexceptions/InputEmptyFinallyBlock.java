///
// Test case file for checkstyle.
// Created: 2001
///

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
    } finally {
      /* ignore */
    }
  }

  void foo2() {
    try {
      if (!flag) {
        doSm();
      }
    } catch (Exception e) {
      /* ignore */
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
      } finally {
        /* ignore */
      }
    }

    void foo2() {
      try {
        if (!flag) {
          doSm();
        }
      } catch (Exception e) {
        /* ignore */
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
          } finally {
            /* ignore */
          }
        }

        void foo2() {
          try {
            if (!flag) {
              doSm();
            }
          } catch (Exception e) {
            /* ignore */
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
