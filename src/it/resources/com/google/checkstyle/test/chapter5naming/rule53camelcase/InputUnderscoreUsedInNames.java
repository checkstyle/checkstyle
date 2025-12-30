package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import org.junit.jupiter.api.Test;

/** Some javadoc. */
public class InputUnderscoreUsedInNames {

  private String guava33_4_6;

  private String guava3346;

  private String jdk8_0_392;

  private String jdk80392;

  private String kotlin1_9_24;

  private String kotlin1924;

  private String gradle8_5_1;

  private String gradle851;

  class InnerBad {
    int guava_33_4_7; // false-negative, _ between digit and letter
    int guava33_4_7;

    int guava33_4_8_; // violation, _ at the end, 'must match pattern'
    int guava33_4_8;

    int jdk_8_90; // false-negative, _ between digit and letter
    int jdk8_90;

    int jdk8_91_; // violation, _ at the end, 'must match pattern'
    int jdk8_91;

    int kotlin_1_9_24; // false-negative, _ between digit and letter
    int kotlin1_9_24;

    // violation below, _ not allowed between lowercase character sequences, 'pattern'
    int kotlin_version1_9_24;

    int kotlinVersion1_9_24;

    int kotlin1_9_25_; // violation, _ at the end, 'must match pattern'
    int kotlin1_9_25;
  }

  static class MultipartVersioningNamesBad {

    // violation below, 'Method name 'guava_34_4_6' has invalid underscore usage.*'
    void guava_34_4_6() {}

    void guava34_4_6() {}

    // violation below, 'Method name 'kotlin_2_9_94' has invalid underscore usage.*'
    void kotlin_2_9_94() {}

    void kotlin2_9_94() {}

    // violation below, 'Method name 'gradle_9_5_1' has invalid underscore usage.*'
    void gradle_9_5_1() {}

    void gradle9_5_1() {}

    // violation below, 'Method name 'jdk_9_0_392' has invalid underscore usage.*'
    void jdk_9_0_392() {}

    void jdk9_0_392() {}

    // violation below, 'Method name 'kotlin_lang1_9_2' has invalid underscore usage.*'
    void kotlin_lang1_9_2() {}

    void kotlinLang1_9_2() {}

    void convertToKotlinVersion1_9_24() {}

    // violation below, 'Method name 'jdk_method8_90' has invalid underscore usage.*'
    void jdk_method8_90() {}

    // violation below, 'Method name 'jdk_Method8_90' has invalid underscore usage.*'
    void jdk_Method8_90() {}

    void jdkMethod8_90() {}

    // violation below, ''guava_version33_4_2' has invalid underscore usage.*'
    void guava_version33_4_2() {}

    // violation below, ''guava_Version33_4_2' has invalid underscore usage.*'
    void guava_Version33_4_2() {}

    void guavaVersion33_4_2() {}

    // violation below, 'Method name 'kotlin1_9_24_' has invalid underscore usage.*'
    void kotlin1_9_24_() {}

    void kotlin1_9_24() {}

    // violation below, 'Method name 'guava_33_4_5_' has invalid underscore usage.*'
    void guava_33_4_5_() {}

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    // violation below, 'Method name '.*' has invalid underscore usage.*'
    void testSetCount_zeroToZero_addSupported() {}

    void testSetCountZeroToZeroAddSupported() {}

    // violation below, ''testPutNullValue_supported' has invalid underscore usage.*'
    void testPutNullValue_supported() {}

    void testPutNullValueSupported() {}

    // violation below, ''testAddAll_nonEmptyList' has invalid underscore usage.*'
    void testAddAll_nonEmptyList() {}

    void testAddAllNonEmptyList() {}

    // violation below, ''testEntrySet_hashCode_size1' has invalid underscore usage.*'
    void testEntrySet_hashCode_size1() {}

    void testEntrySetHashCodeSize1() {}

    // violation below, 'Method name 'testCount_3' has invalid underscore usage.*'
    void testCount_3() {}

    void testCount3() {}
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test
    void testsetcount_zerotozero_addsupported() {}

    @Test
    void testSetCount_ZeroToZero_AddSupported() {
    // violation above, 'Test method name .* is not valid, each segment must be lowerCamelCase.*'
    }

    @Test
    void testPutNullValue_supported() {}

    @Test
    void testputnullvalue_supported() {}

    @Test
    void testPutNullValue_Supported() {
    // violation above, 'Test method name .* is not valid, each segment must be lowerCamelCase.*'
    }

    @Test
    void testAddAll_nonEmptyList() {}

    @Test
    void testaddall_nonemptylist() {}

    @Test
    void testAddAll_NonEmptyList() {
    // violation above, 'Test method name .* is not valid, each segment must be lowerCamelCase.*'
    }

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test
    void testentryset_hashcode_size1() {}

    @Test
    void testEntrySet_HashCode_Size1() {
    // violation above, 'Test method name .* is not valid, each segment must be lowerCamelCase.*'
    }

    @Test // violation below, 'Test method name 'testCount_3' has invalid underscore usage.*'
    void testCount_3() {}

    @Test
    void testCount_number3() {}
  }
}
