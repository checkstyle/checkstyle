package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/**
 * This is a Javadoc comment.
 */
public class InputMissingJavadocTypeCheckNoViolations { // OK
  private int x; // OK
  int y; // OK

  /**
   * This is a Javadoc comment.
   */
  public class InnerPublic implements MyInterfacePublic { // OK

  }

  /**
   * This is a Javadoc comment.
   */
  public enum MyEnumPublic { // OK

  }

  /**
   * This is a Javadoc comment.
   */
  public interface MyInterfacePublic {  // OK

    /**
     * This is a Javadoc comment.
     */
    class MyInterfaceClass {}  // OK
  }

  /**
   * This is a Javadoc comment.
   */
  public @interface MyAnnotationPublic { // OK

  }

  /**
   * This is a Javadoc comment.
   */
  protected class InnerProtected { // OK

  }

  /**
   * This is a Javadoc comment.
   */
  protected enum MyEnumProtected { // OK

  }

  /**
   * This is a Javadoc comment.
   */
  protected interface MyInterfaceProtected {  // OK

  }

  /**
   * This is a Javadoc comment.
   */
  protected @interface MyAnnotationProtected { // OK

  }

  class Inner { // OK

  }

  enum MyEnum { // OK

  }

  interface MyInterface {  // OK

  }

  @interface MyAnnotation { // OK

  }

  private class InnerPrivate { // OK

  }

  private enum MyEnumPrivate { // OK

  }

  private interface MyInterfacePrivate {  // OK

  }

  private @interface MyAnnotationPrivate { // OK

  }

  public void myMethod() {
    class MyMethodClass {} // OK
  }
}
