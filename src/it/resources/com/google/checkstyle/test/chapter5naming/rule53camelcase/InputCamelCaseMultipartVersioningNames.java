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
    String guava_33_4_6 = "guava_33_4_6"; // violation 'name 'guava_33_4_6' must match pattern'

    // violation below 'Member name 'guava_version33_4_6' must match pattern'
    String guava_version33_4_6 = "guava_version33_4_6";

    String guava33_4_6_ = "guava33_4_6_"; // violation 'name 'guava33_4_6_' must match pattern'

    String jdk_8_90 = "jdk_8_90"; // violation 'Member name 'jdk_8_90' must match pattern'

    // violation below 'Member name 'jdk_version8_0_392' must match pattern'
    String jdk_version8_0_392 = "jdk_version8_0_392";

    String jdk8_90_ = "jdk8_90_"; // violation 'Member name 'jdk8_90_' must match pattern'

    // violation below 'Member name 'kotlin_1_9_24' must match pattern'
    String kotlin_1_9_24 = "kotlin_1_9_24";

    // violation below 'Member name 'kotlin_version1_9_24' must match pattern'
    String kotlin_version1_9_24 = "kotlin_verison33_4_24";

    String kotlin1_9_24_ = "kotlin1_9_24_"; // violation 'name 'kotlin1_9_24_' must match pattern'
  }

  class MultipartVersioningNamesBad {
    // numbers is not directy attached with name:
    void guava_34_4_6() {} // violation 'Method name 'guava_34_4_6' must match pattern'

    void kotlin_2_9_94() {} // violation 'Method name 'kotlin_2_9_94' must match pattern'

    void gradle_9_5_1() {} // violation 'Method name 'gradle_9_5_1' must match pattern'

    void jdk_9_0_392() {} // violation 'Method name 'jdk_9_0_392' must match pattern'

    // no way differentiate 'kotlinlang' and 'kotlin_lang'
    void kotlin_lang1_9_24() {} // violation 'Method name 'kotlin_lang1_9_24' must match pattern'

    // no way differentiate 'jdk_method' and 'jdkmethod'
    void jdk_method8_90() {} // violation 'Method name 'jdk_method8_90' must match pattern'

    // no way differentiate 'guavaversion' and 'guava_version'
    void guava_version33_4_6() {} // violation 'name 'guava_version33_4_6' must match pattern'

    // uppercase letter after underscore
    void kotlin_Lang1_9_24() {} // violation 'Method name 'kotlin_Lang1_9_24' must match pattern'

    void jdk_Method8_90() {} // violation 'Method name 'jdk_Method8_90' must match pattern'

    void guava_Version33_4_6() {} // violation 'name 'guava_Version33_4_6' must match pattern'

    // underscore at the end
    void kotlin1_9_24_() {} // violation 'Method name 'kotlin1_9_24_' must match pattern'

    void guava_33_4_6_() {} // violation 'Method name 'guava_33_4_6_' must match pattern'
  }

  class MutlipartVersioningNamesGood {
    void guava34_4_6() {}

    void kotlin2_9_94() {}

    void gradle9_5_1() {}

    void jdk9_0_392() {}

    // false-positive below
    void kotlinLang1_9_24() {} // violation 'name 'kotlinLang1_9_24' must match pattern'

    // false-positive below
    void jdkMethod8_90() {} // violation 'name 'jdkMethod8_90' must match pattern'

    // false-positive below
    void guavaVersion33_4_6() {} // violation 'name 'guavaVersion33_4_6' must match pattern'
  }

  class UnderscoresInNonTestMethods {

    // violation below 'name 'testSetCount_zeroToZero_addSupported' must match pattern'
    void testSetCount_zeroToZero_addSupported() {}

    // violation below 'name 'testPutNullValue_supported' must match pattern'
    void testPutNullValue_supported() {}

    // violation below 'name 'testAddAll_nonEmptyList' must match pattern'
    void testAddAll_nonEmptyList() {}

    // violation below 'name 'testEntrySet_hashCode_size1' must match pattern'
    void testEntrySet_hashCode_size1() {}

    // violation below 'name 'testCount_3' must match pattern'
    void testCount_3() {}
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
