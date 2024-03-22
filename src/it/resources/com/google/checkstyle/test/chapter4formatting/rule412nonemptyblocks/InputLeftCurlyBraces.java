package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyBraces
{ //warn
    /** @return helper func **/
    boolean condition()
    { //warn
        return false;
    }

    /** Test do/while loops **/
    void testDoWhile()
    { //warn

        do {
            testDoWhile();
        }
        while (condition());


        do testDoWhile(); while (condition());
    }

    /** Test while loops **/
    void testWhile()
    { //warn

        while (condition()) {
            testWhile();
        }


        while(condition());
        while (condition())
            testWhile();
        while (condition())
            if (condition())
                testWhile();
    }

    /** Test for loops **/
    void testFor()
    {  //warn

        for (int i = 1; i < 5; i++) {
            testFor();
        }


        for(int i = 1;i < 5;i++);
        for (int i = 1; i < 5; i++)
            testFor();
        for (int i = 1; i < 5;
             i++)
            if (i > 2)
                testFor();
    }

    /** Test if constructs **/
    public void testIf()
    { //warn

        if (condition()) {
            testIf();
        }
        else if (condition()) {
            testIf();
        }
        else {
            testIf();
        }


        if (condition());
        if (condition())
            testIf();
        if (condition())
            testIf();
        else
            testIf();
        if (condition())
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else
            testIf();
        if (condition())
            if (condition())
                testIf();
    }

    void whitespaceAfterSemi()
    { //warn

        int i = 1;int j = 2;


        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputLeftCurlyBraces() {}

    /** Empty method block. **/
    public void emptyImplementation() {}
}

class EnumContainerLeft {
    private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
}

class WithArraysLeft {
    String[] s = {""};
    String[] empty = {};
    String[] s1 = {
        "foo", "foo",
    };
    String[] s2 =
        {
            "foo", "foo",
        };
    String[] s3 =
        {
            "foo",
            "foo",
        };
    String[] s4 =
        {"foo", "foo"};
}
