package // violation 'package statement should not be line-wrapped.'
    // violation below 'Javadoc comment is placed in the wrong location.'
    /** invalid javadoc. */
    com.google.checkstyle.test.chapter7javadoc.rule711generalform;

// violation below 'Javadoc comment is placed in the wrong location.'
/** invalid javadoc. */
import javax.swing.JFrame;

/** Some javadoc. */
public class InputFormattedSingleLineJavadocAndInvalidJavadocPosition {

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
  void foo1() {}

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
  void foo2() {}

  // violation below 'Summary javadoc is missing.'
  /**
   * @throws CheckstyleException if a problem occurs
   */
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

  // violation below 'Summary javadoc is missing.'
  /**
   * @inheritDoc
   */
  void foo7() {}

  /** {@inheritDoc} */
  void foo8() {}

  // violation below 'Summary javadoc is missing.'
  /**
   * @customTag
   */
  void bar() {}

  /**
   * summary.
   *
   * <h1>Some header </h1>
   *
   * {@inheritDoc} {@code bar1} text
   */
  void bar2() {}

  // violation below 'Summary javadoc is missing.'
  /**
   * @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a>text
   */
  void bar3() {}

  /** Single line Javadoc that references {@link String}. */
  void bar4() {}

  // violation below 'Summary javadoc is missing.'
  /**
   * @return in single line javadoc
   */
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
class ExtraInputInvalidJavadocPosition {
  // violation above '.* ExtraInputInvalidJavadocPosition has to reside in its own source file.'
  // violation below 'Javadoc comment is placed in the wrong location.'
  /** invalid javadoc. */
}

/** valid javadoc. */
/* ignore */
class ExtraInputInvalidJavadocPosition2 {
  // violation above '.* ExtraInputInvalidJavadocPosition2 has to reside in its own source file.'
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
      () {}

  // 2 violations 2 lines above:
  //  ''method def lparen' has incorrect indentation level 6, expected .* 2.'
  //  ''(' should be on the previous line.'

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method22(
      /** invalid javadoc. */
      ) {}

  // violation 2 lines above ''method def rparen' has incorrect indentation level 6, expected .* 2.'

  // 2 violations 4 lines below:
  //  '.* indentation should be the same level as line 178.'
  //  'Javadoc comment is placed in the wrong location.'
  void method23()
        /** invalid javadoc. */
      {}

  // violation 2 lines above ''method def lcurly' has incorrect indentation level 6, expected .* 4.'

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

// violation 2 lines below '.* has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
@Deprecated
/** invalid javadoc. */
class ExtraInputInvalidJavadocPosition3 {}

// violation 2 lines below '.* has to reside in its own source file.'
/** valid javadoc. */
@Deprecated
class ExtraInputInvalidJavadocPosition4 {}

// violation 2 lines below '.* has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class
/** invalid javadoc. */
ExtraInputInvalidJavadocPosition5 {}

// violation 2 lines above '.* has incorrect indentation .* 0, expected .* 4.'

// violation 2 lines below '.* has to reside in its own source file.'
// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class ExtraInputInvalidJavadocPosition6
/** invalid javadoc. */
{}
/** invalid javadoc. */
// 2 violations 2 lines above:
//  ''class def lcurly' has incorrect indentation level 0, expected level should be 2.'
//  ''}' at column 2 should be alone on a line.'
// violation 4 lines above 'Javadoc comment is placed in the wrong location.'
