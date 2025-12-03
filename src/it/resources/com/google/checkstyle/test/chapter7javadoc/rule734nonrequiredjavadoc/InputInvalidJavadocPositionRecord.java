package com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

// violation below 'Javadoc comment is placed in the wrong location.'
/** odd javadoc */
/** valid javadoc.
 *
 * @param containerPath options
 * @param options path
 *
 */
public record InputInvalidJavadocPositionRecord(String containerPath, String... options) {
  /** odd javadoc */ // violation 'Javadoc comment is placed in the wrong location.'

  /**
   * Creates a mount.
   *
   * @param containerPath a path on the container
   * @param options       mounting options
   * @throws NullPointerException     if any of the arguments are null
   */
  public InputInvalidJavadocPositionRecord {
    /** Some javadoc. */ // violation 'Javadoc comment is placed in the wrong location'
  }

  /** Some javadoc. **/
  record Example(int num, String data) {

    /**
     * some javadoc.
     **/
    Example {}

    @Override
    /** Some javadoc. **/
    public String toString() {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      return "Example" + data;
    }

    /**
     * some javadoc.
     **/
    @Override
    /** Some javadoc. **/
    public int hashCode() {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      return 5 + num;
    }

    /** Some javadoc. **/
    @Override
    /** Some javadoc. **/
    public boolean equals(Object obj) {
      // violation 2 lines above 'Javadoc comment is placed in the wrong location.'
      /** Some javadoc. **/
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
record MyRecord(String text) {
  // violation above 'Top-level class MyRecord has to reside in its own source file'

  /** Some javadoc.*/
  MyRecord {}
  /** invalid comment. */ // violation 'Javadoc comment is placed in the wrong location.'
}

/** Some javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
/**
 * some javadoc.
 *
 * @param from the from
 */
record Mapping(String from) { // violation 'Top-level class Mapping has to reside in its own'

  /** Some javadoc. */ // violation 'Javadoc comment is placed in the wrong location.'
  /**
   * The constructor for Mapping.
   *
   * @param from The source
   */
  Mapping(String from) {
    this.from = from;
    /** Some javadoc. */
    // violation above 'Javadoc comment is placed in the wrong location.'
  }
}
