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
        do testDoWhile(); while (condition());
    }

    /** Test while loops **/
    void testWhile()
    {
        // Valid
        while (condition()) {
            testWhile();
        }

        // Invalid
        while(condition());
        while (condition()) // violation, 'while' construct must use '{}'s
            testWhile();
        while (condition())
            if (condition()) // violation, 'if' construct must use '{}'s
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
        for(int i = 1;i < 5;i++);
        for (int i = 1; i < 5; i++) // violation, 'for' construct must use '{}'s
            testFor();
        for (int i = 1; i < 5; // violation, 'for' construct must use '{}'s
             i++)
            if (i > 2) // violation, 'if' construct must use '{}'s
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
        if (condition());
        if (condition()) // violation, 'if' construct must use '{}'s
            testIf();
        if (condition()) // violation, 'if' construct must use '{}'s
            testIf();
        else // violation, 'else' construct must use '{}'s
            testIf();
        if (condition()) // violation, 'if' construct must use '{}'s
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else // violation, 'else' construct must use '{}'s
            testIf();
        if (condition()) // violation, 'if' construct must use '{}'s
            if (condition()) // violation, 'if' construct must use '{}'s
                testIf();

        if (condition()) // violation, 'if' construct must use '{}'s
            while (condition()) testWhile(); // violation, 'while' construct must use '{}'s
        if (condition()) // violation, 'if' construct must use '{}'s
            do testDoWhile(); while (condition()); // violation, 'do' construct must use '{}'s
        if (condition()) // violation, 'if' construct must use '{}'s
            for (int i = 0; i < 1; i++) testFor(); // violation, 'for' construct must use '{}'s
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

    public void method() {
        if (true
            || true || true) return;
    }
}
