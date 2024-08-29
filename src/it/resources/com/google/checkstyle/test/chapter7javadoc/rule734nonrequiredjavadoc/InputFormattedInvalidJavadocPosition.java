package // violation 'package statement should not be line-wrapped.'
    /** odd javadoc */
    // violation above 'Javadoc comment is placed in the wrong location.'
    com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
import javax.swing.JFrame;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
/** valid javadoc. */
class InputFormattedInvalidJavadocPosition {
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}

/** valid javadoc. */
/* ignore */
class ExtraInputInvalidJavadocPosition2 {
  // violation above '.* ExtraInputInvalidJavadocPosition2 has to reside in its own source file.'

  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  // violation below 'Javadoc comment is placed in the wrong location.'
  /** odd javadoc */
  /** valid javadoc. */
  int field1;

  /** valid javadoc. */
  JFrame frame = new JFrame();

  /** valid javadoc. */
  public int[] field3;

  /** valid javadoc. */
  @Deprecated int field4;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  int
      /** odd javadoc */
      field20;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  int field21
      /** odd javadoc */
      ;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  @Deprecated
  /** odd javadoc */
  int field22;

  void method1() {}

  /** valid javadoc. */
  void method2() {}

  /** valid javadoc. */
  <T> T method3() {
    return null;
  }

  /** valid javadoc. */
  String[] method4() {
    return null;
  }

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void
      /** odd javadoc */
      method20() {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method21
      /** odd javadoc */
      () {}

  // 2 violations 2 lines above:
  //  '.* incorrect indentation level 6, expected level should be 2.'
  //  ''(' should be on the previous line.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method22(
      /** odd javadoc */
      ) {} // violation '.* incorrect indentation level 6, expected level should be 2.'

  // 2 violations 4 lines below:
  //  '.* indentation should be the same level as line 97.'
  //  'Javadoc comment is placed in the wrong location.'
  void method23()
        /** odd javadoc */
      {} // violation '.* has incorrect indentation level 6, expected level should be 4.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method24() {
    /** odd javadoc */
  }

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method25() {
    /** odd javadoc */
    int variable;
  }

  @Deprecated
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
  class InputInvalidJavadocPosition3 {}

  /** valid javadoc. */
  @Deprecated
  class InputInvalidJavadocPosition4 {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  class
  /** odd javadoc */
  InputInvalidJavadocPosition5 {}

  // violation 2 lines above '.* incorrect indentation .* 2, expected .* 6.'

  /* extra violation 4 line below until https://github.com/google/google-java-format/issues/1126 */
  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  class InputInvalidJavadocPosition6
  /** odd javadoc */
  {}
  // 2 violations above:
  //  ''class def lcurly' has incorrect indentation level 2, expected level should be 4.'
  //  ''}' at column 4 should be alone on a line.'
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}
