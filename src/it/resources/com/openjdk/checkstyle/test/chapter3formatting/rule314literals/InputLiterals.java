package com.openjdk.checkstyle.test.chapter3formatting.rule314literals;

/**
 * Test input for literals.
 */
public class InputLiterals {

    /**
     * Test hex literal case.
     */
    public void testHexLiteralCase() {
      byte b1  = 0x1b;          // violation  'Should use uppercase hexadecimal letters.'
      byte b2  = 0x1B;

      short s1 = 0xF5f;         // violation  'Should use uppercase hexadecimal letters.'
      short s2 = 0xF5F;

      int i1 = 0x11 + 0xabc;    // violation  'Should use uppercase hexadecimal letters.'
      int i2 = 0x11 + 0xABC;
      int i3 = 0x123 + 0xabc;   // violation  'Should use uppercase hexadecimal letters.'
      int i4 = 0x123 + 0xABC;
      int i5 = 0xdeadbeef;      // violation  'Should use uppercase hexadecimal letters.'
      int i6 = 0xDEADBEEF;

      long l1 = 0x7fff_ffff_ffff_ffffL;
      // violation above 'Should use uppercase hexadecimal letters.'
      long l2 = 0x7FFF_FFFF_FFFF_FFFFL;

      long l3 = 0x7FFF_AAA_bBB_DDDL;
      // violation above 'Should use uppercase hexadecimal letters.'
      long l4 = 0x7FFF_AAA_BBB_DDDL;

      float c = 0x1ap1f; // violation  'Should use uppercase hexadecimal letters.'
      float c2 = 0x1Ap1f;
      double e = 0x1AbC.p1d; // violation  'Should use uppercase hexadecimal letters.'
      double e2 = 0x1ABC.p1d;
      float g = 0x1b.p1f; // violation  'Should use uppercase hexadecimal letters.'
      float g2 = 0x1B.p1f;

    }

    /**
     * Test numerical prefixes, infixes, and suffixes character case.
     */
    public void testNumericalPrefixesInfixesSuffixesCharacterCase() {
      int hex1 = 0X1A; // violation 'Numerical prefix should be in lowercase.'
      int hex2 = 0x1A;

      int bin1 = 0B1010; // violation 'Numerical prefix should be in lowercase.'
      int bin2 = 0b1010;

      float exp1 = 1.23E3f; // violation 'Numerical infix should be in lowercase.'
      float exp2 = 1.23e3f;

      double hexEx1 = 0x1.3P2; // violation 'Numerical infix should be in lowercase.'
      double hexEx2 = 0x1.3p2;

      float suf1 = 10F; // violation 'Numerical suffix should be in lowercase.'
      float suf2 = 10f;

      double suf3 = 10D; // violation 'Numerical suffix should be in lowercase.'
      double suf4 = 10d;

      float mix1 = 1.2E3F; // violation 'Numerical infix should be in lowercase.'
      float mix3 = 1.2e3f;

      double mix4 = 0x1.3P2D; // violation 'Numerical infix should be in lowercase.'
      double mix5 = 0x1.3p2D; // violation 'Numerical suffix should be in lowercase.'
    }

    /**
     * Test long suffix character case.
     */
    void testLong() {
      long var1 = 508987L;
      long var2 = 508987l; // violation 'Should use uppercase 'L'.'
    }

    /**
     * Test mix cases.
     */
    void mixCases() {
      float h = 0x1b.P1F; // violation  'Should use uppercase hexadecimal letters.'
      // violation above 'Numerical infix should be in lowercase.'
      double f = 0x1AbC.P1D; // violation  'Should use uppercase hexadecimal letters.'
      // violation above 'Numerical infix should be in lowercase.'
    }

}
