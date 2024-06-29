package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/**
 * This is a Javadoc comment.
 */
public class InputMissingJavadocTypeCheckNoViolations {
  private int myInt;
  int myOtherInt;

  /**
   * This is a Javadoc comment.
   */
  public class InnerPublic implements MyInterfacePublic {

  }

  /**
   * This is a Javadoc comment.
   */
  public enum MyEnumPublic {

  }

  /**
   * This is a Javadoc comment.
   */
  public interface MyInterfacePublic {

    /**
     * This is a Javadoc comment.
     */
    class MyInterfaceClass {}
  }

  /**
   * This is a Javadoc comment.
   */
  public @interface MyAnnotationPublic {

  }

  /**
   * This is a Javadoc comment.
   */
  protected class InnerProtected {

  }

  /**
   * This is a Javadoc comment.
   */
  protected enum MyEnumProtected {

  }

  /**
   * This is a Javadoc comment.
   */
  protected interface MyInterfaceProtected {

  }

  /**
   * This is a Javadoc comment.
   */
  protected @interface MyAnnotationProtected {

  }

  class Inner {

  }

  enum MyEnum {

  }

  interface MyInterface {

  }

  @interface MyAnnotation {

  }

  private class InnerPrivate {

  }

  private enum MyEnumPrivate {

  }

  private interface MyInterfacePrivate {

  }

  private @interface MyAnnotationPrivate {

  }

  public void myMethod() {
    class MyMethodClass {}
  }
}
