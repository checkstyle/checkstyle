package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Some description. */
public class InputFormattedJavadocPositionOnCompactConstructorsWithAnnotation {

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
  }
}
