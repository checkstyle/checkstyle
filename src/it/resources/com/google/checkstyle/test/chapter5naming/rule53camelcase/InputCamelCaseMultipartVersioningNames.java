package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import org.junit.jupiter.api.Test;

/** some javadoc. */
public class InputCamelCaseMultipartVersioningNames {

  private final String guava33_4_6 = "Guava 33.4.6";

  private final String guava3346 = "Guava 33.4.6";

  private final String jdk8_0_392 = "Jdk 8.0.392";

  private final String jdk80392 = "Jdk 8.0.392";

  private final String kotlin1_9_24 = "Kotlin 1.9.24";

  private final String kotlin1924 = "Kotlin 1.9.24";

  private final String gradle8_5_1 = "Gradle 8.5.1";

  private final String gradle851 = "Gradle 8.5.1";

  class InnerBad {
    String guava_33_4_6 = "guava_33_4_6"; // violation some thing something
    String guava33_4_6 = "guava33_4_6"; // violation some thing something

    // violation below 'Member name 'guava_version33_4_6' must match pattern'
    String guava_version33_4_6 = "guava_version33_4_6"; // false-positive

    String guava33_4_6_ = "guava33_4_6_"; // violation 'name 'guava33_4_6_' must match pattern'
    String guava33_4_6 = "guava33_4_6";

    String jdk_8_90 = "jdk_8_90"; // violation 'Member name 'jdk_8_90' must match pattern'
    String jdk8_90 = "jdk8_90";

    // violation below 'Member name 'jdk_version8_0_392' must match pattern'
    String jdk_version8_0_393 = "jdk_version8_0_392"; // false-positive

    String jdk8_91_ = "jdk8_90_"; // violation 'Member name 'jdk8_90_' must match pattern'
    String jdk8_91 = "jdk8_91";

    // violation below 'Member name 'kotlin_1_9_24' must match pattern'
    String kotlin_1_9_24 = "kotlin_1_9_24";
    String kotlin1_9_24 = "kotlin1_9_24";

    // violation below 'Member name 'kotlin_version1_9_24' must match pattern'
    String kotlin_version1_9_24 = "kotlin_verison33_4_24"; // false-positive

    String kotlin1_9_25_ = "kotlin1_9_25_"; // violation 'name 'kotlin1_9_24_' must match pattern'
    String kotlin1_9_25 = "kotlin1_9_25";
  }

  class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation numbers is not directy attached with name
    void guava34_4_6() {}

    void kotlin_2_9_94() {} // violation 'Method name 'kotlin_2_9_94' must match pattern'
    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // violation 'Method name 'gradle_9_5_1' must match pattern'
    void gradle9_5_1() {}

    void jdk_9_0_392() {} // violation 'Method name 'jdk_9_0_392' must match pattern'
    void jdk9_0_392() {}

    void kotlin_lang1_9_2() {} // false-positive, no way differentiate 'kotlinlang' &'kotlin_lang'
    void kotlinLang1_9_2() {} // correct form, false positive

    void jdk_method8_90() {} // violation, no way differentiate 'jdk_method' and 'jdkmethod'
    void jdkMethod8_90() {} // correct form, false positive

    void guava_version33_4_6() {} // violation,no way differentiate 'guavaversion' and 'guava_version'
    void guavaVersion33_4_6() {} // correct form, false positive

    void jdk_Method8_90() {} // violation, uppercase letter after underscore'

    void guava_Version33_4_6() {} // violation, uppercase letter after underscore'
    void guavaVersion33_4_6() {}

    void kotlin1_9_24_() {} // violation, underscore at the end
    void kotlin1_9_24() {} // violation, underscore at the end

    void guava_33_4_6_() {} // violation, underscore at the end
    void guava33_4_6_() {}
  }

  class UnderscoresInNonTestMethods {

    // violation below, not a test method but still uses underscores.
    void testSetCount_zeroToZero_addSupported() {}
    void testSetCountZeroToZeroAddSupported() {} // correct

    // violation below, not a test method but still uses underscores.
    void testPutNullValue_supported() {}
    void testPutNullValueSupported() {} // correct

    // violation below, not a test method but still uses underscores.
    void testAddAll_nonEmptyList() {}
    void testAddAllNonEmptyList() {} // correct

    // violation below, not a test method but still uses underscores.
    void testEntrySet_hashCode_size1() {}
    void testEntrySetHashCodeSize1() {} // correct

    // violation below, not a test method but still uses underscores.
    void testCount_3() {}
    void testCount3() {} // correct
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test
    void testPutNullValue_supported() {}

    @Test
    void testAddAll_nonEmptyList() {}

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test
    void testCount_3() {} // violation 'name 'testCount_3' must match pattern'
  }
}
