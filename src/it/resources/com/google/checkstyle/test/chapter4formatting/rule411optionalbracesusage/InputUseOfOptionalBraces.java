package com.google.checkstyle.test.chapter4formatting.rule411optionalbracesusage;

class InputUseOfOptionalBraces {
  /**
   * Some javadoc..
   *
   * @return helper func *
   */
  boolean condition() {
    return false;
  }

  /** Test do/while loops. */
  void testDoWhile() {
    // Valid
    do {
      testDoWhile();
    } while (condition());

    // Invalid
    // violation below ''do' construct must use '{}'s.'
    do testDoWhile();
    while (condition());
  }

  /** Test while loops. */
  void testWhile() {
    // Valid
    while (condition()) {
      testWhile();
    }

    // Invalid
    // violation 2 lines below ''while' construct must use '{}'s.'
    // violation 3 lines below ''while' construct must use '{}'s.'
    while (condition())
      ;
    while (condition()) testWhile();
    // violation 2 lines below ''while' construct must use '{}'s.'
    // violation below ''if' construct must use '{}'s.'
    while (condition()) if (condition()) testWhile();
    String k = "testing";
    // violation below ''if' construct must use '{}'s.'
    if (k != null) k = "ss";
  }

  /** Test for loops. */
  void testFor() {
    // Valid
    for (int i = 1; i < 5; i++) {
      testFor();
    }

    // Invalid
    // violation below ''for' construct must use '{}'s.'
    for (int i = 1; i < 5; i++)
      ;
    // violation below ''for' construct must use '{}'s.'
    for (int i = 1; i < 5; i++) testFor();
    // violation 2 lines below ''for' construct must use '{}'s.'
    // violation below ''if' construct must use '{}'s.'
    for (int i = 1; i < 5; i++) if (i > 2) testFor();
  }

  /** Test if constructs. */
  public void testIf() {
    // Valid
    if (condition()) {
      testIf();
    } else if (condition()) {
      testIf();
    } else {
      testIf();
    }

    // Invalid
    // violation below ''if' construct must use '{}'s.'
    if (condition())
      ;
    // violation below ''if' construct must use '{}'s.'
    if (condition()) testIf();
    // violation 2 lines below ''if' construct must use '{}'s.'
    // violation 2 lines below ''else' construct must use '{}'s.'
    if (condition()) testIf();
    else testIf();
    // violation below ''if' construct must use '{}'s.'
    if (condition()) testIf();
    else {
      testIf();
    }
    if (condition()) {
      testIf();
      // violation below ''else' construct must use '{}'s.'
    } else testIf();
    // violation 2 lines below ''if' construct must use '{}'s.'
    // violation below ''if' construct must use '{}'s.'
    if (condition()) if (condition()) testIf();
  }

  void whitespaceAfterSemi() {
    // reject
    int i = 1;
    int j = 2;

    // accept
    for (; ; ) {}
  }

  /** Empty constructor block. */
  public InputUseOfOptionalBraces() {}

  /** Empty method block. */
  public void emptyImplementation() {}

  /** Testing Lambdas. */
  static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");

  static Runnable r3 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
  static Runnable r4 =
      () -> {
        String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
      };
  static Runnable r5 = () -> {};

  class EmptyBlocks {
    boolean flag = true;
    int[] abc = {
      1, 2, 3, 4,
    };

    void foo() {
      // violation below ''while' construct must use '{}'s.'
      while (flag)
        ;
      while (flag) {}
      while (flag) {
        /*foo*/
      }
      // violation below ''do' construct must use '{}'s.'
      do
        ;
      while (flag);
      do {} while (flag);
      do {
        /*foo*/
      } while (flag);
      // violation below ''if' construct must use '{}'s.'
      if (flag)
        ;
      if (flag) {
        /*foo*/
      }
      if (flag) {
        /*foo*/
      }
      // violation 2 lines below ''if' construct must use '{}'s.'
      // violation 3 lines below ''else' construct must use '{}'s.'
      if (flag)
        ;
      else
        ;
      if (flag) {
      } else {
      }
      if (flag) {
        /*foo*/
      } else {
        /*foo*/
      }
      // violation below ''for' construct must use '{}'s.'
      for (int i = 0; i < 10; i++)
        ;
      for (int i = 0; i < 10; i++) {}
      for (int i = 0; i < 10; i++) {
        /*foo*/
      }
      // violation below ''for' construct must use '{}'s.'
      for (int b : abc)
        ;
      for (int b : abc) {}
      for (int b : abc) {
        /*foo*/
      }
    }

    class InnerEmptyBlocks {
      boolean flag = true;
      int[] abc = {
        1, 2, 3, 4,
      };

      void foo() {
        // violation below ''while' construct must use '{}'s.'
        while (flag)
          ;
        while (flag) {}
        while (flag) {
          /*foo*/
        }
        // violation below ''do' construct must use '{}'s.'
        do
          ;
        while (flag);
        do {} while (flag);
        do {
          /*foo*/
        } while (flag);
        // violation below ''if' construct must use '{}'s.'
        if (flag)
          ;
        if (flag) {
          /*foo*/
        }
        if (flag) {
          /*foo*/
        }
        // violation 2 lines below ''if' construct must use '{}'s.'
        // violation 3 lines below ''else' construct must use '{}'s.'
        if (flag)
          ;
        else
          ;
        if (flag) {
        } else {
        }
        if (flag) {
          /*foo*/
        } else {
          /*foo*/
        }
        // violation below ''for' construct must use '{}'s.'
        for (int i = 0; i < 10; i++)
          ;
        for (int i = 0; i < 10; i++) {}
        for (int i = 0; i < 10; i++) {
          /*foo*/
        }
        // violation below ''for' construct must use '{}'s.'
        for (int b : abc)
          ;
        for (int b : abc) {}
        for (int b : abc) {
          /*foo*/
        }
      }
    }

    InnerEmptyBlocks anon =
        new InnerEmptyBlocks() {
          boolean flag = true;
          int[] abc = {
            1, 2, 3, 4,
          };

          void foo() {
            // violation below ''while' construct must use '{}'s.'
            while (flag)
              ;
            while (flag) {}
            while (flag) {
              /*foo*/
            }
            // violation below ''do' construct must use '{}'s.'
            do
              ;
            while (flag);
            do {} while (flag);
            do {
              /*foo*/
            } while (flag);
            // violation below ''if' construct must use '{}'s.'
            if (flag)
              ;
            if (flag) {
              /*foo*/
            }
            if (flag) {
              /*foo*/
            }
            // violation 2 lines below ''if' construct must use '{}'s.'
            // violation 3 lines below ''else' construct must use '{}'s.'
            if (flag)
              ;
            else
              ;
            if (flag) {
            } else {
            }
            if (flag) {
              /*foo*/
            } else {
              /*foo*/
            }
            // violation below ''for' construct must use '{}'s.'
            for (int i = 0; i < 10; i++)
              ;
            for (int i = 0; i < 10; i++) {}
            for (int i = 0; i < 10; i++) {
              /*foo*/
            }
            // violation below ''for' construct must use '{}'s.'
            for (int b : abc)
              ;
            for (int b : abc) {}
            for (int b : abc) {
              /*foo*/
            }
          }
        };
  }
}
