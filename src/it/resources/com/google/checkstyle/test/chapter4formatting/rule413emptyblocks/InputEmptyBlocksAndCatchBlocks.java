package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputEmptyBlocksAndCatchBlocks {
  static {
  }

  public void fooMethod() {
    InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
    int a = 1;
    if (a == 1) { } // no warn 'Empty if block.' until #15338
    char[] s = {'1', '2'};
    int index = 2;
    if (doSideEffect() == 1) { } // no warn 'Empty if block.' until #15338
    Io in = new Io();
    while ((r = in.read()) != null) {}
    for (; index < s.length && s[index] != 'x'; index++) {}
    if (a == 1) {
    } else { // no warn above 'Empty if block.' until #15338
      System.identityHashCode("a");
    }
    do {} while (a == 1);
    switch (a) {
    }
    // 1 violations 2 lines above:
    //  'switch without "default" clause.'
    //  no warn 'Empty switch block.' until #15338
    int[] z = {};
  }

  public int doSideEffect() {
    return 1;
  }

  public void emptyMethod() {}
}

// violation below 'Top-level class Io has to reside in its own source file.'
class Io {
  public InputEmptyBlocksAndCatchBlocks read() {
    return new InputEmptyBlocksAndCatchBlocks();
  }
}

// violation below 'Top-level class Empty has to reside in its own source file.'
class Empty {}

// violation below 'Top-level class EmptyImplement has to reside in its own source file.'
interface EmptyImplement {}

// violation below 'Top-level class WithInner has to reside in its own source file.'
class WithInner {
  static {
  }

  public void emptyMethod() {}

  public int doSideEffect() {
    return 1;
  }

  class Inner {
    private void withEmpty() {
      InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
      int a = 1;
      if (a == 1) { } // no warn 'Empty if block.' until #15338 until #15338
      char[] s = {'1', '2'};
      int index = 2;
      if (doSideEffect() == 1) { } // no warn 'Empty if block.' until #15338
      Io in = new Io();
      while ((r = in.read()) != null) {}
      for (; index < s.length && s[index] != 'x'; index++) {}
      if (a == 1) {
      } else { // no warn above 'Empty if block.' until #15338
        System.identityHashCode("a");
      }
      do {} while (a == 1);
      switch (a) {
      }
      // 1 violations 2 lines above:
      //  'switch without "default" clause.'
      //  no warn 'Empty switch block.' until #15338
      int[] z = {};
    }
  }
}

// violation below 'Top-level class WithAnon has to reside in its own source file.'
class WithAnon {
  interface AnonWithEmpty {
    public void fooEmpty();
  }

  void method() {
    AnonWithEmpty foo =
        new AnonWithEmpty() {

          public void emptyMethod() {}

          public void fooEmpty() {
            InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
            int a = 1;
            if (a == 1) { } // no warn 'Empty if block.' until #15338
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) { } // no warn 'Empty if block.' until #15338
            Io in = new Io();
            while ((r = in.read()) != null) {}
            for (; index < s.length && s[index] != 'x'; index++) {}
            if (a == 1) {
            } else { // no warn above 'Empty if block.' until #15338
              System.identityHashCode("a");
            }
            do {} while (a == 1);
            switch (a) {
            }
            // 1 violations 2 lines above:
            //  'switch without "default" clause.'
            //  now warn 'Empty switch block.' until #15338
            int[] z = {};
          }

          public int doSideEffect() {
            return 1;
          }
        };
  }
}

// violation below 'Top-level class NewClass has to reside in its own source file.'
class NewClass {

  void foo() {
    int a = 1;

    if (a == 1) {
      System.identityHashCode("a");
    } else {
    } // no warn above 'Empty else block.' until #15338

    if (a == 1) {
      System.identityHashCode("a");
    } else {
      /*ignore*/
    }

    if (a == 1) {
      /*ignore*/
    } else {
      System.identityHashCode("a");
    }

    if (a == 1) {
      System.identityHashCode("a");
    } else if (a != 1) {
      /*ignore*/
    } else {
      /*ignore*/
    }

    if (a == 1) {
      /*ignore*/
    } else if (a != 1) {
      System.identityHashCode("a");
    } else {
      /*ignore*/
    }

    if (a == 1) {
      /*ignore*/
    } else if (a != 1) {
      /*ignore*/
    } else {
      System.identityHashCode("a");
    }

    if (a == 1) {
      /*ignore*/
    } else if (a != 1) {
      /*ignore*/
    } else {
      /*ignore*/
    }

    if (a == 1) {
      /*ignore*/
      // no warn below 'Empty if block.' until #15338
    } else if (a != 1) {
    } else {
    } // no warn above 'Empty else block.' until #15338

    if (a == 1) {
    } else if (a != 1) { // no warn above 'Empty if block.' until #15338
      /*ignore*/
    } else {
    } // no warn above 'Empty else block.' until #15338

    // no warn below 'Empty if block.' until #15338
    if (a == 1) {
    } else if (a != 1) {
    } else { // no warn above 'Empty if block.' until #15338
      /*ignore*/
    }
  }

  class NewInner {

    void foo() {
      int a = 1;

      if (a == 1) {
        System.identityHashCode("a");
      } else {
      } // no warn above 'Empty else block.' until #15338

      if (a == 1) {
        System.identityHashCode("a");
      } else {
        /*ignore*/
      }

      if (a == 1) {
        /*ignore*/
      } else {
        System.identityHashCode("a");
      }

      if (a == 1) {
        System.identityHashCode("a");
      } else if (a != 1) {
        /*ignore*/
      } else {
        /*ignore*/
      }

      if (a == 1) {
        /*ignore*/
      } else if (a != 1) {
        System.identityHashCode("a");
      } else {
        /*ignore*/
      }

      if (a == 1) {
        /*ignore*/
      } else if (a != 1) {
        /*ignore*/
      } else {
        System.identityHashCode("a");
      }

      if (a == 1) {
        /*ignore*/
      } else if (a != 1) {
        /*ignore*/
      } else {
        /*ignore*/
      }

      if (a == 1) {
        /*ignore*/
        // no warn below 'Empty if block.' until #15338
      } else if (a != 1) {
      } else {
      } // no warn above 'Empty else block.' until #15338

      // no warn below 'Empty if block.' until #15338
      if (a == 1) {
      } else if (a != 1) {
        /*ignore*/
      } else {
      } // no warn above 'Empty else block.' until #15338

      // no warn below 'Empty if block.' until #15338
      if (a == 1) {
      } else if (a != 1) {
      } else { // no warn above 'Empty if block.' until #15338
        /*ignore*/
      }
    }

    NewInner anon =
        new NewInner() {

          void foo() {
            int a = 1;

            if (a == 1) {
              System.identityHashCode("a");
            } else {
            } // no warn above 'Empty else block.' until #15338

            if (a == 1) {
              System.identityHashCode("a");
            } else {
              /*ignore*/
            }

            if (a == 1) {
              /*ignore*/
            } else {
              System.identityHashCode("a");
            }

            if (a == 1) {
              System.identityHashCode("a");
            } else if (a != 1) {
              /*ignore*/
            } else {
              /*ignore*/
            }

            if (a == 1) {
              /*ignore*/
            } else if (a != 1) {
              System.identityHashCode("a");
            } else {
              /*ignore*/
            }

            if (a == 1) {
              /*ignore*/
            } else if (a != 1) {
              /*ignore*/
            } else {
              System.identityHashCode("a");
            }

            if (a == 1) {
              /*ignore*/
            } else if (a != 1) {
              /*ignore*/
            } else {
              /*ignore*/
            }

            if (a == 1) {
              /*ignore*/
              // no warn below 'Empty if block.' until #15338
            } else if (a != 1) {
            } else {
            } // no warn above 'Empty else block.' until #15338

            if (a == 1) {
            } else if (a != 1) { // no warn above 'Empty if block.' until #15338
              /*ignore*/
            } else {
            } // no warn above 'Empty else block.' until #15338

            // no warn below 'Empty if block.' until #15338
            if (a == 1) {
            } else if (a != 1) {
            } else { // no warn above 'Empty if block.' until #15338
              /*ignore*/
            }
          }
        };
  }
}

// violation below 'Top-level class Example has to reside in its own source file.'
class Example {

  void doNothing() {}

  void doNothingElse() {}
}

// violation below 'Top-level class TestingEmptyBlockCatch has to reside in its own source file.'
class TestingEmptyBlockCatch {
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
      // violation below 'Empty catch block.'
    } catch (Exception e) {
    } finally {
    } // no warn above 'Empty finally block.' until #15338
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
        // violation below 'Empty catch block.'
      } catch (Exception e) {
      } finally {
      } // no warn above 'Empty finally block.' until #15338
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
            // violation below 'Empty catch block.'
          } catch (Exception e) {
          } finally {
          } // no warn above 'Empty finally block.' until #15338
        }
      };
}
