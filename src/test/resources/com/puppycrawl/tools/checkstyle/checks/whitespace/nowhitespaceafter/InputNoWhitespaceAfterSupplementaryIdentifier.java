/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_DECLARATOR


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterSupplementaryIdentifier {
    int ascii [] = {}; // violation ''ascii' is followed by whitespace.'
    int 𝒜 [] = {}; // violation ''𝒜' is followed by whitespace.'
    int 𝒜𝒜[] = {};
    int 𝒞𝒜 [][] = {}; // violation ''𝒞𝒜' is followed by whitespace.'

    void method() {
        int local𝒜 [] = {}; // violation ''local𝒜' is followed by whitespace.'
        int other𝒜[] = {};
    }
}
