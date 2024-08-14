// non-compiled with javac: Compilable with Java17

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

/** Some javadoc. */
public class InputOverloadsNeverSplitRecords {
  /** Some javadoc. */
  public record MyRecord1(int x, int y) {
    public MyRecord1(int a) {
      this(a, a);
    }

    void foo() {}

    void foo2() {}

    public MyRecord1 {} // violation 'Constructors should be grouped together.*'

    public MyRecord1(int a, int b, int c, int d) {
      // violation above 'Constructors should be grouped together.*'
      this(a + b, c + d);
    }

    public MyRecord1(int x, int y, int z) {
      // violation above 'Constructors should be grouped together.*'
      this(x + y, z);
    }
  }

  class MyClass {
    int xyz = 20;

    MyClass() {}

    MyClass(String s) {}

    String[] str;

    String[] str2;

    MyClass(int a) {} // violation 'Constructors should be grouped together.*'
  }

  /** Some javadoc. */
  public record MyRecord2(double d) {
    public MyRecord2(double a, double b, double c) {
      this(a + b + c);
    }

    public MyRecord2 {}

    public MyRecord2(double a, double b) {
      this(a + b);
    }
  }

  /** Some javadoc. */
  public record MyRecord3(float f) {
    public MyRecord3(float a, float b, float c) {
      this(a + b + c);
    }
  }

  /** Some javadoc. */
  public record MyRecord4(String str) {
    public MyRecord4 {}
  }

  /** Some javadoc. */
  public record MyRecord5(long l) {
    void test() {}

    void test2() {}

    void test3() {}
  }

  /** Some javadoc. */
  public record MyRecord6(String str, int x) {}

  public void overloadMethod(int i) {
    // some foo code
  }

  public void overloadMethod(String s) {
    // some foo code
  }

  public void overloadMethod(boolean b) {
    // some foo code
  }

  public void fooMethod() {}

  // violation below 'All overloaded methods should be placed next to each other. .* '89'
  public void overloadMethod(String s, Boolean b, int i) {
    // some foo code
  }
}
