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

    void guava_34_4_6() {} // false-negative, _ between digit and letter

    void guava34_4_6() {}

    void kotlin_2_9_94() {} // false-negative, _ between digit and letter

    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // false-negative, _ between digit and letter

    void gradle9_5_1() {}

    void jdk_9_0_392() {} // false-negative, _ between digit and letter

    void jdk9_0_392() {}

    // violation below, _ not allowed between lowercase character sequences, 'pattern'
    void kotlin_lang1_9_2() {}

    void kotlinLang1_9_2() {}

    void convertToKotlinVersion1_9_24() {}

    // violation below, _ not allowed between lowercase character sequences, 'pattern'
    void jdk_method8_90() {}

    void jdk_Method8_90() {} // violation, uppercase letter after _, 'must match pattern'

    void jdkMethod8_90() {}

    // violation below, _ not allowed between lowercase character sequences, 'pattern'
    void guava_version33_4_2() {}

    void guava_Version33_4_2() {} // violation, uppercase letter after _, 'match pattern'

    void guavaVersion33_4_2() {}

    void kotlin1_9_24_() {} // violation, _ at the end, 'must match pattern'

    void kotlin1_9_24() {}

    void guava_33_4_5_() {} // violation, _ at the end, 'must match pattern'

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    // violation below, normal method must not use _, 'must match pattern'
    void testSetCount_zeroToZero_addSupported() {}

    void testSetCountZeroToZeroAddSupported() {}

    // violation below, normal method must not use _, 'must match pattern'
    void testPutNullValue_supported() {}

    void testPutNullValueSupported() {}

    // violation below, normal method must not use _, 'must match pattern'
    void testAddAll_nonEmptyList() {}

    void testAddAllNonEmptyList() {}

    // violation below, normal method must not use _, 'must match pattern'
    void testEntrySet_hashCode_size1() {}

    void testEntrySetHashCodeSize1() {}

    void testCount_3() {} // false-negative, normal method must not use _

    void testCount3() {}
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test
    void testsetcount_zerotozero_addsupported() {}

    @Test // violation below, uppercase letter after _, 'must match pattern'
    void testSetCount_ZeroToZero_AddSupported() {}

    @Test
    void testPutNullValue_supported() {}

    @Test
    void testputnullvalue_supported() {}

    @Test // violation below, uppercase letter after _, 'must match pattern'
    void testPutNullValue_Supported() {}

    @Test
    void testAddAll_nonEmptyList() {}

    @Test
    void testaddall_nonemptylist() {}

    @Test
    void testAddAll_NonEmptyList() {} // violation, uppercase letter after _, 'pattern'

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test
    void testentryset_hashcode_size1() {}

    @Test
    void testEntrySet_HashCode_Size1() {} // violation, uppercase letter after _, 'pattern'

    @Test
    void testCount_3() {}

    @Test
    void testCount_number3() {}
  }
}
