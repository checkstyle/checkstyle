package com.puppycrawl.tools.checkstyle.javaparser;

public class InputJavaParserUnicodeEscapesInIdentifiers {

    int \u0061bc = 1;

    int \uu0062cd = 2;

    int multiple\u0065sc\u0061pes = 3;

    int \ud801\udc00abc = 4;

    int def\ud801\udc00 = 5;

    int \ud801\udc00\ud834\udd7b = 6;

    \u0046oo foo = new \u0046oo();

    void m\u0065thod(int p\u0061ram) {
        int local\u0076ar = \u0061bc + p\u0061ram;
        method(1);
    }

    static class \u0046oo {
    }
}
