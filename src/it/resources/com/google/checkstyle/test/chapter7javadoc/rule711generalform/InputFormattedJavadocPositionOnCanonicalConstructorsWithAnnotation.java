package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Some javadoc. */
public class InputFormattedJavadocPositionOnCanonicalConstructorsWithAnnotation {
  @interface SizeType {
    int max();
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   */
  @SizeType(max = 3)
  record Mapping(String from) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     */
    @SizeType(max = 3)
    Mapping(String from) {
      this.from = from;
    }
  }

  /**
   * Maps the ports.
   *
   * @param to the param
   */
  @SizeType(max = 3)
  public record Mapping2(String to) {

    /**
     * The constructor for Mapping.
     *
     * @param to The source
     */
    @SizeType(max = 3)
    public Mapping2(String to) {
      this.to = to;
    }
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   * @param to the to param
   */
  record BindCtor(String from, String to) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     * @param to The destination
     */
    BindCtor(String from, String to) {
      this.from = from;
      this.to = to;
    }
  }

  /**
   * Maps the ports.
   *
   * @param from the from param
   * @param to the to param
   */
  public record BindCtor2(String from, String to) {

    /**
     * The constructor for Mapping.
     *
     * @param from The source
     * @param to The destination
     */
    public BindCtor2(String from, String to) {
      this.from = from;
      this.to = to;
    }
  }
}
