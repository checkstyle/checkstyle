package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodParamsTags {

  /**
   * Config: default
   *
   * A method with an undocumented param and a missing param.
   * Checkstyle should at least
   * missingParam, ideally would be nice to have a flag to report
   * also undocumentedParam
   *
   * @param param1 some text // ok
   * @param param2 // ok
   *                some text for param2 (without space at the end of line)
   * @param param3                // ok
   *                some text for param3 (with space at the end of line)
   *
   * @param unexpectedParam // violation
   * @param unexpectedParam2 // violation
   *                some text for unexpectedParam2 (without space in the end of line)
   *        @param unexpectedParam3 // violation
     @param unexpectedParam4 // violation
   */
  void testEmpty(Object param1, Object param2, Object param3) {

  }

  /**
   * Set the path to use by reference.
   *
   * @param r
   *            a reference to an
               existing path */
  public void setPathRef(Object r) { // ok

  }

  /** @param s
   a reference to an existing path */
  public void setPathRef2(Object s) { // ok

  }

  /** @param k
    */
  public void setPathRef3(Object k) { // ok

  }

  /** @param t
   */
  public void setPathRef4(Object w) { // violation

  }

  /** @param z*/
  public void setPathRef5(Object z) { // ok

  }

  /** @param x*/      // violation
  public void setPathRef6(Object y) { // violation

  }

}
