package com.google.checkstyle.test.chapter4formatting.rule411optionalbracesusage;

class InputUseOfOptionalBraces {
  /**
   * some javadoc..
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
    do testDoWhile(); // violation ''do' construct must use '{}'s.'
    while (condition());
  }

  /** Test while loops. */
  void testWhile() {
    // Valid
    while (condition()) {
      testWhile();
    }

    // Invalid
    while (condition()); // violation ''while' construct must use '{}'s.'
    while (condition()) // violation ''while' construct must use '{}'s.'
      testWhile();
    while (condition()) // violation ''while' construct must use '{}'s.'
      if (condition()) // violation ''if' construct must use '{}'s.'
          testWhile();
    String k = "testing";
    if (k != null) k = "ss"; // violation ''if' construct must use '{}'s.'
  }

  /** Test for loops. */
  void testFor() {
    // Valid
    for (int i = 1; i < 5; i++) {
      testFor();
    }

    // Invalid
    for (int i = 1; i < 5; i++); // violation ''for' construct must use '{}'s.'
    for (int i = 1; i < 5; i++) // violation ''for' construct must use '{}'s.'
      testFor();
    for (int i = 1; // violation ''for' construct must use '{}'s.'
        i < 5;
        i++)
      if (i > 2) // violation ''if' construct must use '{}'s.'
        testFor();
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
    if (condition()); // violation ''if' construct must use '{}'s.'
    if (condition()) // violation ''if' construct must use '{}'s.'
      testIf();
    if (condition()) // violation ''if' construct must use '{}'s.'
      testIf();
    else // violation ''else' construct must use '{}'s.'
      testIf();
    if (condition()) // violation ''if' construct must use '{}'s.'
      testIf();
    else {
      testIf();
    }
    if (condition()) {
      testIf();
    } else // violation ''else' construct must use '{}'s.'
      testIf();
    if (condition()) // violation ''if' construct must use '{}'s.'
      if (condition()) // violation ''if' construct must use '{}'s.'
          testIf();
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
      while (flag); // violation ''while' construct must use '{}'s.'
      while (flag) {}
      while (flag) {
        /*foo*/
      }
      do ; // violation ''do' construct must use '{}'s.'
      while (flag);
      do {} while (flag);
      do {
        /*foo*/
      } while (flag);
      if (flag) ; // violation ''if' construct must use '{}'s.'
      if (flag) { /*foo*/ }
      if (flag) {
        /*foo*/
      }
      if (flag) ; // violation ''if' construct must use '{}'s.'
      else ; // violation ''else' construct must use '{}'s.'
      // violation below 'Empty if block.'
      if (flag) {
      } else {
      }
      // violation 2 lines above 'Empty else block.'
      if (flag) {
        /*foo*/
      } else {
        /*foo*/
      }
      for (int i = 0; i < 10; i++); // violation ''for' construct must use '{}'s.'
      for (int i = 0; i < 10; i++) {}
      for (int i = 0; i < 10; i++) {
        /*foo*/
      }
      for (int b : abc); // violation ''for' construct must use '{}'s.'
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
        while (flag); // violation ''while' construct must use '{}'s.'
        while (flag) {}
        while (flag) {
          /*foo*/
        }
        do ; // violation ''do' construct must use '{}'s.'
        while (flag);
        do {} while (flag);
        do {
          /*foo*/
        } while (flag);
        if (flag) ; // violation ''if' construct must use '{}'s.'
        if (flag) { /*foo*/ }
        if (flag) {
          /*foo*/
        }
        if (flag) ; // violation ''if' construct must use '{}'s.'
        else ; // violation ''else' construct must use '{}'s.'
        // violation below 'Empty if block.'
        if (flag) {
        } else {
        }
        // violation 2 lines above 'Empty else block.'
        if (flag) {
          /*foo*/
        } else {
          /*foo*/
        }
        for (int i = 0; i < 10; i++); // violation ''for' construct must use '{}'s.'
        for (int i = 0; i < 10; i++) {}
        for (int i = 0; i < 10; i++) {
          /*foo*/
        }
        for (int b : abc); // violation ''for' construct must use '{}'s.'
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
                while (flag); // violation ''while' construct must use '{}'s.'
                while (flag) {}
                while (flag) {
                  /*foo*/
                }
                do ; // violation ''do' construct must use '{}'s.'
                while (flag);
                do {} while (flag);
                do {
                  /*foo*/
                } while (flag);
                if (flag) ; // violation ''if' construct must use '{}'s.'
                if (flag) { /*foo*/ }
                if (flag) {
                  /*foo*/
                }
                if (flag) ; // violation ''if' construct must use '{}'s.'
                else ; // violation ''else' construct must use '{}'s.'
                // violation below 'Empty if block.'
                if (flag) {
                } else {
                }
                // violation 2 lines above 'Empty else block.'
                if (flag) {
                  /*foo*/
                } else {
                  /*foo*/
                }
                for (int i = 0; i < 10; i++); // violation ''for' construct must use '{}'s.'
                for (int i = 0; i < 10; i++) {}
                for (int i = 0; i < 10; i++) {
                  /*foo*/
                }
                for (int b : abc); // violation ''for' construct must use '{}'s.'
                for (int b : abc) {}
                for (int b : abc) {
                  /*foo*/
                }
              }
            };
  }
}
