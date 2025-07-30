package com.google.checkstyle.test.chapter5naming.rule53camelcase;

/** some javadoc. */
public class InputCamelCaseMultipartVersioningNames {

  private final String guava33_4_6 = "Guava 33.4.6"; // false-positive until #17507
  // violation above 'Member name 'guava33_4_6' must match pattern'

  private final String guava3346 = "Guava 33.4.6";

  private final String jdk8_0_392 = "Jdk 8.0.392"; // false-positive until #17507
  // violation above 'Member name 'jdk8_0_392' must match pattern'

  private final String jdk80392 = "Jdk 8.0.392";

  private final String kotlin1_9_24 = "Kotlin 1.9.24"; // false-positive until #17507
  // violation above 'Member name 'kotlin1_9_24' must match pattern'

  private final String kotlin1924 = "Kotlin 1.9.24";

  private final String gradle8_5_1 = "Gradle 8.5.1"; // false-positive until #17507
  // violation above 'Member name 'gradle8_5_1' must match pattern'

  private final String gradle851 = "Gradle 8.5.1";

  void guava34_4_6() {} // false-positive until #17507
  // violation above 'Method name 'guava34_4_6' must match pattern'

  void kotlin2_9_94() {} // false-positive until #17507
  // violation above 'Method name 'kotlin2_9_94' must match pattern'

  void gradle9_5_1() {} // false-positive until #17507
  // violation above 'Method name 'gradle9_5_1' must match pattern'

  void jdk9_0_392() {} // false-positive until #17507
  // violation above 'Method name 'jdk9_0_392' must match pattern'
}
