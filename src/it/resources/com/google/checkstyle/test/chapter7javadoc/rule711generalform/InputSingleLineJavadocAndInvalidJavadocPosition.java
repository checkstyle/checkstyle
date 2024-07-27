package // violation 'package statement should not be line-wrapped.'
    /** warn. */
    com.google.checkstyle.test.chapter7javadoc.rule711generalform;
    
// violation 3 lines above 'Javadoc comment is placed in the wrong location.'

/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
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

  /** {@inheritDoc}. */
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

/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
/** valid. */
class InputInvalidJavadocPosition {
  // violation above '.* InputInvalidJavadocPosition has to reside in its own source file.'
  /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
}

/** valid. */
/* ignore */
class InputInvalidJavadocPosition2 {
  // violation above '.* InputInvalidJavadocPosition2 has to reside in its own source file.'
  /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  /** valid. */
  int field1;

  /** valid. */
  int[] field2;

  /** valid. */
  public int[] field3;

  /** valid. */
  @Deprecated int field4;

  int
      /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
      field20;
  int field21
      /** warn. */  // violation 'Javadoc comment is placed in the wrong location.'
      ;
  @Deprecated
  /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  int field22;

  void method1() {}

  /** valid. */
  void method2() {}

  /** valid. */
  <T> T method3() {
    return null;
  }

  /** valid. */
  String[] method4() {
    return null;
  }

  void
      /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
      method20() {}

  void method21
      /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  () {} // violation ''(' should be on the previous line.'

  void method22(
      /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  ) {}

  void method23()
      /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
    {}

  void method24() {
    /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  }

  void method25() {
    /** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
    int variable;
  }
}

// violation below '.* InputInvalidJavadocPosition3 has to reside in its own source file.'
@Deprecated
/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition3 {}

// violation 2 lines below '.* InputInvalidJavadocPosition4 has to reside in its own source file.'
/** valid. */
@Deprecated
class InputInvalidJavadocPosition4 {}

// violation below '.* InputInvalidJavadocPosition5 has to reside in its own source file.'
class
/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
InputInvalidJavadocPosition5 {}
// violation above ''InputInvalidJavadocPosition5' has incorrect indentation .*, expected .* 4.'

// violation below '.* InputInvalidJavadocPosition6 has to reside in its own source file.'
class InputInvalidJavadocPosition6
/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
  {} // violation ''}' at column 4 should be alone on a line.'
/** warn. */ // violation 'Javadoc comment is placed in the wrong location.'
