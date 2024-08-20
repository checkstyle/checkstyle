package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** some javadoc. */
public class InputFormattedNonemptyBlockKrStyle {
  /**
   * summary.
   *
   * @return helper func *
   */
  boolean condition() {
    return false;
  }

  /** Test do/while loops. */
  void testDoWhile() {

    do {
      testDoWhile();
    } while (condition());

    do {
      testDoWhile();
    } while (condition());
  }

  /** Test while loops. */
  void testWhile() {

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
  void testFor() {

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
  public void testIf() {

    if (condition()) {
      testIf();
    } else if (condition()) {
      testIf();
    } else {
      testIf();
    }

    if (condition()) {
      /* foo */
    }
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
    } else {
      testIf();
    }
    if (condition()) {
      if (condition()) {
        testIf();
      }
    }
  }

  void whitespaceAfterSemi() {

    int i = 1;
    int j = 2;

    for (; ; ) {}
  }

  /** Empty constructor block. */
  public InputFormattedNonemptyBlockKrStyle() {}

  /** Empty method block. */
  public void emptyImplementation() {}
}

// violation below 'Top-level class ExtraEnumContainerLeft has to reside in its own source file.'
class ExtraEnumContainerLeft {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class ExtraWithArraysLeft has to reside in its own source file.'
class ExtraWithArraysLeft { // ok
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

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputRightCurlyOther2 {
  /**
   * summary.
   *
   * @see test method *
   */
  int foo() throws InterruptedException {
    int x = 1;
    int a = 2;
    while (true) {
      try {
        if (x > 0) {
          break;
        } else if (x < 0) { // ok

          ;
        } else {
          break;
        } // ok
        switch (a) {
          case 0:
            break;
          default:
            break;
        } // ok
      } catch (Exception e) {
        break;
      } // ok
    } // ok

    synchronized (this) {
      do {
        x = 2;
      } while (x == 2); // ok
    } // ok

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++) {
      String innerBlockVariable = "";
    } // ok

    if (System.currentTimeMillis() > 1000) {
      return 1;
    } else {
      return 2;
    }
  } // ok

  static {
    int x = 1;
  } // ok

  /** some javadoc. */
  public enum GreetingsEnum {
    HELLO,
    GOODBYE
  } // ok

  void method2() {
    boolean flag = true;
    if (flag) {
      System.identityHashCode("heh");
      flag = !flag;
    }
    System.identityHashCode("Xe-xe");

    if (flag) {
      System.identityHashCode("some foo");
    }
  } // ok
} // ok

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
// violation below 'Top-level class ExtraFooCtor has to reside in its own source file.'
class ExtraFooCtor {
  int i3;

  public ExtraFooCtor() {
    i3 = 1;
  }
}

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
// violation below 'Top-level class ExtraFooMethod has to reside in its own source file.'
class ExtraFooMethod {
  public void fooMethod() {
    int i = 1;
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
// violation below 'Top-level class ExtraFooInner has to reside in its own source file.'
class ExtraFooInner {
  class InnerFoo {
    public void fooInnerMethod() {}
  }
} // ok

// violation below 'Top-level class ExtraEnumContainer has to reside in its own source file.'
class ExtraEnumContainer {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class ExtraWithArrays has to reside in its own source file.'
class ExtraWithArrays {
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

// violation below 'Top-level class .* has to reside in its own source file.'
@ExtraTestClassAnnotation
class ExtraInputLeftCurlyAnnotations {
  private static final int X = 10;

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  @SuppressWarnings("unused")
  public int hashCode() {
    int a = 10;
    return 1;
  }

  @Override
  @SuppressWarnings({"unused", "unchecked", "static-access"})
  public String toString() {
    Integer i = this.X;
    List<String> l = new ArrayList();
    return "SomeString";
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
@ExtraTestClassAnnotation
class ExtraInputLeftCurlyAnnotations2 {
  private static final int X = 10;

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  @SuppressWarnings("unused")
  public int hashCode() {
    int a = 10;
    return 1;
  }

  @Override
  @SuppressWarnings({"unused", "unchecked", "static-access"})
  public String toString() {
    Integer i = this.X;
    List<String> l = new ArrayList();
    return "SomeString";
  }
}

// violation below 'Top-level class ExtraTestClassAnnotation has to reside in its own source file.'
@Target(ElementType.TYPE)
@interface ExtraTestClassAnnotation {}

// violation below 'Top-level class ExtraInputLeftCurlyMethod has to reside in its own source file.'
class ExtraInputLeftCurlyMethod {
  ExtraInputLeftCurlyMethod() {}

  ExtraInputLeftCurlyMethod(String one) {}

  ExtraInputLeftCurlyMethod(int one) {}

  void method1() {}

  void method2() {}

  void method3() {}

  void method4() {}

  void method5(String one, String two) {}

  void method6(String one, String two) {}

  enum ExtraInputLeftCurlyMethodEnum {
    CONSTANT1("hello") {
      void method1() {}

      void method2() {}

      void method3() {}

      void method4() {}

      void method5(String one, String two) {}

      void method6(String one, String two) {}
    },

    CONSTANT2("hello") {},

    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello") {};

    private ExtraInputLeftCurlyMethodEnum(String value) {}

    void method1() {}

    void method2() {}

    void method3() {}

    void method4() {}

    void method5(String one, String two) {}

    void method6(String one, String two) {}
  }
}

// violation below 'Top-level class ExtraInputRightCurly has to reside in its own source file.'
class ExtraInputRightCurly {
  /** some javadoc. */
  public static void main(String[] args) {
    boolean after = false;
    try {
      /* foo */
    } finally {
      after = true;
    }
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputRightCurlyDoWhile {
  public void foo1() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo2() {
    int i = 1;
    while (i < 5) {
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
      i++;
    }
  }

  /** some javadoc. */
  public void foo3() {
    int i = 1;
    do {
      i++;
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
    } while (i < 5);
  }

  /** some javadoc. */
  public void foo4() {
    int prog;
    int user;
    prog = (int) (Math.random() * 10) + 1;
    Scanner input = new Scanner(System.in, "utf-8");
    if (input.hasNextInt()) {
      do {
        user = input.nextInt();
        if (user == prog) {
          String.CASE_INSENSITIVE_ORDER.equals("Good!");
        } else {
          if (user > 0 && user <= 10) {
            String.CASE_INSENSITIVE_ORDER.equals("Bad! ");
            if (prog < user) {
              String.CASE_INSENSITIVE_ORDER.equals("My number is less than yours.");
            } else {
              String.CASE_INSENSITIVE_ORDER.equals("My number is greater than yours");
            }
          } else {
            String.CASE_INSENSITIVE_ORDER.equals("Violation!");
          }
        }
      } while (user != prog);
    } else {
      String.CASE_INSENSITIVE_ORDER.equals("Violation!");
    }
    String.CASE_INSENSITIVE_ORDER.equals("Goodbye!");
  }

  public void foo5() {
    do {} while (true);
  }

  public void foo6() {
    do {} while (true);
  }

  public void foo7() {
    do {} while (true);
  }

  public void foo8() {
    do {} while (true);
  }

  public void foo9() {
    do {} while (true);
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputRightCurlyDoWhile2 {

  public void foo1() {
    do {} while (true);
  }

  /** some javadoc. */
  public void foo2() {
    int i = 1;
    while (i < 5) {
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
      i++;
    }
  }

  /** some javadoc. */
  public void foo3() {
    int i = 1;
    do {
      i++;
      String.CASE_INSENSITIVE_ORDER.equals(i + " ");
    } while (i < 5);
  }

  /** some javadoc. */
  public void foo4() {
    int prog;
    int user;
    prog = (int) (Math.random() * 10) + 1;
    Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);
    if (input.hasNextInt()) {
      do {
        user = input.nextInt();
        if (user == prog) {
          String.CASE_INSENSITIVE_ORDER.equals("Good!");
        } else {
          if (user > 0 && user <= 10) {
            String.CASE_INSENSITIVE_ORDER.equals("Bad! ");
            if (prog < user) {
              String.CASE_INSENSITIVE_ORDER.equals("My number is less than yours.");
            } else {
              String.CASE_INSENSITIVE_ORDER.equals("My number is greater than yours");
            }
          } else {
            String.CASE_INSENSITIVE_ORDER.equals("Violation!");
          }
        }
      } while (user != prog);
    } else {
      String.CASE_INSENSITIVE_ORDER.equals("Violation!");
    }
    String.CASE_INSENSITIVE_ORDER.equals("Goodbye!");
  }

  public void foo5() {
    do {} while (true);
  }

  public void foo6() {
    do {} while (true);
  }

  public void foo7() {
    do {} while (true);
  }

  public void foo8() {
    do {} while (true);
  }

  public void foo9() {
    do {} while (true);
  }
}

// violation below 'Top-level class ExtraInputRightCurlyOther has to reside in its own source file.'
class ExtraInputRightCurlyOther {
  /**
   * summary.
   *
   * @see test method *
   */
  int foo() throws InterruptedException {
    int x = 1;
    int a = 2;
    while (true) {
      try {
        if (x > 0) {
          break;
        } else if (x < 0) {

          ;
        } else {
          break;
        }
        switch (a) {
          case 0:
            break;
          default:
            break;
        }
      } catch (Exception e) {
        break;
      }
    }

    synchronized (this) {
      do {
        x = 2;
      } while (x == 2);
    }

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++) {
      String innerBlockVariable = "";
    }

    if (System.currentTimeMillis() > 1000) {
      return 1;
    } else {
      return 2;
    }
  }

  static {
    int x = 1;
  }

  public enum GreetingsEnum {
    HELLO,
    GOODBYE
  }

  void method2() {
    boolean flag = true;
    if (flag) {
      System.identityHashCode("heh");
      flag = !flag;
    }
    System.identityHashCode("Xe-xe");

    if (flag) {
      System.identityHashCode("some foo");
    }
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
class ExtraFooCtorAlone {
  // violation above 'Top-level class ExtraFooCtorAlone has to reside in its own source file.'
  int test;

  public ExtraFooCtorAlone() {
    test = 1;
  }
}

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
class ExtraFooMethodAlone {
  // violation above 'Top-level class ExtraFooMethodAlone has to reside in its own source file.'
  public void fooMethod() {
    int i = 1;
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
class ExtraFooInnerAlone {
  // violation above 'Top-level class ExtraFooInnerAlone has to reside in its own source file.'
  class InnerFoo {
    public void fooInnerMethod() {}
  }
}

// violation below 'Top-level class ExtraEnumContainerAlone has to reside in its own source file.'
class ExtraEnumContainerAlone {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  }
}

// violation below 'Top-level class ExtraWithArraysAlone has to reside in its own source file.'
class ExtraWithArraysAlone {
  String[] ss = {""};
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

// violation below 'Top-level class ExtraInterface has to reside in its own source file.'
class ExtraInterface {
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

// violation below 'Top-level class ExtraTestEnum has to reside in its own source file.'
enum ExtraTestEnum {}

// violation below 'Top-level class ExtraTestEnum1 has to reside in its own source file.'
enum ExtraTestEnum1 {
  SOME_VALUE;
}

// violation below 'Top-level class ExtraTestEnum2 has to reside in its own source file.'
enum ExtraTestEnum2 {
  SOME_VALUE;
}

// violation below 'Top-level class ExtraTestEnum3 has to reside in its own source file.'
enum ExtraTestEnum3 {
  SOME_VALUE;
}

// violation below 'Top-level class ExtraTestEnum4 has to reside in its own source file.'
enum ExtraTestEnum4 {
  SOME_VALUE;
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputRightCurlySwitchCase {

  /** some javadoc. */
  public static void method0() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0;
    }
  }

  /** some javadoc. */
  public static void method1() {
    int mode = 0;
    switch (mode) {
      default:
        int x = 0;
    }
  }

  /** some javadoc. */
  public static void method2() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0;
    }
  }

  /** some javadoc. */
  public static void method3() {
    int mode = 0;
    switch (mode) {
      default:
        int x = 0;
    }
  }

  /** some javadoc. */
  public static void method4() {
    int mode = 0;
    switch (mode) {
      case 1:
        int y = 2;
        break;
      default:
        int x = 0;
    }
  }
}
