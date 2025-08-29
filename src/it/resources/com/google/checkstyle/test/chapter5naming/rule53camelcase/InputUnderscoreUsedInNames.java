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
    String guava_33_4_7 = ""; // violation 'Member name 'guava_33_4_7' must match pattern'
    String guava33_4_7 = "";

    String guava33_4_8_ = ""; // violation 'Member name 'guava33_4_8_' must match pattern'
    String guava33_4_8 = "";

    String jdk_8_90 = ""; // violation 'Member name 'jdk_8_90' must match pattern'
    String jdk8_90 = "";

    String jdk8_91_ = ""; // violation 'Member name 'jdk8_91_' must match pattern'
    String jdk8_91 = "";

    String kotlin_1_9_24 = ""; // violation 'Member name 'kotlin_1_9_24' must match pattern'
    String kotlin1_9_24 = "";

    // violation below 'Member name 'kotlin_version1_9_24' must match pattern'
    String kotlin_version1_9_24 = "";

    String kotlinVersion1_9_24 = "";  // violation 'name 'kotlinVersion1_9_24' must match pattern'

    String kotlin1_9_25_ = ""; // violation 'Member name 'kotlin1_9_25_' must match pattern'
    String kotlin1_9_25 = "";
  }

  static class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation 'Method name 'guava_34_4_6' must match pattern'

    void guava34_4_6() {}

    void kotlin_2_9_94() {} // violation 'Method name 'kotlin_2_9_94' must match pattern'

    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // violation 'Method name 'gradle_9_5_1' must match pattern'

    void gradle9_5_1() {}

    void jdk_9_0_392() {} // violation 'Method name 'jdk_9_0_392' must match pattern'

    void jdk9_0_392() {}

    // violation below 'Method name 'kotlin_lang1_9_2' must match pattern'
    void kotlin_lang1_9_2() {}

    // violation below 'Method name 'kotlinLang1_9_2' must match pattern'
    void kotlinLang1_9_2() {} // false-positive

    // violation below 'Method name 'jdk_method8_90' must match pattern'
    void jdk_method8_90() {}

    void jdk_Method8_90() {} // violation, uppercase letter after underscore

    // violation below 'Method name 'jdkMethod8_90' must match pattern'
    void jdkMethod8_90() {} // false-positive

    // violation below 'Method name 'guava_version33_4_2' must match pattern'
    void guava_version33_4_2() {}

    void guava_Version33_4_2() {} // violation, uppercase letter after underscore

    // violation below 'Method name 'guavaVersion33_4_2' must match pattern'
    void guavaVersion33_4_2() {} // false-positive

    void kotlin1_9_24_() {} // violation 'Method name 'kotlin1_9_24_' must match pattern'

    void kotlin1_9_24() {}

    void guava_33_4_5_() {} // violation 'Method name 'guava_33_4_5_' must match pattern'

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    // violation below 'Method name 'testSetCount_zeroToZero_addSupported' must match pattern'
    void testSetCount_zeroToZero_addSupported() {}

    void testSetCountZeroToZeroAddSupported() {}

    // violation below 'Method name 'testPutNullValue_supported' must match pattern'
    void testPutNullValue_supported() {}

    void testPutNullValueSupported() {}

    // violation below 'Method name 'testAddAll_nonEmptyList' must match pattern'
    void testAddAll_nonEmptyList() {}

    void testAddAllNonEmptyList() {}

    // violation below 'Method name 'testEntrySet_hashCode_size1' must match pattern'
    void testEntrySet_hashCode_size1() {}

    void testEntrySetHashCodeSize1() {}

    void testCount_3() {} // violation 'Method name 'testCount_3' must match pattern'

    void testCount3() {}
  }

  class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test // violation below, '_' not allowed between lowercase character sequences
    void testsetcount_zerotozero_addsupported() {}

    @Test
    void testSetCount_ZeroToZero_AddSupported() {} // violation, uppercase letter after underscore

    @Test
    void testPutNullValue_supported() {}

    @Test // violation below, '_' not allowed between lowercase character sequences
    void testputnullvalue_supported() {}

    @Test
    void testPutNullValue_Supported() {} // violation, uppercase letter after underscore

    @Test
    void testAddAll_nonEmptyList() {}

    @Test // violation below, '_' not allowed between lowercase character sequences
    void testaddall_nonemptylist() {}

    @Test
    void testAddAll_NonEmptyList() {} // violation, uppercase letter after underscore

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test // violation below, '_' not allowed between lowercase character sequences
    void testentryset_hashcode_size1() {}

    @Test
    void testEntrySet_HashCode_Size1() {} // violation, uppercase letter after underscore

    @Test
    void testCount_3() {} // violation 'Method name 'testCount_3' must match pattern'

    @Test
    void testCount_number3() {}
  }
}
