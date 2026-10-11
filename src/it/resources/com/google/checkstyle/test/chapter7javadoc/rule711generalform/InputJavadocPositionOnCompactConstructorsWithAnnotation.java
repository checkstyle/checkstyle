package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Some description. */
public class InputJavadocPositionOnCompactConstructorsWithAnnotation {

  @interface SizeType {
    int max();
  }

  /**
   * Something something.
   *
   * @param containerPath a path on the container
   */
  @SizeType(max = 2)
  record BindMount(String containerPath) {

    /**
     * Creates a mount.
     *
     * @param containerPath a path on the container
     */
    @SizeType(max = 1)
    BindMount {}
  }

  /**
   * Something something.
   *
   * @param goPath a path on the container
   */
  public record BindMount2(String goPath) {

    /**
     * Creates a mount.
     *
     * @param goPath a path on the container
     */
    public BindMount2 {}
  }

  /**
   * Something something.
   *
   * @param fileSystem a path on the container
   */
  @SizeType(max = 4)
  public record MyRecord(String fileSystem) {

    /**
     * Creates a mount.
     *
     * @param fileSystem a path on the container
     */
    @SizeType(max = 2)
    public MyRecord {}
    /** Invalid javadoc.*/
    // violation above 'Javadoc comment is placed in the wrong location.'
  }
}
