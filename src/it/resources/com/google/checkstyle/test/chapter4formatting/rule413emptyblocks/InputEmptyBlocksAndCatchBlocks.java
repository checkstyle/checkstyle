package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputEmptyBlocksAndCatchBlocks {
  static {
  }

  static {}

  static { } // violation 'Empty blocks should have no spaces. .* may only be represented as {}'

  public void fooMethod() {
    InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
    int a = 1;
    if (a == 1) { }
    // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'
    char[] s = {'1', '2'};
    int index = 2;
    if (doSideEffect() == 1) { }
    // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'
    Io in = new Io();
    while ((r = in.read()) != null) {}
    for (; index < s.length && s[index] != 'x'; index++) {}
    if (a == 1) {
    } else {
      System.identityHashCode("a");
    }
    do {} while (a == 1);
    switch (a) {
    }
    // violation 2 lines above 'switch without "default" clause.'
    int[] z = {};
  }

  public int doSideEffect() {
    return 1;
  }

  public void emptyMethod() {}

  void foo() throws Exception {
    int a = 90;

    if (a == 1) {
    } else {} // false-negative until #15791

    if (a == 1) {
    } else { }
    // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'

    try (MyResource r = new MyResource()) { }
    // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'
    try (MyResource r = new MyResource()) {}
    // violation below ''}' at column 74 should be alone on a line.'
    try (MyResource r = new MyResource()) {} catch (Exception expected) {}

    try (MyResource r = new MyResource()) {} catch (Exception expected) { }
    // 2 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    ''}' at column 75 should be alone on a line.'
    try (MyResource r = new MyResource()) {

    } catch (Exception expected) {}
    // violation above ''}' at column 35 should be alone on a line.'
    try (MyResource r = new MyResource()) {

    } catch (Exception expected) { }
    // 2 violations above:
    //                    'Empty blocks should have no spaces.'
    //                    ''}' at column 36 should be alone on a line.'

    try (MyResource r = new MyResource()) {;}
    // 3 violations above:
    //  'WhitespaceAround: '{' is not followed by whitespace.'
    //  ''{' at column 43 should have line break after.'
    //  'WhitespaceAround: '}' is not preceded with whitespace.'
  }

  /** some. */
  public class MyResource implements AutoCloseable {
    /** some. */
    @Override
    public void close() throws Exception {
      System.out.println("Closed MyResource");
    }
  }
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
      if (a == 1) { }
      // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'
      char[] s = {'1', '2'};
      int index = 2;
      if (doSideEffect() == 1) { }
      // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'
      Io in = new Io();
      while ((r = in.read()) != null) {}
      for (; index < s.length && s[index] != 'x'; index++) {}
      if (a == 1) {
      } else {
        System.identityHashCode("a");
      }
      do {} while (a == 1);
      switch (a) {
      }
      // violation 2 lines above 'switch without "default" clause.'
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
            if (a == 1) { }
            // violation above 'Empty blocks should have no spaces. .* only be represented as {}'
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) { }
            // violation above 'Empty blocks should have no spaces. .* only be represented as {}'
            Io in = new Io();
            while ((r = in.read()) != null) {}
            for (; index < s.length && s[index] != 'x'; index++) {}
            if (a == 1) {
            } else {
              System.identityHashCode("a");
            }
            do {} while (a == 1);
            switch (a) {
            }
            // violation 2 lines above 'switch without "default" clause.'
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
    }

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
    } else if (a != 1) {
    } else {
    }

    if (a == 1) {
    } else if (a != 1) {
      /*ignore*/
    } else {
    }

    if (a == 1) {
    } else if (a != 1) {
    } else {
      /*ignore*/
    }
  }

  class NewInner {

    void foo() {
      int a = 1;

      if (a == 1) {
        System.identityHashCode("a");
      } else {
      }

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
      } else if (a != 1) {
      } else {
      }

      if (a == 1) {
      } else if (a != 1) {
        /*ignore*/
      } else {
      }

      if (a == 1) {
      } else if (a != 1) {
      } else {
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
            }

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
            } else if (a != 1) {
            } else {
            }

            if (a == 1) {
            } else if (a != 1) {
              /*ignore*/
            } else {
            }

            if (a == 1) {
            } else if (a != 1) {
            } else {
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
        // violation below 'Empty catch block.'
      } catch (Exception e) {
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
            // violation below 'Empty catch block.'
          } catch (Exception e) {
          } finally {
          }
        }
      };
}
