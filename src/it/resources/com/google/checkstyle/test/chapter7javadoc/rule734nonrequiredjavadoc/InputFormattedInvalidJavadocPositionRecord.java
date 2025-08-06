package com.google.checkstyle.test.chapter7javadoc.rule734nonrequiredjavadoc;

/**
 * valid javadoc.
 *
 * @param containerPath options
 * @param containerPath path
 */
public record InputFormattedInvalidJavadocPositionRecord(String containerPath, String... options) {

  /**
   * Creates a mount.
   *
   * @param containerPath a path on the container
   * @param options mounting options
   * @throws NullPointerException if any of the arguments are null
   */
  InputFormattedInvalidJavadocPositionRecord {}
}

/**
 * The configuration.
 *
 * @param text the text
 */
public record MyRecord(String text) {

  /** some javadoc. */
  public MyRecord {}
}

/**
 * some javadoc.
 *
 * @param from the from
 */
record Mapping(String from) { // violation 'Top-level class Mapping has to reside in its own'

  /**
   * The constructor for Mapping.
   *
   * @param from The source
   */
  Mapping(String from) {
    this.from = from;
  }
}
