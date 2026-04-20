package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/** Some javadoc. */
public class InputJavadocMethodProtectedScopeCorrect {

  /**
   * Process data.
   *
   * @param actual this parameter exist
   */
  protected void processData(int actual) {}

  /**
   * Process data.
   *
   * @param actual this parameter exist
   */
  public void processDataPublic(int actual) {}
}
