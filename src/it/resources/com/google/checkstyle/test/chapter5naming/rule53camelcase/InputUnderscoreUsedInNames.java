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
    String guava_33_4_7 = ""; // violation due to '_' between digit and letter
    String guava33_4_7 = "";

    String guava33_4_8_ = ""; // violation, underscore at the end
    String guava33_4_8 = "";

    String jdk_8_90 = ""; // violation due to '_' between digit and letter
    String jdk8_90 = "";

    String jdk8_91_ = ""; // violation, underscore at the end
    String jdk8_91 = "";

    String kotlin_1_9_24 = ""; // violation due to '_' between digit and letter
    String kotlin1_9_24 = "";

    // violation below, '_' not allowed between lowercase character sequences
    String kotlin_version1_9_24 = "";

    String kotlinVersion1_9_24 = "";

    String kotlin1_9_25_ = ""; // violation, underscore at the end
    String kotlin1_9_25 = "";
  }

  static class MultipartVersioningNamesBad {

    void guava_34_4_6() {} // violation due to '_' between digit and letter

    void guava34_4_6() {}

    void kotlin_2_9_94() {} // violation due to '_' between digit and letter

    void kotlin2_9_94() {}

    void gradle_9_5_1() {} // violation due to '_' between digit and letter

    void gradle9_5_1() {}

    void jdk_9_0_392() {} // violation due to '_' between digit and letter

    void jdk9_0_392() {}

    // violation below, '_' not allowed between lowercase character sequences
    void kotlin_lang1_9_2() {}

    void kotlinLang1_9_2() {}

    // violation below, '_' not allowed between lowercase character sequences
    void jdk_method8_90() {}

    void jdk_Method8_90() {} // violation, uppercase letter after underscore

    void jdkMethod8_90() {}

    // violation below, '_' not allowed between lowercase character sequences
    void guava_version33_4_2() {}

    void guava_Version33_4_2() {} // violation, uppercase letter after underscore

    void guavaVersion33_4_2() {}

    void kotlin1_9_24_() {} // violation, underscore at the end

    void kotlin1_9_24() {}

    void guava_33_4_5_() {} // violation, underscore at the end

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    void testSetCount_zeroToZero_addSupported() {} // violation, normal method must not use '_'

    void testSetCountZeroToZeroAddSupported() {}

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
    void testCount_3() {} // violation single number after '_'

    @Test
    void testCount_number3() {}
  }
}
