package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputRightCurlyOther {
  /**
   * @see test method *
   */
  int foo() throws InterruptedException {
    int x = 1;
    int a = 2;
    while (true)
    {
      try
      {
        if (x > 0)
        {
          break;
        } else if (x < 0)
        {

          ;
        } // violation ''}' at column 9 should be on the same line as the next part of .*'
        else
        {
          break;
        }
        switch (a)
        {
          case 0:
            break;
          default:
            break;
        }
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      catch (Exception e)
      {
        break;
      }
    }

    synchronized (this)
    {
      do
      {
        x = 2;
      } while (x == 2);
    }

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++)
    {
      String innerBlockVariable = "";
    }

    if (System.currentTimeMillis() > 1000) return 1;
    else return 2;
  }

  static {
    int x = 1;
  }

  public enum GreetingsEnum {
    HELLO,
    GOODBYE
  };

  void method2() {
    boolean flag = true;
    if (flag)
    {
      System.identityHashCode("heh");
      flag = !flag; } System. // violation ''}' at column 21 should have line break before.'
      identityHashCode("Xe-xe");

    if (flag)
    {
      System.identityHashCode("some foo");
    }
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
class FooCtorAlone {
  int i;

  public FooCtorAlone() {
    i = 1;
  }} // violation ''}' at column 3 should be alone on a line.'

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
class FooMethodAlone {
  public void fooMethod() {
    int i = 1;
  }} // violation ''}' at column 3 should be alone on a line.'

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
class FooInnerAlone {
  class InnerFoo {
    public void fooInnerMethod() {}
  }
}

class EnumContainerAlone {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  }
}

class WithArraysAlone {
  String[] s = {""};
  String[] empty = {};
  String[] s1 = {
    "foo", "foo",
  };
  String[] s2 = {
    "foo", "foo",
  };
  String[] s3 = {
    "foo", "foo",
  };
  String[] s4 = {"foo", "foo"};
}

class Interface {
  public @interface TestAnnotation {}

  public @interface TestAnnotation1 {
    String someValue();
  }

  public @interface TestAnnotation2 {
    String someValue();
  }

  public @interface TestAnnotation3 {
    String someValue();
  }

  public @interface TestAnnotation4 {
    String someValue();
  }
}

enum TestEnum {}

enum TestEnum1 {
  SOME_VALUE;
}

enum TestEnum2 {
  SOME_VALUE;
}

enum TestEnum3 {
  SOME_VALUE;
}

enum TestEnum4 {
  SOME_VALUE;
}
