package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/** some javadoc. */
public class InputFormattedHorizontalWhitespace
    implements Comparable<InputFormattedHorizontalWhitespace>, Serializable {
  <T> InputFormattedHorizontalWhitespace(List<T> things, int i) {}

  public <T> InputFormattedHorizontalWhitespace(List<T> things) {}

  public static <T> Callable<T> callable(Runnable task, T result) {
    return null;
  }

  /** some javadoc. */
  public static <T> Callable<T> callable2(Runnable task, T result) {
    Map<Class<?>, Integer> x = new HashMap<Class<?>, Integer>();
    for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
      entry.getValue();
    }
    Class<?>[] parameterClasses = new Class<?>[0];
    return null;
  }

  void meth() {
    List<Integer> x = new ArrayList<Integer>();
    List<List<Integer>> y = new ArrayList<List<Integer>>();
    List<Integer> a = new ArrayList<Integer>();
    List<List<Integer>> b = new ArrayList<List<Integer>>();
  }

  public int compareTo(InputFormattedHorizontalWhitespace obj) {
    return 0;
  }

  /** some javadoc. */
  public int getConstructor(Class<?>... parameterTypes) {
    Collections.<Object>emptySet();
    Collections.<Object>emptySet();
    return 666;
  }

  /** some javadoc. */
  public interface IntEnum {}

  /** some javadoc. */
  public static class IntEnumValueType<
      E extends Enum<E> & InputFormattedHorizontalWhitespace.IntEnum> {}

  /** some javadoc. */
  public static class IntEnumValueType2<
      E extends Enum<E> & InputFormattedHorizontalWhitespace.IntEnum> {}

  /** some javadoc. */
  public static class IntEnumValueType3<
      E extends Enum<E> & InputFormattedHorizontalWhitespace.IntEnum> {}
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputGenericWhitespaceEndsTheLine {
  /** some javadoc. */
  public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
    return otherObject instanceof Enum<?>;
  }
}

// violation below 'Top-level class ExtraInputMethodParamPad has to reside in its own source file.'
class ExtraInputMethodParamPad {
  public ExtraInputMethodParamPad() {
    super();
  }

  public ExtraInputMethodParamPad(int param) {
    super();
  }

  public void method() {}

  public void method(int param) {}

  /** some javadoc. */
  public void method(double param) {
    // invoke constructor
    ExtraInputMethodParamPad pad = new ExtraInputMethodParamPad();
    pad = new ExtraInputMethodParamPad();
    pad = new ExtraInputMethodParamPad();

    // call method
    method();
    method();
  }

  /** some javadoc. */
  public void dottedCalls() {
    this.method();
    this.method();
    this.method();

    ExtraInputMethodParamPad p = new ExtraInputMethodParamPad();
    p.method();
    p.method();
    p.method();

    java.lang.Integer.parseInt("0");
    java.lang.Integer.parseInt("0");
    java.lang.Integer.parseInt("0");
  }

  /** some javadoc. */
  public void newArray() {
    int[] a = new int[] {0, 1};
    java.util.Vector<String> v = new java.util.Vector<String>();
    java.util.Vector<String> v1 = new Vector<String>();
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputNoWhitespaceBeforeAnnotations {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @NonNull int @NonNull [] @NonNull [] fiel1; // ok until #8205
  @NonNull int @NonNull [] @NonNull [] field2; // ok

  public void foo(final char @NonNull [] param) {} // ok

  // @NonNull int @NonNull ... field3; // non-compilable
  // @NonNull int @NonNull... field4; // non-compilable

  public void foo1(final char[] param) {} // ok

  public void foo2(final char[] param) {} // ok

  public void foo3(final char @NonNull [] param) {} // ok until #8205

  public void foo4(final char @NonNull [] param) {} // ok

  void test1(String... param) {} // ok until #8205

  void test2(String... param) {} // ok until #8205

  void test3(String @NonNull ... param) {} // ok until #8205

  void test4(String @NonNull ... param) {} // ok
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputNoWhitespaceBeforeCaseDefaultColon {
  {
    switch (1) {
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputNoWhitespaceBeforeColonOfLabel {

  {
    label1:
    for (int i = 0; i < 10; i++) {}
  }

  /** some javadoc. */
  public void foo() {
    label2:
    while (true) {}
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputNoWhitespaceBeforeEmptyForLoop {

  /** some javadoc. */
  public static void foo() {
    for (; ; ) { // ok
      break;
    }
    for (int x = 0; ; ) { // ok
      break;
    }
    for (int x = 0; ; ) {
      break;
    }
    for (int x = 0; x < 10; ) { // ok
      break;
    }
    for (int x = 0; x < 10; ) {
      break;
    }
  }
}

// violation below 'Top-level class ExtraInputParenPad has to reside in its own source file.'
class ExtraInputParenPad {
  boolean fooo = this.bar((true && false) && true);

  String foo() {
    return ((Object) bar((1 > 2) ? ((3 < 4) ? false : true) : ((1 == 1) ? false : true)))
        .toString();
  }

  /** some javadoc. */
  @MyAnnotation
  public boolean bar(boolean a) {
    assert (true);
    return true;
  }

  class ParenPadNoSpace {
    ParenPadNoSpace() {
      this(0);
    }

    ParenPadNoSpace(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  class ParenPadSpaceLeft {
    ParenPadSpaceLeft() {
      this(0);
    }

    ParenPadSpaceLeft(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  class ParenPadSpaceRight {
    ParenPadSpaceRight() {
      this(0);
    }

    ParenPadSpaceRight(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  enum MyEnum {
    SOME_CONSTANT() {
      final int testing = 2 * (4 / 2);
    };

    private Object exam;

    private static String getterName(Exception t) {
      if (t instanceof ClassNotFoundException) {
        return ((ClassNotFoundException) t).getMessage();
      } else {
        return "?";
      }
    }

    /** some javadoc. */
    public void myMethod() {
      String s = "test";
      Object o = s;
      ((String) o).length();
      ((String) o).length();
    }

    /** some javadoc. */
    public void crisRon() {
      Object leo = "messi";
      Object ibra = leo;
      ((String) leo).compareTo((String) ibra);

      Math.random();
    }

    /** some javadoc. */
    public void intStringConv() {
      Object a = 5;
      Object b = "string";
      int w = Integer.parseInt((String) a);
      int x = Integer.parseInt((String) a);
      double y = Double.parseDouble((String) a);
      float z = Float.parseFloat((String) a);

      String d = ((String) b);
    }

    /** some javadoc. */
    public int something(Object o) {

      if (o == null || !(o instanceof Float)) {

        return -1;
      }
      return Integer.valueOf(22).compareTo((Integer) o);
    }

    private void launch(Integer number) {
      String myInt = (number.toString() + '\0');

      boolean result = number == 123;
    }

    /** some javadoc. */
    public String testing() {
      return (this.exam != null) ? ((Enum) this.exam).name() : null;
    }

    Object stringReturnValue(Object result) {

      if (result instanceof String) {

        result = ((String) result).length();
      }
      return result;
    }

    private void except() {
      java.util.ArrayList<Integer> arrlist = new java.util.ArrayList<Integer>(5);

      arrlist.add(20);
      arrlist.add(15);
      arrlist.add(30);

      arrlist.add(45);
      try {
        (arrlist).remove(2);

      } catch (IndexOutOfBoundsException x) {

        x.getMessage();
      }
      org.junit.Assert.assertThat("123", org.hamcrest.CoreMatchers.is("123"));

      org.junit.Assert.assertThat("Help! Integers don't work", 0, org.hamcrest.CoreMatchers.is(1));
    }

    private void tryWithResources() throws Exception {
      try (AutoCloseable a = null) {
        /* foo */
      }

      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }

      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }

      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }

      try (AutoCloseable a = null) {
        /* foo */
      }

      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }
    }
  }

  @interface MyAnnotation {
    String someField() default "Hello world";
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputWhitespaceAfterBad {
  /** some javadoc. */
  public void check1(int x, int y) {
    // violation below ''for' construct must use '{}'s.'
    for (int a = 1, b = 2; a < 5; a++, b--)
      ;
    while (x == 0) {

      int a = 0;
      int b = 1;
    }
    do {

      System.out.println("Testing");
    } while (x == 0 || y == 2);
  }

  /** some javadoc. */
  public void check2(final int a, final int b) {
    if ((float) a == 0.0) {

      System.out.println("true");
    } else {

      System.out.println("false");
    }
  }

  /** some javadoc. */
  public void check3(int... a) {
    Runnable r2 = () -> String.valueOf("Hello world two!");

    switch (a[0]) {
      default:
        break;
    }
  }

  /** some javadoc. */
  public void check4() throws java.io.IOException {
    try (java.io.InputStream ignored = System.in; ) {
      /* foo */
    }
  }

  /** some javadoc. */
  public void check5() {
    try {
      /* foo */
    } finally {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
      /* foo */
    }
  }

  /** some javadoc. */
  public void check6() {
    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    }
  }

  /** some javadoc. */
  public void check7() {
    synchronized (this) {
    }

    synchronized (this) {
    }
  }

  /** some javadoc. */
  public String check8() {
    return ("a" + "b");
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputWhitespaceAfterGood {

  int xyz; // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr; //     testing both.

  /** some javadoc. */
  public void check1(int x, int y) {
    // violation below ''for' construct must use '{}'s.'
    for (int a = 1, b = 2; a < 5; a++, b--)
      ;
    while (x == 0) {
      int a = 0;
      int b = 1;
    }
    do {
      System.out.println("Testing");
    } while (x == 0 || y == 2);
  }

  /** some javadoc. */
  public void check2(final int a, final int b) {
    if ((float) a == 0.0) {
      System.out.println("true");
    } else {
      System.out.println("false");
    }
  }

  /** some javadoc. */
  public void check3(int... a) {
    Runnable r2 = () -> String.valueOf("Hello world two!");
    switch (a[0]) {
      default:
        break;
    }
  }

  /** some javadoc. */
  public void check4() throws java.io.IOException {
    try (java.io.InputStream ignored = System.in) {
      /* foo */
    }

    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** some javadoc. */
  public void check5() {

    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** some javadoc. */
  public void check6() {
    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  public void check7() {
    synchronized (this) {
    }
  }

  public String check8() {
    return ("a" + "b");
  }
}

/*
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 *
 * @author o_sukhodolsky
 * @version 1.0
 */

/** Class for testing whitespace issues. violation missing author tag. */
// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputWhitespaceAroundBasic {
  private final int var1 = 1;
  private final int var2 = 1;

  /** Should be ok. */
  private final int var3 = 1;

  /** skip blank lines between comment and code, should be ok. */
  private final int var4 = 1;

  int xyz; // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr; //     testing both.

  /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class). */
  private int test;

  private int i4;
  private int i5;
  private int i6;

  /** method. */
  void method1() {
    final int a = 1;
    int b = 1;
    b = 1;
    b += 1;
    b -= -1 + (+b);
    b = b++ + b--; // ok
    b = ++b - --b; // ok
  }

  /** method. */
  void method2() {
    synchronized (this) {
    }
    try {
      /* foo */
    } catch (RuntimeException e) {
      /* foo */
    }
  }

  /** test WS after void return. */
  private void fastExit() {
    boolean complicatedStuffNeeded = true;
    if (!complicatedStuffNeeded) {
      // should not complain about missing WS after return
    } else {
      // do complicated stuff
    }
  }

  /**
   * test WS after non void return.
   *
   * @return 2
   */
  private int nonVoid() {
    if (true) {
      return (2);

    } else {
      return 2; // this is ok
    }
  }

  /** test casts. */
  private void testCasts() {
    Object o = (Object) new Object(); // ok
    o = (Object) o; // ok
    o = (Object) o; // ok
    o = (Object) o; // ok
  }

  /** test questions. */
  private void testQuestions() {

    boolean b = (1 == 2) ? false : true;
  }

  /** star test. */
  private void starTest() {
    int x = 2 * 3 * 4;
  }

  /** boolean test. */
  private void boolTest() {
    boolean a = true;
    boolean x = !a;
    int z = ~1 + ~2;
  }

  /** division test. */
  private void divTest() {
    int a = 4 % 2;
    int b = 4 % 2;
    int c = 4 % 2;
    int d = 4 % 2;
    int e = 4 / 2;
    int f = 4 / 2;
    int g = 4 / 2;
  }

  /**
   * summary.
   *
   * @return dot test *
   */
  private java.lang.String dotTest() {
    Object o = new java.lang.Object();
    o.toString();
    o.toString();
    o.toString();
    return o.toString();
  }

  /** assert statement test. */
  public void assertTest() {
    // OK
    assert true;

    // OK
    assert true : "Whups";

    // evil colons, should be OK
    assert "OK".equals(null) ? false : true : "Whups";

    // missing WS around assert
    assert (true);

    // missing WS around colon
    assert true : "Whups";
  }

  /** another check. */
  void donBradman(Runnable run) {
    donBradman(
        new Runnable() {
          public void run() {}
        });

    final Runnable r =
        new Runnable() {
          public void run() {}
        };
  }

  /** rfe 521323, detect whitespace before ';'. */
  void rfe521323() {
    doStuff();
    for (int i = 0; i < 5; i++) {}
  }

  /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class). */
  void bug806243() {
    Object o =
        new InputWhitespaceAroundBasic() {
          private int test;
        };
  }

  void doStuff() {}

  interface Foo {
    void foo();
  }

  /**
   * Avoid Whitespace violations in for loop.
   *
   * @author lkuehne
   * @version 1.0
   */
  class SpecialCasesInForLoop {
    void forIterator() {
      // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
      for (int i = 0; i++ < 5; ) {
        //                  ^ no whitespace
      }

      // bug 895072
      // avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
      int i = 0;
      for (; i < 5; i++) {
        //   ^ whitespace
      }
      for (int anInt : getSomeInts()) {
        // Should be ignored
      }
    }

    int[] getSomeInts() {
      int i = 2 / 3;
      return null;
    }

    void forColon() {
      int[] ll = new int[10];
      for (int x : ll) {}

      for (int x : ll) {}
      for (int x : ll) {}
      for (int x : ll) {} // ok
    }
  }

  /** Operators mentioned in Google Coding Standards 2016-07-12. */
  class NewGoogleOperators {
    NewGoogleOperators() {
      Runnable l;

      l = () -> {};
      l = () -> {};

      l = () -> {}; // ok
      l = () -> {}; // ok

      java.util.Arrays.sort(null, String::compareToIgnoreCase);
      java.util.Arrays.sort(null, String::compareToIgnoreCase);

      new Object().toString();
      new Object().toString();
    }
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputWhitespaceAroundEmptyTypesAndCycles {

  private void foo() {
    int i = 0;
    String[][] x = {{"foo"}};
    for (int first = 0; first < 5; first++) {} // ok
    int j = 0;
    while (j == 1) {} // ok
    do {} while (i == 1); // ok
  }

  enum EmptyFooEnum {} // ok

  interface SupplierFunction<T> extends Function<Supplier<T>, T> {} // ok

  class EmptyFoo {} // ok
}

// we need these interfaces for generics
// violation below 'Top-level class ExtraFoo has to reside in its own source file.'
interface ExtraFoo {}

// violation below 'Top-level class ExtraFoo2 has to reside in its own source file.'
interface ExtraFoo2 {}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputWhitespaceAroundGenerics {}

// No whitespace after commas
// violation below 'Top-level class ExtraBadCommas has to reside in its own source file.'
class ExtraBadCommas<A, B, C extends Map<A, String>> {
  private final java.util.Hashtable<Integer, Foo> test = new java.util.Hashtable<Integer, Foo>();
}

// violation below 'Top-level class ExtraWildcard has to reside in its own source file.'
class ExtraWildcard {
  public static void foo(Collection<? extends Wildcard[]> collection) {

    // A statement is important in this method to flush out any
    // issues with parsing the wildcard in the signature
    collection.size();
  }

  public static void foo2(Collection<? extends Wildcard[]> collection) {
    // A statement is important in this method to flush out any
    // issues with parsing the wildcard in the signature
    collection.size();
  }
}
