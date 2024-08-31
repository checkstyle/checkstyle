package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

class InputFormattedEmptyBlocksAndCatchBlocks {
  static {
  }

  public void fooMethod() {
    InputEmptyBlocksAndCatchBlocks r = new InputEmptyBlocksAndCatchBlocks();
    int a = 1;
    if (a == 1) {}
    // 3 violations above:
    //  'Empty if block.'
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    char[] s = {'1', '2'};
    int index = 2;
    if (doSideEffect() == 1) {}
    // 3 violations above:
    //  'Empty if block.'
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    Io in = new Io();
    while ((r = in.read()) != null) {}
    for (; index < s.length && s[index] != 'x'; index++) {}
    if (a == 1) {
    } else { // violation above 'Empty if block.'
      System.identityHashCode("a");
    }
    do {} while (a == 1);
    switch (a) {
    }
    // 2 violations 2 lines above:
    //  'switch without "default" clause.'
    //  'Empty switch block.'
    int[] z = {};
  }

  public int doSideEffect() {
    return 1;
  }

  public void emptyMethod() {}
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
      // 3 violations above:
      //  'Empty if block.'
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      char[] s = {'1', '2'};
      int index = 2;
      if (doSideEffect() == 1) {}
      // 3 violations above:
      //  'Empty if block.'
      //  ''{' is not followed by whitespace.'
      //  ''}' is not preceded with whitespace.'
      Io in = new Io();
      while ((r = in.read()) != null) {}
      for (; index < s.length && s[index] != 'x'; index++) {}
      if (a == 1) {
      } else { // violation above 'Empty if block.'
        System.identityHashCode("a");
      }
      do {} while (a == 1);
      switch (a) {
      }
      // 2 violations 2 lines above:
      //  'switch without "default" clause.'
      //  'Empty switch block.'
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
            // 3 violations above:
            //  'Empty if block.'
            //  ''{' is not followed by whitespace.'
            //  ''}' is not preceded with whitespace.'
            char[] s = {'1', '2'};
            int index = 2;
            if (doSideEffect() == 1) {}
            // 3 violations above:
            //  'Empty if block.'
            //  ''{' is not followed by whitespace.'
            //  ''}' is not preceded with whitespace.'
            Io in = new Io();
            while ((r = in.read()) != null) {}
            for (; index < s.length && s[index] != 'x'; index++) {}
            if (a == 1) {
            } else { // violation above 'Empty if block.'
              System.identityHashCode("a");
            }
            do {} while (a == 1);
            switch (a) {
            }
            // 2 violations 2 lines above:
            //  'switch without "default" clause.'
            //  'Empty switch block.'
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
    } // violation above 'Empty else block.'

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
      // violation below 'Empty if block.'
    } else if (a != 1) {
    } else {
    } // violation above 'Empty else block.'

    if (a == 1) {
    } else if (a != 1) { // violation above 'Empty if block.'
      /*ignore*/
    } else {
    } // violation above 'Empty else block.'

    // violation below 'Empty if block.'
    if (a == 1) {
    } else if (a != 1) {
    } else { // violation above 'Empty if block.'
      /*ignore*/
    }
  }

  class NewInner {

    void foo() {
      int a = 1;

      if (a == 1) {
        System.identityHashCode("a");
      } else {
      } // violation above 'Empty else block.'

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
        // violation below 'Empty if block.'
      } else if (a != 1) {
      } else {
      } // violation above 'Empty else block.'

      // violation below 'Empty if block.'
      if (a == 1) {
      } else if (a != 1) {
        /*ignore*/
      } else {
      } // violation above 'Empty else block.'

      // violation below 'Empty if block.'
      if (a == 1) {
      } else if (a != 1) {
      } else { // violation above 'Empty if block.'
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
            } // violation above 'Empty else block.'

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
              // violation below 'Empty if block.'
            } else if (a != 1) {
            } else {
            } // violation above 'Empty else block.'

            if (a == 1) {
            } else if (a != 1) { // violation above 'Empty if block.'
              /*ignore*/
            } else {
            } // violation above 'Empty else block.'

            // violation below 'Empty if block.'
            if (a == 1) {
            } else if (a != 1) {
            } else { // violation above 'Empty if block.'
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
          } // violation above 'Empty finally block.'
        }
      };
}
