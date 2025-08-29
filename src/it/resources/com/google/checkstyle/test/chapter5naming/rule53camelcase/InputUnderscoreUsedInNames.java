package com.google.checkstyle.test.chapter5naming.rule53camelcase;

import org.junit.jupiter.api.Test;

/** some javadoc. */
public class InputUnderscoreUsedInNames {

  private final String guava33_4_6 = "";

  private final String guava3346 = "";

  private final String jdk8_0_392 = "";

  private final String jdk80392 = "";

  private final String kotlin1_9_24 = "";

  private final String kotlin1924 = "";

  private final String gradle8_5_1 = "";

  private final String gradle851 = "";

  class InnerBad {
    int guava_33_4_7 = 0; // violation, due to '_' between digit and letter, 'must match pattern'
    int guava33_4_7 = 0;

    int guava33_4_8_ = 0; // violation, underscore at the end, 'must match pattern'
    int guava33_4_8 = 0;

    int jdk_8_90 = 0; // violation, due to '_' between digit and letter, 'must match pattern'
    int jdk8_90 = 0;

    int jdk8_91_ = ""; // violation, underscore at the end, 'must match pattern'
    int jdk8_91 = "";

    int kotlin_1_9_24 = 0; // violation, due to '_' between digit and letter, 'must match pattern'
    int kotlin1_9_24 = 0;

    // violation below, underscore not allowed between lowercase character sequences, 'pattern'
    int kotlin_version1_9_24 = 0;

    int kotlinVersion1_9_24 = 0; // violation, false-positive, 'must match pattern'

    int kotlin1_9_25_ = 0; // violation, underscore at the end, 'must match pattern'
    int kotlin1_9_25 = 0;
  }

  static class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation, due to '_' between digit and letter, 'must match pattern'

    void guava34_4_6() {}

    void kotlin_2_9_94() {} // violation, due to '_' between digit and letter, 'must match pattern'

    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // violation, due to '_' between digit and letter, 'must match pattern'

    void gradle9_5_1() {}

    void jdk_9_0_392() {} // violation, due to '_' between digit and letter, 'must match pattern'

    void jdk9_0_392() {}

    // violation below, underscore not allowed between lowercase character sequences, 'pattern'
    void kotlin_lang1_9_2() {}

    void kotlinLang1_9_2() {} // violation, false-positive, 'must match pattern'

    // violation below, underscore not allowed between lowercase character sequences, 'pattern'
    void jdk_method8_90() {}

    void jdk_Method8_90() {} // violation, uppercase letter after underscore, 'must match pattern'

    void jdkMethod8_90() {} // violation, false-positive, 'must match pattern'

    // violation below, underscore not allowed between lowercase character sequences, 'pattern'
    void guava_version33_4_2() {}

    void guava_Version33_4_2() {} // violation, uppercase letter after underscore, 'match pattern'

    void guavaVersion33_4_2() {} // violation, false-positive, 'must match pattern'

    void kotlin1_9_24_() {} // violation, underscore at the end, 'must match pattern'

    void kotlin1_9_24() {}

    void guava_33_4_5_() {} // violation, underscore at the end, 'must match pattern'

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    // violation below, normal method must not use underscore, 'must match pattern'
    void testSetCount_zeroToZero_addSupported() {}

    void testSetCountZeroToZeroAddSupported() {}

    // violation below, normal method must not use underscore, 'must match pattern'
    void testPutNullValue_supported() {}

    void testPutNullValueSupported() {}

    // violation below, normal method must not use underscore, 'must match pattern'
    void testAddAll_nonEmptyList() {}

    void testAddAllNonEmptyList() {}

    // violation below, normal method must not use underscore, 'must match pattern'
    void testEntrySet_hashCode_size1() {}

    void testEntrySetHashCodeSize1() {}

    // violation below, normal method must not use underscore, 'must match pattern'
    void testCount_3() {}

    void testCount3() {}
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test
    void testsetcount_zerotozero_addsupported() {}

    @Test // violation below, uppercase letter after underscore, 'must match pattern'
    void testSetCount_ZeroToZero_AddSupported() {} 

    @Test
    void testPutNullValue_supported() {}

    @Test
    void testputnullvalue_supported() {}

    @Test // violation below, uppercase letter after underscore, 'must match pattern'
    void testPutNullValue_Supported() {}

    @Test
    void testAddAll_nonEmptyList() {}

    @Test
    void testaddall_nonemptylist() {}

    @Test
    void testAddAll_NonEmptyList() {} // violation, uppercase letter after underscore, 'pattern'

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test
    void testentryset_hashcode_size1() {}

    @Test
    void testEntrySet_HashCode_Size1() {} // violation, uppercase letter after underscore, 'pattern'

    @Test
    void testCount_3() {} // violation, single number after underscore, 'must match pattern'

    @Test
    void testCount_number3() {}
  }
}
