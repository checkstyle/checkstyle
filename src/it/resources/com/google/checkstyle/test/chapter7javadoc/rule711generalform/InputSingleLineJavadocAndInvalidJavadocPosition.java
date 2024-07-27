package // violation 'package statement should not be line-wrapped.'
    /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
    com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
import java.lang.String;

/** some javadoc. */
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

  /** @inheritDoc */
  // 3 violations above:
  //  'Javadoc tag '@inheritDoc' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  void foo7() {}

  /** {@inheritDoc} */
  void foo8() {}

  /** @customTag */
  // 3 violations above:
  //  'Javadoc tag '@customTag' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  void bar() {}

  /**
   * summary.
   *
   * <h1>Some header </h1>
   *
   * {@inheritDoc} {@code bar1} text
   */
  void bar2() {}

  /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a>text */
  // 3 violations above:
  //  'Javadoc tag '@customTag' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
  void bar3() {}

  /** Single line Javadoc that references {@link String}. */
  void bar4() {}

  /** @return in single line javadoc */
  // 3 violations above:
  //  'Javadoc tag '@return' should be preceded with an empty line.'
  //  'Single-line Javadoc comment should be multi-line.'
  //  'Summary javadoc is missing.'
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

/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
/** valid javadoc. */
class InputInvalidJavadocPosition {
  // violation above '.* InputInvalidJavadocPosition has to reside in its own source file.'
  /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
}

/** valid javadoc. */
/* ignore */
class InputInvalidJavadocPosition2 {
  // violation above '.* InputInvalidJavadocPosition2 has to reside in its own source file.'
  /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  /** valid javadoc. */
  int field1;

  /** valid javadoc. */
  int[] field2;

  /** valid javadoc. */
  public int[] field3;

  /** valid javadoc. */
  @Deprecated int field4;

  int
      /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
      field20;
  int field21
      /** invalid javadoc. */  // violation 'Javadoc comment is placed in the wrong location.'
      ;
  @Deprecated
  /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
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

  void
      /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
      method20() {}

  void method21
      /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  () {} // violation ''(' should be on the previous line.'

  void method22(
      /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  ) {}

  void method23()
      /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
    {}

  void method24() {
    /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  }

  void method25() {
    /** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
    int variable;
  }
}

// violation below '.* InputInvalidJavadocPosition3 has to reside in its own source file.'
@Deprecated
/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition3 {}

// violation 2 lines below '.* InputInvalidJavadocPosition4 has to reside in its own source file.'
/** valid javadoc. */
@Deprecated
class InputInvalidJavadocPosition4 {}

// violation below '.* InputInvalidJavadocPosition5 has to reside in its own source file.'
class
/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
InputInvalidJavadocPosition5 {}
// violation above ''InputInvalidJavadocPosition5' has incorrect indentation .*, expected .* 4.'

// violation below '.* InputInvalidJavadocPosition6 has to reside in its own source file.'
class InputInvalidJavadocPosition6
/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  {} // violation ''}' at column 4 should be alone on a line.'
/** invalid javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
