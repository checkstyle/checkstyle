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
    // violation 4 lines below """Non-constant field name 'guava_33_4_7' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int guava_33_4_7;
    int guava33_4_7;

    // violation 4 lines below """Non-constant field name 'guava33_4_8_' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int guava33_4_8_;
    int guava33_4_8;

    // violation 4 lines below """Non-constant field name 'jdk_8_90' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int jdk_8_90;
    int jdk8_90;

    // violation 4 lines below """Non-constant field name 'jdk8_91_' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int jdk8_91_;
    int jdk8_91;

    // violation 4 lines below """Non-constant field name 'kotlin_1_9_24' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int kotlin_1_9_24;
    int kotlin1_9_24;

    // violation 4 lines below """Non-constant field name 'kotlin_version1_9_24'
    //  must start lowercase, be at least 2 chars, avoid single lowercase letter
    //  followed by uppercase, contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int kotlin_version1_9_24;

    int kotlinVersion1_9_24;

    // violation 4 lines below """Non-constant field name 'kotlin1_9_25_' must start lowercase,
    //  be at least 2 chars, avoid single lowercase letter followed by uppercase,
    //  contain only letters, digits or underscores,
    //  with underscores allowed only between adjacent digits."""
    int kotlin1_9_25_;
    int kotlin1_9_25;
  }

  static class MultipartVersioningNamesBad {

    void guava_34_4_6() {}

    // violation 2 lines above """Method name 'guava_34_4_6' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void guava34_4_6() {}

    void kotlin_2_9_94() {}

    // violation 2 lines above """Method name 'kotlin_2_9_94' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void kotlin2_9_94() {}

    void gradle_9_5_1() {}

    // violation 2 lines above """Method name 'gradle_9_5_1' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void gradle9_5_1() {}

    void jdk_9_0_392() {}

    // violation 2 lines above """Method name 'jdk_9_0_392' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void jdk9_0_392() {}

    void kotlin_lang1_9_2() {}

    // violation 2 lines above """Method name 'kotlin_lang1_9_2' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void kotlinLang1_9_2() {}

    void convertToKotlinVersion1_9_24() {}

    void jdk_method8_90() {}

    // violation 2 lines above """Method name 'jdk_method8_90' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void jdk_Method8_90() {}

    // violation 2 lines above """Method name 'jdk_Method8_90' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void jdkMethod8_90() {}

    void guava_version33_4_2() {}

    // violation 2 lines above """Method name 'guava_version33_4_2' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void guava_Version33_4_2() {}

    // violation 2 lines above """Method name 'guava_Version33_4_2' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void guavaVersion33_4_2() {}

    void kotlin1_9_24_() {}

    // violation 2 lines above """Method name 'kotlin1_9_24_' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void kotlin1_9_24() {}

    void guava_33_4_5_() {}

    // violation 2 lines above """Method name 'guava_33_4_5_' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void guava33_4_5() {}
  }

  class UnderscoresInNonTestMethods {

    void testSetCount_zeroToZero_addSupported() {}

    // violation 2 lines above """Method name 'testSetCount_zeroToZero_addSupported' has
    // invalid underscore usage, underscores only allowed between adjacent digits."""

    void testSetCountZeroToZeroAddSupported() {}

    void testPutNullValue_supported() {}

    // violation 2 lines above """Method name 'testPutNullValue_supported' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void testPutNullValueSupported() {}

    void testAddAll_nonEmptyList() {}

    // violation 2 lines above """Method name 'testAddAll_nonEmptyList' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void testAddAllNonEmptyList() {}

    void testEntrySet_hashCode_size1() {}

    // violation 2 lines above """Method name 'testEntrySet_hashCode_size1' has invalid
    // underscore usage, underscores only allowed between adjacent digits."""

    void testEntrySetHashCodeSize1() {}

    void testCount_3() {}

    // violation 2 lines above """Method name 'testCount_3' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void testCount3() {}
  }

  static class UnderscoreInTestMethodNames {

    @Test
    void testSetCount_zeroToZero_addSupported() {}

    @Test
    void testsetcount_zerotozero_addsupported() {}

    @Test
    void testSetCount_ZeroToZero_AddSupported() {}

    // violation 2 lines above """Test method name 'testSetCount_ZeroToZero_AddSupported'
    // segment must be more than a character, start lowercase, and not have a
    // single lowercase followed by uppercase, or consecutive uppercase."""

    @Test
    void testPutNullValue_supported() {}

    @Test
    void testputnullvalue_supported() {}

    @Test
    void testPutNullValue_Supported() {}

    // violation 2 lines above """Test method name 'testPutNullValue_Supported' segment must
    // be more than a character, start lowercase, and not have a single lowercase followed
    // by uppercase, or consecutive uppercase."""

    @Test
    void testAddAll_nonEmptyList() {}

    @Test
    void testaddall_nonemptylist() {}

    @Test
    void testAddAll_NonEmptyList() {}

    // violation 2 lines above """Test method name 'testAddAll_NonEmptyList' segment must
    // be more than a character, start lowercase, and not have a single lowercase followed
    // by uppercase, or consecutive uppercase."""

    @Test
    void testEntrySet_hashCode_size1() {}

    @Test
    void testentryset_hashcode_size1() {}

    @Test
    void testEntrySet_HashCode_Size1() {}

    // violation 2 lines above """Test method name 'testEntrySet_HashCode_Size1' segment
    // must be more than a character, start lowercase, and not have a single lowercase
    // followed by uppercase, or consecutive uppercase."""

    @Test
    void testCount_3() {}

    // violation 2 lines above """Test method name 'testCount_3' has invalid
    // underscore usage, underscore only allowed between letters or between digits"""

    @Test
    void testCount_number3() {}
  }
}
