/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodParamsTags {

  // violation 16 lines below 'Unused @param tag for 'unexpectedParam'.'
  // violation 16 lines below 'Unused @param tag for 'unexpectedParam2'.'
  // violation 17 lines below 'Unused @param tag for 'unexpectedParam3'.'
  // violation 17 lines below 'Unused @param tag for 'unexpectedParam4'.'
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
   * @param unexpectedParam4
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
  // violation 2 lines above 'Unused @param tag for 't'.'
  public void setPathRef4(Object w) { // violation 'Expected @param tag for 'w'.'

  }

  /** @param z*/
  public void setPathRef5(Object z) {

  }

  /** @param x*/
  // violation above 'Unused @param tag for 'x'.'
  public void setPathRef6(Object y) { // violation 'Expected @param tag for 'y'.'

  }

}
