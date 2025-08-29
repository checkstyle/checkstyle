package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import org.junit.jupiter.api.Test;

/** some javadoc. */
public class InputCamelCaseMultipartVersioningNames {

  private final String guava33_4_6 = "";

  private final String guava3346 = "";

  private final String jdk8_0_392 = "";

  private final String jdk80392 = "";

  private final String kotlin1_9_24 = "";

  private final String kotlin1924 = "";

  private final String gradle8_5_1 = "";

  private final String gradle851 = "";

  class InnerBad {
    String guava_33_4_6 = ""; // violation due to '_' between digit and letter
    String guava33_4_6 = "";

    String guava_version33_4_6 = ""; // false-positive

    String guava33_4_6_ = ""; // violation, underscore at the end
    String guava33_4_6 = "";

    String jdk_8_90 = ""; // violation due to '_' between digit and letter
    String jdk8_90 = "";

    String jdk_version8_0_393 = ""; // false-positive
    // no way to differentiate 'jdk_verison' and 'jdkversion'

    String jdk8_91_ = ""; // violation, underscore at the end
    String jdk8_91 = "";

    String kotlin_1_9_24 = ""; // violation due to '_' between digit and letter
    String kotlin1_9_24 = "";

    String kotlin_version1_9_24 = ""; // false-positive,
    // not way to differentiate 'kolin_version' and 'kotlinversion'

    String kotlin1_9_25_ = "";  // violation, underscore at the end
    String kotlin1_9_25 = "";
  }

  class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation due to '_' between digit and letter
    void guava34_4_6() {}

    void kotlin_2_9_94() {} // violation due to '_' between digit and letter
    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // violation due to '_' between digit and letter
    void gradle9_5_1() {}

    void jdk_9_0_392() {} // violation due to '_' between digit and letter
    void jdk9_0_392() {}

    void kotlin_lang1_9_2() {} // false-positive:
    // no way to differentiate 'kotlinlang' & 'kotlin_lang'
    void kotlinLan1_9_2() {} // correct form but still false positive

    void jdk_method8_90() {} // false-postive, no way differentiate 'jdk_method' and 'jdkmethod'
    void jdkMethod8_90() {} // correct form but still false positive

    void guava_version33_4_6() {} // false-postive:
    // no way to differentiate 'guavaversion' and 'guava_version'
    void guavaVersion33_4_6() {} // correct form, false positive

    void jdk_Method8_90() {} // violation, uppercase letter after underscore'

    void guava_Version33_4_6() {} // violation, uppercase letter after underscore'
    void guavaVersion33_4_6() {}

    void kotlin1_9_24_() {} // violation, underscore at the end
    void kotlin1_9_24() {}

    void guava_33_4_6_() {} // violation, underscore at the end
    void guava33_4_6() {}
  }

  class UnderscoresInNonTestMethods {

    void testSetCount_zeroToZero_addSupported() {} // violation, normal method must not use '_'
    void testSetCountZeroToZeroAddSupported() {} // correct

    void testPutNullValue_supported() {} // violation, normal method must not use '_'
    void testPutNullValueSupported() {}

    void testAddAll_nonEmptyList() {} // violation, normal method must not use '_'
    void testAddAllNonEmptyList() {}

    void testEntrySet_hashCode_size1() {} // violation, normal method must not use '_'
    void testEntrySetHashCodeSize1() {}

    void testCount_3() {} // violation, normal method must not use '_'
    void testCount3() {}
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
    void testCount_3() {} // violation single number after '_'
  }
}
