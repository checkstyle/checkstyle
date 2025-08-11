package com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
/**
 * valid javadoc.
 *
 * @param containerPath options
 * @param options path
 */
public record InputFormattedInvalidJavadocPositionRecord(String containerPath, String... options) {
  /** odd javadoc */
  // violation above 'Javadoc comment is placed in the wrong location.'

  /**
   * Creates a mount.
   *
   * @param containerPath a path on the container
   * @param options mounting options
   * @throws NullPointerException if any of the arguments are null
   */
  public InputFormattedInvalidJavadocPositionRecord {
    /** some javadoc. */
    // violation above 'Javadoc comment is placed in the wrong location'
  }

  /** some javadoc. * */
  record Example1(int num, String data) {

    /** some javadoc. */
    Example1 {}

    @Override
    /** some javadoc. * */
    public String toString() {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      return "Example" + data;
    }

    /** some javadoc. */
    @Override
    /** some javadoc. * */
    public int hashCode() {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      return 5 + num;
    }

    /** some javadoc. * */
    @Override
    /** some javadoc. * */
    public boolean equals(Object obj) {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      /** some javadoc. * */
      // violation above 'Javadoc comment is placed in the wrong location.'
      return false;
    }
  }
}

// violation below 'Javadoc comment is placed in the wrong location.'
/** invalid comment. */
/**
 * The configuration.
 *
 * @param text the text
 */
record MyRecord1(String text) {
  // violation above 'Top-level class MyRecord1 has to reside in its own source file'

  /** some javadoc. */
  MyRecord1 {}
  /** invalid comment. */
  // violation above 'Javadoc comment is placed in the wrong location.'
}

/** some javadoc. */
// violation above 'Javadoc comment is placed in the wrong location.'
/**
 * some javadoc.
 *
 * @param from the from
 */
record Mapping1(String from) { // violation 'Top-level class Mapping1 has to reside in its own'

  /** some javadoc. */
  // violation above 'Javadoc comment is placed in the wrong location.'
  /**
   * The constructor for Mapping.
   *
   * @param from The source
   */
  Mapping1(String from) {
    this.from = from;
    /** some javadoc. */
    // violation above 'Javadoc comment is placed in the wrong location.'
  }
}
