package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputFormattedEmptyBlocksAndCatchBlocks {
  static {
  }

  static {
  }

  static {
  }

  public void fooMethod() {
    InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
    int a = 1;
    if (a == 1) {}
    char[] s = {'1', '2'};
    int index = 2;
    if (doSideEffect() == 1) {}
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
    } else {
    }

    if (a == 1) {
    } else {
    }

    try (MyResource r = new MyResource()) {}
    try (MyResource r = new MyResource()) {}
    try (MyResource r = new MyResource()) {
      // violation below 'Empty catch block.'
    } catch (Exception expected) {
    }
    try (MyResource r = new MyResource()) {
      // violation below 'Empty catch block.'
    } catch (Exception expected) {
    }
    try (MyResource r = new MyResource()) {
      // violation below 'Empty catch block.'
    } catch (Exception expected) {
    }
    try (MyResource r = new MyResource()) {
      // violation below 'Empty catch block.'
    } catch (Exception expected) {
    }
    try (MyResource r = new MyResource()) {
      ;
    }
  }

  /** Some. */
  public class MyResource implements AutoCloseable {
    /** Some. */
    @Override
    public void close() throws Exception {
      System.out.println("Closed MyResource");
    }
  }
}

// violation below 'Top-level class ExtraIo has to reside in its own source file.'
class ExtraIo {
  public InputEmptyBlocksAndCatchBlocks read() {
    return new InputEmptyBlocksAndCatchBlocks();
  }
}

// violation below 'Top-level class ExtraEmpty has to reside in its own source file.'
class ExtraEmpty {}

// violation below 'Top-level class ExtraEmptyImplement has to reside in its own source file.'
interface ExtraEmptyImplement {}

// violation below 'Top-level class ExtraWithInner has to reside in its own source file.'
class ExtraWithInner {
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
      if (a == 1) {}
      char[] s = {'1', '2'};
      int index = 2;
      if (doSideEffect() == 1) {}
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

// violation below 'Top-level class ExtraWithAnon has to reside in its own source file.'
class ExtraWithAnon {
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
            if (a == 1) {}
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) {}
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

// violation below 'Top-level class ExtraNewClass has to reside in its own source file.'
class ExtraNewClass {

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

// violation below 'Top-level class ExtraExample has to reside in its own source file.'
class ExtraExample {

  void doNothing() {}

  void doNothingElse() {}
}

// violation below '.* ExtraTestingEmptyBlockCatch has to reside in its own source file.'
class ExtraTestingEmptyBlockCatch {
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
