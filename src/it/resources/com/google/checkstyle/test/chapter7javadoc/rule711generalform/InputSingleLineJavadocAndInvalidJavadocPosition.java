package
    /** warn */
    com.google.checkstyle.test.chapter7javadoc.rule711generalform;
// violation 2 lines above 'Javadoc comment is placed in the wrong location.'

/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
import java.lang.String;

public class InputSingleLineJavadocAndInvalidJavadocPosition {

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
  void foo1() {}

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)} */
  void foo2() {}

  /** @throws CheckstyleException if a problem occurs */
  // violation above 'Single-line Javadoc comment should be multi-line.'
  void foo3() {}

  /**
   * @throws CheckstyleException if a problem occurs
   */
  void foo4() {}

  /** An especially short bit of Javadoc. */
  void foo5() {}

  /** An especially short bit of Javadoc. */
  void foo6() {}

  /** @inheritDoc */
  // violation above 'Single-line Javadoc comment should be multi-line.'
  void foo7() {}

  /** {@inheritDoc} */
  void foo8() {}

  /** @customTag */
  // violation above 'Single-line Javadoc comment should be multi-line.'
  void bar() {}

  /**
   *
   *
   * <h1>Some header </h1>
   *
   * {@inheritDoc} {@code bar1} text
   */
  void bar2() {}

  /** @customTag <a> href="https://github.com/checkstyle/checkstyle/"</a>text */
  // violation above 'Single-line Javadoc comment should be multi-line.'
  void bar3() {}

  /** Single line Javadoc that references {@link String}. */
  void bar4() {}

  /** @return in single line javadoc */
  // violation above 'Single-line Javadoc comment should be multi-line.'
  int bar5() {
    return 0;
  }

  /**
   * @return in multi line javadoc
   */
  int bar6() {
    return 0;
  }
}

/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
/** valid */
class InputInvalidJavadocPosition {
  /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
}

/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
  /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
  /** valid */
  int field1;

  /** valid */
  int[] field2;

  /** valid */
  public int[] field3;

  /** valid */
  @Deprecated int field4;

  int
      /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
      field20;
  int field21
      /** warn */  // violation 'Javadoc comment is placed in the wrong location.'
      ;
  @Deprecated
  /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
  int field22;

  void method1() {}

  /** valid */
  void method2() {}

  /** valid */
  <T> T method3() {
    return null;
  }

  /** valid */
  String[] method4() {
    return null;
  }

  void
      /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
      method20() {}

  void method21
      /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
      () {}

  void method22(
      /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
      ) {}

  void method23()
      /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
      {}

  void method24() {
    /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
  }

  void method25() {
    /** warn */ // violation 'Javadoc comment is placed in the wrong location.'
    int variable;
  }
}

@Deprecated
/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition3 {}

/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}

class
/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
InputInvalidJavadocPosition5 {}

class InputInvalidJavadocPosition6
/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
 {}
/** warn */ // violation 'Javadoc comment is placed in the wrong location.'
