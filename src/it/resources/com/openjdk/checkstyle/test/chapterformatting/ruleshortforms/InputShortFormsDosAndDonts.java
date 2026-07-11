package com.openjdk.checkstyle.test.chapterformatting.ruleshortforms;

// violation first line 'Header is missing*'

public class InputShortFormsDosAndDonts {

    enum Response { YES, NO, MAYBE }

    public boolean isReference() { return true; }

    // violation below 'Only one statement per line allowed.'
    public int getResult() { int value = 10; return value < 0 ? 0 : value; }

    public void styleGuideDonts() {
        int size = 10;
        int sum = 0;
        int[] data = new int[size];

        // violation below ''{' at column 40 should have line break after.'
        for (int i = 0; i < size; i++) { sum += data[i]; }
    }
}
