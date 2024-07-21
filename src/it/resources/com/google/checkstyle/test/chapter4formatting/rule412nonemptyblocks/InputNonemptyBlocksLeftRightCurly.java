package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputNonemptyBlocksLeftRightCurly
{ // violation ''{' at column 1 should be on the previous line.'
  /**
   * @return helper func *
   */
  boolean condition()
  { // violation ''{' at column 3 should be on the previous line.'
    return false;
  }

  /** Test do/while loops * */
  void testDoWhile()
  { // violation ''{' at column 3 should be on the previous line.'

    do
    {
      testDoWhile();
    } // violation ''}' at column 5 should be on the same line as the next part of .*'
    while (condition());

    do testDoWhile();
    while (condition());
  }

  /** Test while loops * */
  void testWhile()
  { // violation ''{' at column 3 should be on the previous line.'

    while (condition())
    {
      testWhile();
    }

    while (condition())
      ;
    while (condition()) testWhile();
    while (condition()) if (condition()) testWhile();
  }

  /** Test for loops * */
  void testFor()
  { // violation ''{' at column 3 should be on the previous line.'

    for (int i = 1; i < 5; i++)
    {
      testFor();
    }

    for (int i = 1; i < 5; i++)
      ;
    for (int i = 1; i < 5; i++) testFor();
    for (int i = 1; i < 5; i++) if (i > 2) testFor();
  }

  /** Test if constructs * */
  public void testIf()
  { // violation ''{' at column 3 should be on the previous line.'

    if (condition())
    {
      testIf();
    } // violation ''}' at column 5 should be on the same line as the next part of .*'
    else if (condition())
    {
      testIf();
    } // violation ''}' at column 5 should be on the same line as the next part of .*'
    else
    {
      testIf();
    }

    if (condition())
      ;
    if (condition()) testIf();
    if (condition()) testIf();
    else testIf();
    if (condition()) testIf();
    else
    {
      testIf();
    }
    if (condition())
    {
      testIf();
    } // violation ''}' at column 5 should be on the same line as the next part of .*'
    else testIf();
    if (condition()) if (condition()) testIf();
  }

  void whitespaceAfterSemi()
  { // violation ''{' at column 3 should be on the previous line.'

    int i = 1;
    int j = 2;

    for (; ; ) {}
  }

  /** Empty constructor block. * */
  public InputNonemptyBlocksLeftRightCurly() {}

  /** Empty method block. * */
  public void emptyImplementation() {}
}

class EnumContainerLeft {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

class WithArraysLeft { // ok
  String[] s = {""}; // ok
  String[] empty = {}; // ok
  String[] s1 = { // ok
    "foo", "foo",
  };
  String[] s2 = { // ok
    "foo", "foo",
  };
  String[] s3 = { // ok
    "foo", "foo",
  };
  String[] s4 = {"foo", "foo"}; // ok
}

class InputRightCurlyOther2
{ // violation ''{' at column 1 should be on the previous line.'
  /**
   * @see test method *
   */
  int foo()
      throws InterruptedException
  { // violation ''{' at column 3 should be on the previous line.'
    int x = 1;
    int a = 2;
    while (true) {  // violation '\'{' at column 18 should be on a new line.'
      try
      {
        if (x > 0)
        {
          break;
        } else if (x < 0)
        { // ok

          ;
        } // violation ''}' at column 9 should be on the same line as the next part.*'
        else
        {
          break;
        } // ok
        switch (a)
        {
          case 0:
            break;
          default:
            break;
        } // ok
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      catch (Exception e)
      {
        break;
      } // ok
    } // ok

    synchronized (this)
    {
      do
      {
        x = 2;
      } while (x == 2); // ok
    } // ok

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++)
    {
      String innerBlockVariable = "";
    } // ok

    if (System.currentTimeMillis() > 1000) return 1;
    else return 2;
  } // ok

  static
  { // violation ''{' at column 3 should be on the previous line.'
    int x = 1;
  } // ok

  public enum GreetingsEnum
  { // violation ''{' at column 3 should be on the previous line.'
    HELLO,
    GOODBYE
  }; // ok

  void method2()
  { // violation ''{' at column 3 should be on the previous line.'
    boolean flag = true;
    if (flag)
    {
      System.identityHashCode("heh");
      flag = !flag; } System.
      // violation above ''}' at column 21 should have line break before.'
      identityHashCode("Xe-xe");

    if (flag) { System.identityHashCode("some foo"); }
    // violation above ''{' at column 15 should be on a new line.'
  } // ok
} // ok

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
class FooCtor
{ // violation ''{' at column 1 should be on the previous line.'
  int i;

  public FooCtor()
  { // violation ''{' at column 3 should be on the previous line.'
    i = 1;
  }} // violation ''}' at column 3 should be alone on a line.'

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
class FooMethod
{ // violation ''{' at column 1 should be on the previous line.'
  public void fooMethod()
  { // violation ''{' at column 3 should be on the previous line.'
    int i = 1;
  }} // violation ''}' at column 3 should be alone on a line.'

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
class FooInner
{ // violation ''{' at column 1 should be on the previous line.'
  class InnerFoo
  { // violation ''{' at column 3 should be on the previous line.'
    public void fooInnerMethod()
    { // violation ''{' at column 5 should be on the previous line.'
    }
  }
} // ok

class EnumContainer {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

class WithArrays {
  String[] s = {""}; // ok
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
