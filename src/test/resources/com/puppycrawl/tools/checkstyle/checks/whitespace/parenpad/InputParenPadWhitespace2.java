/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.parenpad;

class InputParenPadWhitespace2
{
    void method1()
    {
        final int a = 1;
        int b= 1; // Ignore 1
        b=1; // Ignore 1
        b+=1; // Ignore 1
        b -=- 1 + (+ b);
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        b = b ++ + b --; // Ignore 1
        b = ++ b - -- b; // Ignore 1
    }

    void method2()
    {
        synchronized(this) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        }
        try{
        }
        catch(RuntimeException e){
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        }
    }

    private int nonVoid()
    {
        if ( true )
        {
            return(2);
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        }
        else
        {
            return 2; // this is ok
        }
    }

    private void testCasts()
    {
        Object o = (Object) new Object(); // some comment
        o = (Object)o;
        o = ( Object ) o; // some comment
        o = (Object)
            o; // some comment
    }

    private void testQuestions()
    {
        boolean b = (1 == 2)?true:false;
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        b = (1==2) ? false : true;
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
    }

    public void assertTest()
    {
        // some comment
        assert true;

        // some comment
        assert true : "Whups";

        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'

        // missing WS around assert
        assert(true);
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'

        // missing WS around colon
        assert true:"Whups";
    }
}
