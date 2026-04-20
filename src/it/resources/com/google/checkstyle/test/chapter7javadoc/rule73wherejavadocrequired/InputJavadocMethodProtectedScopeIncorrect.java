package com.google.checkstyle.test.chapter7javadoc.rule73wherejavadocrequired;

/** Some javadoc. */
public class InputJavadocMethodProtectedScopeIncorrect {

  /**
   * Process data.
   *
   * @param nonExistent this parameter does not exist
   */
  // violation 2 lines above 'Unused @param tag for 'nonExistent'.'
  protected void processData(int actual) {}

  /**
   * Process data.
   *
   * @param nonExistent this parameter does not exist
   */
  // violation 2 lines above 'Unused @param tag for 'nonExistent'.'
  public void processDataPublic(int actual) {}
}
