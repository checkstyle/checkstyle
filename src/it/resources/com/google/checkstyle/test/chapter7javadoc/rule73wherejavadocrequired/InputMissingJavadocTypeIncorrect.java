package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

// violation below 'Missing a Javadoc comment.'
public class InputMissingJavadocTypeIncorrect {

  // violation below 'Missing a Javadoc comment.'
  public class InnerPublic implements MyInterfacePublic {
    @Override
    public void testPublicInterfaceMethod() {}

    @Override
    public void testPackagePrivateInterfaceMethod() {}
  }

  // violation below 'Missing a Javadoc comment.'
  public enum MyEnum {}

  // violation below 'Missing a Javadoc comment.'
  public interface MyInterface {
    // violation below 'Missing a Javadoc comment.'
    class MyInterfaceClass {}
  }

  // violation below 'Missing a Javadoc comment.'
  public @interface MyAnnotation {}

  // violation below 'Missing a Javadoc comment.'
  protected @interface MyAnnotationProtected {}

  /** Some javadoc. */
  public void myMethod() {
    class MyMethodClass {}
  }

  // ok, not public
  class AdditionalClass {}

  // violation below 'Missing a Javadoc comment.'
  protected int testProtectedMethod1() {
    return 0;
  }

  // violation below 'Missing a Javadoc comment.'
  protected void testProtectedMethod2() {}

  // violation below 'Missing a Javadoc comment.'
  protected InputMissingJavadocTypeIncorrect() {}

  // violation below 'Missing a Javadoc comment.'
  public class InnerClass {
    // violation below 'Missing a Javadoc comment.'
    public void testPublicInnerMethod() {}

    // violation below 'Missing a Javadoc comment.'
    protected void testProtectedInnerMethod() {}

    private void testPrivateInnerMethod() {}

    void testPackagePrivateInnerMethod() {}

    // violation below 'Missing a Javadoc comment.'
    public InnerClass(int arg) {}

    // violation below 'Missing a Javadoc comment.'
    protected InnerClass() {}

    InnerClass(String arg) {}

    private InnerClass(double arg) {}
  }

  // violation below 'Missing a Javadoc comment.'
  protected class InnerProtected {
    protected void testProtectedInnerMethod() {}

    public void testPublicInnerMethod() {}

    private void testPrivateInnerMethod() {}

    void testPackagePrivateInnerMethod() {}

    protected InnerProtected() {}

    InnerProtected(String arg) {}

    private InnerProtected(double arg) {}
  }

  // violation below 'Missing a Javadoc comment.'
  public enum MyEnumPublic {
    TEST;

    // violation below 'Missing a Javadoc comment.'
    public void testPublicEnumMethod() {}

    // violation below 'Missing a Javadoc comment.'
    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumPublic() {}

    MyEnumPublic(String arg) {}
  }

  // violation below 'Missing a Javadoc comment.'
  protected enum MyEnumProtected {
    TEST;

    public void testPublicEnumMethod() {}

    protected void testProtectedEnumMethod() {}

    private void testPrivateEnumMethod() {}

    void testPackagePrivateEnumMethod() {}

    private MyEnumProtected() {}

    MyEnumProtected(String arg) {}
  }

  // violation below 'Missing a Javadoc comment.'
  protected interface MyInterfaceProtected {
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    void testPackagePrivateInterfaceMethod();
  }

  // violation below 'Missing a Javadoc comment.'
  public interface MyInterfacePublic {
    // violation below 'Missing a Javadoc comment.'
    public void testPublicInterfaceMethod();

    private void testPrivateInterfaceMethod() {}

    // violation below 'Missing a Javadoc comment.'
    void testPackagePrivateInterfaceMethod();
  }
}
