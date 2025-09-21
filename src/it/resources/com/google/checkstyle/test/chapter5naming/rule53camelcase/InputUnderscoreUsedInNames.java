package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import org.junit.jupiter.api.Test;

/** some javadoc. */
public class InputUnderscoreUsedInNames {

  private final String guava33_4_6 = ""; // correct

  private final String guava3346 = ""; // correct

  private final String jdk8_0_392 = ""; // correct

  private final String jdk80392 = ""; // correct

  private final String kotlin1_9_24 = ""; // correct

  private final String kotlin1924 = ""; // correct

  private final String gradle8_5_1 = ""; // correct

  private final String gradle851 = ""; // correct

  class InnerBad {
    String guava_33_4_6 = ""; // violation due to '_' between digit and letter
    String guava33_4_6 = ""; // correct

    String guava33_4_6_ = ""; // violation, underscore at the end
    String guava33_4_6 = ""; // correct

    String jdk_8_90 = ""; // violation due to '_' between digit and letter
    String jdk8_90 = ""; // correct

    String jdk8_91_ = ""; // violation, underscore at the end
    String jdk8_91 = ""; // correct

    String kotlin_1_9_24 = ""; // violation due to '_' between digit and letter
    String kotlin1_9_24 = ""; // correct

    String kotlin_version1_9_24 = ""; // violation
    // because '_' can't be placed between sequence of characters that start with lower-case

    String kotlinVersion1_9_24 = ""; // correct

    String kotlin1_9_25_ = "";  // violation, underscore at the end
    String kotlin1_9_25 = ""; // correct
  }

  static class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation due to '_' between digit and letter
    void guava34_4_6() {} // correct

    void kotlin_2_9_94() {} // violation due to '_' between digit and letter
    void kotlin2_9_94() {} // correct

    void gradle_9_5_1() {} // violation due to '_' between digit and letter
    void gradle9_5_1() {} // correct

    void jdk_9_0_392() {} // violation due to '_' between digit and letter
    void jdk9_0_392() {} // correct

    void kotlin_lang1_9_2() {} // violation
    // because '_' can't be placed between sequence of characters that start with lower-case

    void kotlinLang1_9_2() {} // correct

    void jdk_method8_90() {} // violation
    // because '_' can't be placed between sequence of characters that start with lower-case

    void jdkMethod8_90() {} // correct

    void guava_version33_4_6() {} // violation
    // because '_' can't be placed between sequence of characters that start with lower-case

    void jdk_Method8_90() {} // violation, uppercase letter after underscore

    void guava_Version33_4_6() {} // violation, uppercase letter after underscore
    void guavaVersion33_4_6() {} // correct

    void kotlin1_9_24_() {} // violation, underscore at the end
    void kotlin1_9_24() {} // correct

    void guava_33_4_6_() {} // violation, underscore at the end
    void guava33_4_6() {} // correct
  }

  class UnderscoresInNonTestMethods {

    void testSetCount_zeroToZero_addSupported() {} // violation, normal method must not use '_'
    void testSetCountZeroToZeroAddSupported() {} // correct

    void testPutNullValue_supported() {} // violation, normal method must not use '_'
    void testPutNullValueSupported() {} // correct

    void testAddAll_nonEmptyList() {} // violation, normal method must not use '_'
    void testAddAllNonEmptyList() {} // correct

    void testEntrySet_hashCode_size1() {} // violation, normal method must not use '_'
    void testEntrySetHashCodeSize1() {} // correct

    void testCount_3() {} // violation, normal method must not use '_'
    void testCount3() {} // correct
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {} // correct

    @Test
    void testPutNullValue_supported() {} // correct

    @Test
    void testAddAll_nonEmptyList() {} // correct

    @Test
    void testEntrySet_hashCode_size1() {} // correct

    @Test
    void testCount_3() {} // violation single number after '_'
  }
}
