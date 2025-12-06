package // violation 'package statement should not be line-wrapped.'
    /** Odd javadoc */ // violation 'Javadoc comment is placed in the wrong location.'
    com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

// violation below 'Javadoc comment is placed in the wrong location.'
/** Odd javadoc */
import javax.swing.JFrame;

// violation below 'Javadoc comment is placed in the wrong location.'
/** Odd javadoc */
/** Valid javadoc. */
class InputInvalidJavadocPosition {
  /** Odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}

/** Valid javadoc. */
/* ignore */
class InputInvalidJavadocPosition2 {
  // violation above '.* InputInvalidJavadocPosition2 has to reside in its own source file.'

  /** Odd javadoc */ // violation 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  // violation below 'Javadoc comment is placed in the wrong location.'
  /** Odd javadoc */
  /** Valid javadoc. */
  int field1;

  /** Valid javadoc. */
  JFrame frame = new JFrame();

  /** Valid javadoc. */
  public int[] field3;

  /** Valid javadoc. */
  @Deprecated int field4;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  int
      /** Odd javadoc */
      field20;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  int field21
      /** Odd javadoc */;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  @Deprecated
  /** Odd javadoc */
  int field22;

  void method1() {}

  /** Valid javadoc. */
  void method2() {}

  /** Valid javadoc. */
  <T> T method3() {
    return null;
  }

  /** Valid javadoc. */
  String[] method4() {
    return null;
  }

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void
      /** Odd javadoc */
      method20() {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method21
  /** Odd javadoc */
  () {} // violation ''(' should be on the previous line.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method22(
  /** Odd javadoc */
  ) {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method23()
    /** Odd javadoc */
    {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method24() {
    /** Odd javadoc */
  }

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method25() {
    /** Odd javadoc */
    int variable;
  }

  @Deprecated
  /** Odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
  class InputInvalidJavadocPosition3 {}

  /** Valid javadoc. */
  @Deprecated
  class InputInvalidJavadocPosition4 {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  class
  /** Odd javadoc */
  InputInvalidJavadocPosition5 {}
  // violation above ''InputInvalidJavadocPosition5' has incorrect indentation .* 2, expected .* 6.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  class InputInvalidJavadocPosition6
    /** Odd javadoc */
    {} // violation ''}' at column 6 should be alone on a line.'
  /** Odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}
