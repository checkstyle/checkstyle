package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodParamsTags {

  /**
   * A method with an undocumented param and a missing param.
   * Checkstyle should at least
   * missingParam, ideally would be nice to have a flag to report
   * also undocumentedParam
   *
   * @param param1 some text
   * @param param2
   *                some text for param2 (without space at the end of line)
   * @param param3
   *                some text for param3 (with space at the end of line)
   *
   * @param unexpectedParam
   * @param unexpectedParam2
   *                some text for unexpectedParam2 (without space in the end of line)
   *        @param unexpectedParam3
     @param unexpectedParam4
   */
  void testEmpty(Object param1, Object param2, Object param3) {

  }

  /**
   * Set the path to use by reference.
   *
   * @param r
   *            a reference to an
               existing path */
  public void setPathRef(Object r) {

  }

  /** @param s
   a reference to an existing path */
  public void setPathRef2(Object s) {

  }

  /** @param k
    */
  public void setPathRef3(Object k) {

  }

  /** @param t
   */
  public void setPathRef4(Object w) {

  }

  /** @param z*/
  public void setPathRef5(Object z) {

  }

  /** @param x*/
  public void setPathRef6(Object y) {

  }

}
