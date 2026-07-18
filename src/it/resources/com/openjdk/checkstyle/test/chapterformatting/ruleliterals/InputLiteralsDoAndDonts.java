package com.openjdk.checkstyle.test.chapterformatting.ruleliterals;

// violation first line 'Header mismatch*'

public class InputLiteralsDoAndDonts {

    public void styleGuideDo() {
        long l = 5432L;
        int i = 0x123 + 0xABC;
        byte b = 0b1010;
        float f1 = 1 / 5432f;
        float f2 = 0.123e4f;
        double d1 = 1 / 5432d;
        double d2 = 0x1.3p2;
    }

    public void styleGuideDonts() {
        long l = 5432l; // violation 'Should use uppercase 'L'.'
        int i = 0X123 + 0xabc;
        // 2 violations above:
        // 'Numerical prefix should be in lowercase.'
        // 'Should use uppercase hexadecimal letters.'
        byte b = 0B1010; // violation 'Numerical prefix should be in lowercase.'
        float f1 = 1 / 5432F; // violation 'Numerical suffix should be in lowercase.'
        float f2 = 0.123E4f; // violation 'Numerical infix should be in lowercase.'
        double d1 = 1 / 5432D; // violation 'Numerical suffix should be in lowercase.'
        double d2 = 0x1.3P2; // violation 'Numerical infix should be in lowercase.'
    }
}
