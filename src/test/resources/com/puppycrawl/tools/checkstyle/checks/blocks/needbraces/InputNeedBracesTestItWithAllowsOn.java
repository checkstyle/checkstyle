/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = true
tokens = LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE, \
         LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

class InputNeedBracesTestItWithAllowsOn
{
    /** @return helper func **/
    boolean condition()
    {
        return false;
    }

    /** Test do/while loops **/
    void testDoWhile()
    {
        // Valid
        do {
            testDoWhile();
        }
        while (condition());

        // Invalid
        do testDoWhile(); while (condition()); // ok
    }

    /** Test while loops **/
    void testWhile()
    {
        // Valid
        while (condition()) {
            testWhile();
        }

        // Invalid
        while(condition()); // ok
        while (condition()) // violation
            testWhile();
        while (condition()) // ok
            if (condition()) // violation
                testWhile();
    }

    /** Test for loops **/
    void testFor()
    {
        // Valid
        for (int i = 1; i < 5; i++) {
            testFor();
        }

        // Invalid
        for(int i = 1;i < 5;i++); // ok
        for (int i = 1; i < 5; i++) // violation
            testFor();
        for (int i = 1; i < 5; // violation
             i++)
            if (i > 2) // violation
                testFor();
    }

    /** Test if constructs **/
    public void testIf()
    {
        // Valid
        if (condition()) {
            testIf();
        }
        else if (condition()) {
            testIf();
        }
        else {
            testIf();
        }

        // Invalid
        if (condition()); // ok
        if (condition()) // violation
            testIf();
        if (condition()) // violation
            testIf();
        else // violation
            testIf();
        if (condition()) // violation
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else // violation
            testIf();
        if (condition()) // violation
            if (condition()) // violation
                testIf();

        if (condition()) // violation
            while (condition()) testWhile(); // violation
        if (condition()) // violation
            do testDoWhile(); while (condition()); // violation
        if (condition()) // violation
            for (int i = 0; i < 1; i++) testFor(); // violation
        int a = 0;
        switch (a) {default: {}}
    }

    void whitespaceAfterSemi()
    {
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputNeedBracesTestItWithAllowsOn() {}

    /** Empty method block. **/
    public void emptyImplementation() {}
}
