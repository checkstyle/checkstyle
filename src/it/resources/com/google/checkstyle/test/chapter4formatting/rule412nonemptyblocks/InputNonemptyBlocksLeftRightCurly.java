package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputNonemptyBlocksLeftRightCurly
  { // violation ''{' at column 3 should be on the previous line.'
    /**
     * Summary.
     *
     * @return helper func *
     */
    boolean condition()
    { // violation ''{' at column 5 should be on the previous line.'
      return false;
    }

    /** Test do/while loops. */
    void testDoWhile()
    { // violation ''{' at column 5 should be on the previous line.'

      do {
        testDoWhile();
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      while (condition());

      do {
        testDoWhile();
      } while (condition());
    }

    /** Test while loops. */
    void testWhile()
    { // violation ''{' at column 5 should be on the previous line.'

      while (condition()) {
        testWhile();
      }

      while (condition()) {
        /* foo */
      }
      while (condition()) {
        testWhile();
      }
      while (condition()) {
        if (condition()) {
          testWhile();
        }
      }
    }

    /** Test for loops. */
    void testFor()
    { // violation ''{' at column 5 should be on the previous line.'

      for (int i = 1; i < 5; i++) {
        testFor();
      }

      for (int i = 1; i < 5; i++) {
        /* foo */
      }
      for (int i = 1; i < 5; i++) {
        testFor();
      }
      for (int i = 1; i < 5; i++) {
        if (i > 2) {
          testFor();
        }
      }
    }

    /** Test if constructs. */
    public void testIf()
    { // violation ''{' at column 5 should be on the previous line.'

      if (condition()) {
        testIf();
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      else if (condition()) {
        testIf();
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      else {
        testIf();
      }

      if (condition()) { /* foo */ }
      if (condition()) {
        testIf();
      }
      if (condition()) {
        testIf();
      } else {
        testIf();
      }
      if (condition()) {
        testIf();
      } else {
        testIf();
      }
      if (condition()) {
        testIf();
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      else {
        testIf();
      }
      if (condition()) {
        if (condition()) {
          testIf();
        }
      }
    }

    void whitespaceAfterSemi()
    { // violation ''{' at column 5 should be on the previous line.'

      int i = 1;
      int j = 2;

      for (; ; ) {}
    }

    /** Empty constructor block. */
    public InputNonemptyBlocksLeftRightCurly() {}

    /** Empty method block. */
    public void emptyImplementation() {}
  }

// violation below 'Top-level class EnumContainerLeft has to reside in its own source file.'
class EnumContainerLeft {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class WithArraysLeft has to reside in its own source file.'
class WithArraysLeft { // ok
  String[] s1 = {""}; // ok
  String[] empty = {}; // ok
  String[] s2 = { // ok
    "foo", "foo",
  };
  String[] s3 = { // ok
    "foo", "foo",
  };
  String[] s4 = { // ok
    "foo", "foo",
  };
  String[] s5 = {"foo", "foo"}; // ok
}

// violation below 'Top-level class InputRightCurlyOther2 has to reside in its own source file.'
class InputRightCurlyOther2
  { // violation ''{' at column 3 should be on the previous line.'
    /**
     * Summary.
     *
     * @see test method *
     */
    int foo()
        throws InterruptedException
    { // violation ''{' at column 5 should be on the previous line.'
      int x = 1;
      int a = 2;
      while (true)
        { // violation ''{' at column 9 should be on the previous line.'
          try
          { // violation ''{' at column 11 should be on the previous line.'
            if (x > 0)
              { // violation ''{' at column 15 should be on the previous line.'
                break;
              } else if (x < 0) { // ok

              ;
            } // violation ''}' at column 13 should be on the same line as the next part.*'
            else
              { // violation ''{' at column 15 should be on the previous line.'
                break;
              } // ok
            switch (a)
              { // violation ''{' at column 15 should be on the previous line.'
              case 0:
                break;
              default:
                break;
              } // ok
          } // violation ''}' at column 11 should be on the same line as the next part of .*'
          catch (Exception e)
          { // violation ''{' at column 11 should be on the previous line.'
            break;
          } // ok
        } // ok

      synchronized (this)
        { // violation ''{' at column 9 should be on the previous line.'
          do
          { // violation ''{' at column 11 should be on the previous line.'
            x = 2;
          } while (x == 2); // ok
        } // ok

      this.wait(666); // Bizarre, but legal

      for (int k = 0; k < 1; k++)
        { // violation ''{' at column 9 should be on the previous line.'
          String innerBlockVariable = "";
        } // ok

      if (System.currentTimeMillis() > 1000) {
        return 1;
      } else {
        return 2;
      }
    } // ok

    static
    { // violation ''{' at column 5 should be on the previous line.'
      int x = 1;
    } // ok

    /** Some javadoc. */
    public enum GreetingsEnum
    { // violation ''{' at column 5 should be on the previous line.'
      HELLO,
      GOODBYE
    } // ok

    void method2()
    { // violation ''{' at column 5 should be on the previous line.'
      boolean flag = true;
      if (flag) {
        System.identityHashCode("heh");
      // 2 violations 3 lines below:
      //  ''if' child has incorrect indentation level 6, expected level should be 8.'
      //  ''}' at column 21 should have line break before.'
      flag = !flag; } System
        .identityHashCode("Xe-xe");

      if (flag) { System.identityHashCode("some foo"); }
      // violation above ''{' at column 17 should have line break after.'
    } // ok
  } // ok

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
// violation below 'Top-level class FooCtor has to reside in its own source file.'
class FooCtor
  { // violation ''{' at column 3 should be on the previous line.'
  int i3;

  public FooCtor()
    { // violation ''{' at column 5 should be on the previous line.'
      i3 = 1;
    } } // violation ''}' at column 5 should be alone on a line.'

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
// violation below 'Top-level class FooMethod has to reside in its own source file.'
class FooMethod
  { // violation ''{' at column 3 should be on the previous line.'
    public void fooMethod()
    { // violation ''{' at column 5 should be on the previous line.'
      int i = 1;
    } } // violation ''}' at column 5 should be alone on a line.'

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
// violation below 'Top-level class FooInner has to reside in its own source file.'
class FooInner
  { // violation ''{' at column 3 should be on the previous line.'
  class InnerFoo
    { // violation ''{' at column 5 should be on the previous line.'
      public void fooInnerMethod()
        { // violation ''{' at column 9 should be on the previous line.'
        }
    }
  } // ok

// violation below 'Top-level class EnumContainer has to reside in its own source file.'
class EnumContainer {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class WithArrays has to reside in its own source file.'
class WithArrays {
  String[] test = {""}; // ok
  String[] empty = {}; // ok
  String[] s1 = {
    "foo", "foo",
  }; // ok
  String[] s2 = {
    "foo", "foo",
  }; // ok
  String[] s3 = {
    "foo", "foo",
  }; // ok
  String[] s4 = {"foo", "foo"}; // ok
}
