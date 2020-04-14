package com.puppycrawl.tools.checkstyle.checks.javadoc.missingleadingasterisk;

/** Config = default */
public class InputMissingLeadingAsterisk {
  /**
   ** OK
   *
       violation
   violation

   **/
  public void test() {}

  /**
   * Some description here.
   * @param a description of the parameter.
   */
  public void testWithParam(int a) {}

  /**
   * Some description here.
   * @param a description of the parameter.
     @param b description of the parameter.
   */
  public void testWithInvalidParam(int a, int b) {}

  /**
   * Some description here.
     @throws Exception
     @return some description here
   */
  public void testWithThrows() throws Exception {}

  /**
   * Some description here.
     @return some description here.
   */
  public int testWithReturnsTag() { return -1; }

  /**
   * Some description here.
   * <p>
   *  Some HTML content here.
   * <p>
   */
  public void testWithHTML() {}

  /**
   * Some description here.
   * <p>
     Some HTML content here.
   * </p>
   */
  public void testWithIncorrectHTML() {}

  /**
   * Some description here.
+  * A description with some character than
   */
  public void testWithInvalidFormat() {}

  /**
   * Some description here.
   << * Another line of description.
   */
  public void testWithSomeOtherLeadingSymbol() {}

  /** This is an inline comment */
  public void test2() {}

  /*
      This is a non javadoc comment.
   */
  /**/
  /***/
}
