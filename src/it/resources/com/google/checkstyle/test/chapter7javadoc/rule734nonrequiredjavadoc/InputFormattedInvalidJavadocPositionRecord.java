package com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

/**
 * valid javadoc.
 *
 * @param containerPath options
 * @param options path
 */
public record InputFormattedInvalidJavadocPositionRecord(String containerPath, String... options) {

  /**
   * Creates a mount.
   *
   * @param containerPath a path on the container
   * @param options mounting options
   * @throws NullPointerException if any of the arguments are null
   */
  public InputFormattedInvalidJavadocPositionRecord {}
}

/**
 * The configuration.
 *
 * @param text the text
 */
record MyRecord1(String text) {
  // violation above 'Top-level class MyRecord1 has to reside in its own source file'
  /** some javadoc. */
  public MyRecord1 {}
}

/**
 * some javadoc.
 *
 * @param from the from
 */
record Mapping1(String from) { // violation 'Top-level class Mapping1 has to reside in its own'

  /**
   * The constructor for Mapping.
   *
   * @param from The source
   */
  Mapping1(String from) {
    this.from = from;
  }
}
