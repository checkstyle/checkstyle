package // violation 'package statement should not be line-wrapped.'
    /** odd javadoc */ // violation 'Javadoc comment is placed in the wrong location.'
    com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
import java.lang.String;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
/** valid javadoc. */
class InputInvalidJavadocPosition {
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}

/** valid javadoc. */
/* ignore */
class InputInvalidJavadocPosition2 {
  // violation above '.* InputInvalidJavadocPosition2 has to reside in its own source file.'

  /** odd javadoc */ // violation 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  // violation below 'Javadoc comment is placed in the wrong location.'
  /** odd javadoc */
  /** valid javadoc. */
  int field1;

  /** valid javadoc. */
  int[] field2;

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
      /** odd javadoc */;

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
  () {} // violation ''(' should be on the previous line.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method22(
  /** odd javadoc */
  ) {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method23()
    /** odd javadoc */
    {}

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
  // violation above ''InputInvalidJavadocPosition5' has incorrect indentation .* 2, expected .* 6.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  class InputInvalidJavadocPosition6
    /** odd javadoc */
    {} // violation ''}' at column 6 should be alone on a line.'
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}
