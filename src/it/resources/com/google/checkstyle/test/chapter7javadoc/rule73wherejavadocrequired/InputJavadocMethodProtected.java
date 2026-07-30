package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/** Some javadoc. */
public class InputJavadocMethodProtected {

  /**
   * Some javadoc.
   *
   * @param b this parameter doesn't exist
   */
  protected void foo(int a) {}

  // violation 4 lines above 'Unused @param tag for 'b''

  /**
   * Some javadoc.
   *
   * @param a this parameter do exist
   */
  protected void foo1(int a) {}

  /**
   * Some javadoc.
   *
   * @param b parameter exist
   * @param c paramter doesn't exist
   */
  protected void foo2(int b) {}

  // violation 4 lines above 'Unused @param tag for 'c''

  /**
   * Some javadoc.
   *
   * @param b parameter exist
   */
  protected void foo3(int b) {}
}
