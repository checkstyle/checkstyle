package // violation 'package statement should not be line-wrapped.'
    // violation below 'Javadoc comment is placed in the wrong location.'
    /** invalid javadoc. */
    com.google.checkstyle.test.chapter7javadoc.rule711generalform;

// violation below 'Javadoc comment is placed in the wrong location.'
/** invalid javadoc. */
import javax.swing.JFrame;

/** Some javadoc. */
public class InputSingleLineJavadocAndInvalidJavadocPosition {

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
  void foo1() {}

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
  void foo2() {}

  /** @throws CheckstyleException if a problem occurs */
  // 3 violations above:
  //  'Javadoc tag '@throws' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  void foo3() {}

  /**
   * summary.
   *
   * @throws CheckstyleException if a problem occurs
   */
  void foo4() {}

  /** An especially short bit of Javadoc. */
  void foo5() {}

  /** An especially short bit of Javadoc. */
  void foo6() {}

  // 3 violations 4 lines below:
  //  'Javadoc tag '@inheritDoc' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  /** @inheritDoc */
  void foo7() {}

  /** {@inheritDoc} */
  void foo8() {}

  // 3 violations 4 lines below:
  //  'Javadoc tag '@customTag' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  /** @customTag */
  void bar() {}

  /**
   * summary.
   *
   * <h1>Some header </h1>
   *
   * {@inheritDoc} {@code bar1} text
   */
  void bar2() {}

  // 3 violations 4 lines below:
  //  'Javadoc tag '@customTag' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a>text */
  void bar3() {}

  /** Single line Javadoc that references {@link String}. */
  void bar4() {}

  // 3 violations 4 lines below:
  //  'Javadoc tag '@return' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  /** @return in single line javadoc */
  int bar5() {
    return 0;
  }

  /**
   * summary.
   *
   * @return in multi line javadoc
   */
  int bar6() {
    return 0;
  }
}

// violation below 'Javadoc comment is placed in the wrong location.'
/** invalid javadoc. */
/** valid javadoc. */
class InputInvalidJavadocPosition {
  // violation above '.* InputInvalidJavadocPosition has to reside in its own source file.'
  // violation below 'Javadoc comment is placed in the wrong location.'
  /** invalid javadoc. */
}

/** valid javadoc. */
/* ignore */
class InputInvalidJavadocPosition2 {
  // violation above '.* InputInvalidJavadocPosition2 has to reside in its own source file.'
  // violation below 'Javadoc comment is placed in the wrong location.'
  /** invalid javadoc. */
  static {
    /* ignore */
  }

  // violation below 'Javadoc comment is placed in the wrong location.'
  /** invalid javadoc. */
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
      /** invalid javadoc. */
      field20;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  int field21
      /** invalid javadoc. */
      ;

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  @Deprecated
  /** invalid javadoc. */
  JFrame frame = new JFrame();

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
      /** invalid javadoc. */
      method20() {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method21
  /** invalid javadoc. */
  () {} // violation ''(' should be on the previous line.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method22(
  /** invalid javadoc. */
  ) {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method23()
    /** invalid javadoc. */
    {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method24() {
    /** invalid javadoc. */
  }

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method25() {
    /** invalid javadoc. */
    int variable;
  }
}

// violation 2 lines below '.* InputInvalidJavadocPosition3 has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
@Deprecated
/** invalid javadoc. */
class InputInvalidJavadocPosition3 {}

// violation 2 lines below '.* InputInvalidJavadocPosition4 has to reside in its own source file.'
/** valid javadoc. */
@Deprecated
class InputInvalidJavadocPosition4 {}

// violation 2 lines below '.* InputInvalidJavadocPosition5 has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class
/** invalid javadoc. */
InputInvalidJavadocPosition5 {}
// violation above ''InputInvalidJavadocPosition5' has incorrect indentation .* 0, expected .* 4.'

// violation 2 lines below '.* InputInvalidJavadocPosition6 has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition6
  /** invalid javadoc. */
  {}
/** invalid javadoc. */
// violation 2 lines above''}' at column 4 should be alone on a line.'
// violation 2 lines above 'Javadoc comment is placed in the wrong location.'
