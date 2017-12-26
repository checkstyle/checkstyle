package com.puppycrawl.tools.checkstyle.checks.whitespace.afterleftcurly;

/**
 * Method content test cases for AfterLeftCurlyCheck.
 */
class InputAfterLeftCurlyLiteral
{

    private final boolean condition = "".equals(null);

    /** LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY */
    void tryCatchFinally()
    {
        try{}catch(Exception e){}finally{}

        try
        {
            String.valueOf("");
        }
        catch (Exception e) {
            throw e;
        }
        finally  { String.valueOf("");  }

        try
        {

            return;
        } catch (Exception e) {

            throw e;
        }

        finally
        {

            String.valueOf("");
        }
    }

    /** LITERAL_DO */
    void doWhile()
    {
        do{}
        while(condition);

        do break; while(true);

        do
        {
            String.valueOf("");
        }
        while (1 == 2);

        do {

            continue;
        } while (true);
    }

    /** LITERAL_FOR */
    void literalFor()
    {
        for(; condition;){}
        for(; condition;);

        for(;condition;)
        {
            continue;
        }

        for(;condition;) {

            break;
        }
    }

    /** LITERAL_WHILE */
    void literalWhile()
    {
        while(condition){}
        while(condition);

        while(condition) {
            continue;
        }

        while(condition)
        {

            break;
        }
    }

    /** LITERAL_IF, LITERAL_ELSE */
    void ifElse()
    {
        if (condition){}
        if (condition){}
        else{}
        if(condition);
        if(condition); else;

        if(condition) {
            ;
        }

        if(condition) {
            ;
        }
        else { ; }

        if(condition)
        {

            /**/
        }

        if(condition)
        {

            //
        }
        else{

            return;
        }
    }

    /** LITERAL_SYNCHRONIZED */
    void literalSynchronized()
    {
        synchronized (this){}

        synchronized(this) {
            ;
        }

        synchronized(this)
        {

            return;
        }
    }

    /** LITERAL_SWITCH, LITERAL_CASE, LITERAL_DEFAULT  */
    void switchCaseDefault()
    {
        switch (0){}

        switch(1) {
            case 1:{}
            case 2:
            {break;}
            default:
            {
                break;
            }
        }

        switch (2)
        {

            case 1:
            case 3: {

                break;
            }

            default:
            {

                break;
            }
        }

        switch(3) {
            case 1: break;
            case 2: {
                if (condition)
                    break;
            }
            break;
            default:
            {
                //
            }
            {
                //
            }
            break;
        }

        switch (4)
        {

            case 1:

                break;
            case 2: {

                if (condition)
                    break;
            }
            break;

            default:
            {

                //
            } {

                //
            }
            break;
        }
    }

    /** LITERAL_NEW  */
    void literalNew()
    {
        new Runnable() {
            @Override
            public void run() {}
        };
        new Runnable() {

            @Override
            public void run() {}
        };
    }
}
