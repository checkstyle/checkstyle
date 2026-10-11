package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/** This is a Javadoc comment. */
public class InputMissingJavadocTypeCorrect {
  private int myInt;
  int myOtherInt;

  /** This is a Javadoc comment. */
  public class InnerPublic implements MyInterfacePublic2 {
    @Override
    public void testPublicInterfaceMethod() {}

    @Override
    public void testPackagePrivateInterfaceMethod() {}
  }

  /** This is a Javadoc comment. */
  public enum MyEnumPublic {}

  /** This is a Javadoc comment. */
  public interface MyInterfacePublic {

    /** This is a Javadoc comment. */
    class MyInterfaceClass {}
  }

  /** This is a Javadoc comment. */
  public @interface MyAnnotationPublic {}

  /** This is a Javadoc comment. */
  protected @interface MyAnnotationProtected {}

  @interface MyAnnotationPackagePrivate {}

  class Inner {}

  enum MyEnum {}

  interface MyInterface {}

  @interface MyAnnotation {}

  private class InnerPrivate {}

  private enum MyEnumPrivate {}

  private interface MyInterfacePrivate {}

  private @interface MyAnnotationPrivate {}

  /** Some javadoc. */
  public void myMethod() {
    class MyMethodClass {}
  }

  /** Some javadoc. */
  protected int testProtectedMethod1() {
    return 0;
  }

  /** Some javadoc. */
  protected void testProtectedMethod2() {}

  /** Some javadoc. */
  public void testPublicMethod() {}

  private void testPrivateMethod() {}

  void testPackagePrivateMethod() {}

  /** Some javadoc. */
  protected InputMissingJavadocTypeCorrect() {}

  /** Some javadoc. */
  public InputMissingJavadocTypeCorrect(String arg) {}

  private InputMissingJavadocTypeCorrect(int arg) {}

  InputMissingJavadocTypeCorrect(double arg) {}

  /** Some javadoc. */
  protected @interface ProtectedAnnotation {}

  /** Some javadoc. */
  public @interface PublicAnnotation {}

  private @interface PrivateAnnotation {}

  @interface PackagePrivateAnnotation {}

  /** Some javadoc. */
  protected class InnerProtected {
    protected void testProtectedInnerMethod() {}

    public void testPublicInnerMethod() {}

    private void testPrivateInnerMethod() {}

    void testPackagePrivateInnerMethod() {}

    protected InnerProtected() {}

    InnerProtected(String arg) {}

    private InnerProtected(double arg) {}
  }

  /** Some javadoc. */
  public enum MyEnumPublic2 {
    TEST;

    /** Some javadoc. */
    public void testPublicEnumMethod() {}

    /** Some javadoc. */
    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumPublic2() {}

    MyEnumPublic2(String arg) {}
  }

  /** Some javadoc. */
  protected enum MyEnumProtected {
    TEST;

    public void testPublicEnumMethod() {}

    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumProtected() {}

    MyEnumProtected(String arg) {}
  }

  /** Some javadoc. */
  protected interface MyInterfaceProtected {
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    void testPackagePrivateInterfaceMethod();
  }

  /** Some javadoc. */
  public interface MyInterfacePublic2 {
    /** Some javadoc. */
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    /** Some javadoc. */
    void testPackagePrivateInterfaceMethod();
  }
}
