package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
 * The configuration of a bind mount.
 *
 * @param containerPath a path on the container
 * @param options mounting options
 */
public record InputRecordClassJavadocPosition(String containerPath, String... option) {

  /**
   * Creates a mount.
   *
   * @param containerPath a path on the container
   * @param options mounting options
   * @throws NullPointerException if any of the arguments are null
   */
  public InputRecordClassJavadocPosition {}

  /**
   * The configuration of a bind mount.
   *
   * @param containerPath a path on the container
   */
  record BindMount2(String containerPath) {

    /**
     * Creates a mount.
     *
     * @param containerPath a path on the container
     */
    BindMount2 {}
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  record Mapping(String from) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    Mapping(String from) {
      this.from = from;
    }
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   * @param to the to param
   */
  public record Mapping2(String from, String to) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     * @param to The destination
     */
    public Mapping2(String from, String to) {
      this.from = from;
      this.to = to;
    }
  }
}
