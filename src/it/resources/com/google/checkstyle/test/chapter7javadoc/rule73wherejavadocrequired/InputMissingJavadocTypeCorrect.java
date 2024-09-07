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

  /** some javadoc. */
  public void myMethod() {
    class MyMethodClass {}
  }

  /** some javadoc. */
  protected int testProtectedMethod1() {
    return 0;
  }

  /** some javadoc. */
  protected void testProtectedMethod2() {}

  /** some javadoc. */
  public void testPublicMethod() {}

  private void testPrivateMethod() {}

  void testPackagePrivateMethod() {}

  /** some javadoc. */
  protected InputMissingJavadocTypeCorrect() {}

  /** some javadoc. */
  public InputMissingJavadocTypeCorrect(String arg) {}

  private InputMissingJavadocTypeCorrect(int arg) {}

  InputMissingJavadocTypeCorrect(double arg) {}

  /** some javadoc. */
  protected @interface ProtectedAnnotation {}

  /** some javadoc. */
  public @interface PublicAnnotation {}

  private @interface PrivateAnnotation {}

  @interface PackagePrivateAnnotation {}

  /** some javadoc. */
  protected class InnerProtected {
    protected void testProtectedInnerMethod() {}

    public void testPublicInnerMethod() {}

    private void testPrivateInnerMethod() {}

    void testPackagePrivateInnerMethod() {}

    protected InnerProtected() {}

    InnerProtected(String arg) {}

    private InnerProtected(double arg) {}
  }

  /** some javadoc. */
  public enum MyEnumPublic2 {
    TEST;

    /** some javadoc. */
    public void testPublicEnumMethod() {}

    /** some javadoc. */
    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumPublic2() {}

    MyEnumPublic2(String arg) {}
  }

  /** some javadoc. */
  protected enum MyEnumProtected {
    TEST;

    public void testPublicEnumMethod() {}

    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumProtected() {}

    MyEnumProtected(String arg) {}
  }

  /** some javadoc. */
  protected interface MyInterfaceProtected {
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    void testPackagePrivateInterfaceMethod();
  }

  /** some javadoc. */
  public interface MyInterfacePublic2 {
    /** some javadoc. */
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    /** some javadoc. */
    void testPackagePrivateInterfaceMethod();
  }
}
