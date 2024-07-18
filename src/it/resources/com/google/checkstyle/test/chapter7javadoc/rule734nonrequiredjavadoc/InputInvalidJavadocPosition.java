package
    /** odd javadoc */
    com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;
// violation 2 lines above 'Javadoc comment is placed in the wrong location.'

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
import java.lang.String;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
/** valid */
class InputInvalidJavadocPosition {
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
}

/** valid */
/* ignore */
class InputInvalidJavadocPosition2 {
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'
  static {
    /* ignore */
  }

  // violation below 'Javadoc comment is placed in the wrong location.'
  /** odd javadoc */
  /** valid */
  int field1;

  /** valid */
  int[] field2;

  /** valid */
  public int[] field3;

  /** valid */
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

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void
      /** odd javadoc */
      method20() {}

  // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
  void method21
      /** odd javadoc */
      () {}

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
}

@Deprecated
/** odd javadoc */
// violation above 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition3 {}

/** valid */
@Deprecated
class InputInvalidJavadocPosition4 {}

// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class
/** odd javadoc */
InputInvalidJavadocPosition5 {}

// violation 2 lines below 'Javadoc comment is placed in the wrong location.'
class InputInvalidJavadocPosition6
/** odd javadoc */
 {}
/** odd javadoc */
// violation above 'Javadoc comment is placed in the wrong location.'
