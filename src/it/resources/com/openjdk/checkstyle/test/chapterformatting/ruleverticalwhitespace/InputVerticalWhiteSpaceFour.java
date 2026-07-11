package com.openjdk.checkstyle.test.chapterformatting.ruleverticalwhitespace;

// violation first line 'Header mismatch*'

public class InputVerticalWhiteSpaceFour {
    int id;

    int score1;
    int score2;


    int name1;
    int name2;

    InputVerticalWhiteSpaceFour() {

    }


    InputVerticalWhiteSpaceFour(int a) {
        // violation above ''CTOR_DEF' has more than 1 empty lines before.'
    }



    public void methodTest() {
        // violation above ''METHOD_DEF' has more than 1 empty lines before.'
    }

}
