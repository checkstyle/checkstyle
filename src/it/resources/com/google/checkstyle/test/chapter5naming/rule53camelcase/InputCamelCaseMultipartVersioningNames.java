package com.google.checkstyle.test.chapter5naming.rule53camelcase;

/** some javadoc. */
public class InputCamelCaseMultipartVersioningNames {

  private final String guava33_4_6 = "Guava 33.4.6";

  private final String guava3346 = "Guava 33.4.6"; // false-negative until #17507

  private final String jdk8_0_392 = "Jdk 8.0.392";

  private final String jdk80392 = "Jdk 8.0.392"; // false-negative until #17507

  private final String kotlin1_9_24 = "Kotlin 1.9.24";

  private final String kotlin1924 = "Kotlin 1.9.24"; // false-negative until #17507

  private final String gradle8_5_1 = "Gradle 8.5.1";

  private final String gradle851 = "Gradle 8.5.1"; // false-negative until #17507

  class InnerBad {
    String guava_33_4_6 = "guava_33_4_6";
    // violation above 'Member name 'guava_33_4_6' must match pattern'

    String guava_version33_4_6 = "guava_version33_4_6";
    // violation above 'Member name 'guava_version33_4_6' must match pattern'

    String guava33_4_6_ = "guava33_4_6_";
    // violation above 'Member name 'guava33_4_6_' must match pattern'

    String jdk_8_90 = "jdk_8_90";
    // violation above 'Member name 'jdk_8_90' must match pattern'

    String jdk_version8_0_392 = "jdk_version8_0_392";
    // violation above 'Member name 'jdk_version8_0_392' must match pattern'

    String jdk8_90_ = "jdk8_90_";
    // violation above 'Member name 'jdk8_90_' must match pattern'

    String kotlin_1_9_24 = "kotlin_1_9_24";
    // violation above 'Member name 'kotlin_1_9_24' must match pattern'

    String kotlin_version1_9_24 = "kotlin_verison33_4_24";
    // violation above 'Member name 'kotlin_version1_9_24' must match pattern'

    String kotlin1_9_24_ = "kotlin1_9_24_";
    // violation above 'Member name 'kotlin1_9_24_' must match pattern'

    void guava_33_4_6() {}
    // violation above 'Method name 'guava_33_4_6' must match pattern'

    void guava_Version33_4_6() {}
    // violation above 'Method name 'guava_Version33_4_6' must match pattern'

    void guava_33_4_6_() {}
    // violation above 'Method name 'guava_33_4_6_' must match pattern'

    void jdk_8_90() {}
    // violation above 'Method name 'jdk_8_90' must match pattern'

    void jdk_method8_90() {}
    // violation above 'Method name 'jdk_method8_90' must match pattern'

    void jdk_8_90_() {}
    // violation above 'Method name 'jdk_8_90_' must match pattern'

    void kotlin_1_9_24() {}
    // violation above 'Method name 'kotlin_1_9_24' must match pattern'

    void kotlin_lang1_9_24() {}
    // violation above 'Method name 'kotlin_lang1_9_24' must match pattern'

    void kotlin1_9_24_() {}
    // violation above 'Method name 'kotlin1_9_24_' must match pattern'
  }

  void guava34_4_6() {}

  void kotlin2_9_94() {}

  void gradle9_5_1() {}

  void jdk9_0_392() {}
}
