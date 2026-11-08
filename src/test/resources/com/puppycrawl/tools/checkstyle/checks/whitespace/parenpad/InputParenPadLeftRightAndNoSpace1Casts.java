/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

enum MyEnum1 {
    SOME_CONSTANT( ) {
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        int i = (int) (2 * (4 / 2)
                );
    };
}

public class InputParenPadLeftRightAndNoSpace1Casts
{
    public void myMethod() {
        String s = "test";
        Object o = s;
        ((String)o).length();
        ( (String)o ).length();
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
    }

    public void crisRon() {
        Object leo = "messi";
        Object ibra = leo;
        ((String)leo).compareTo( (String)ibra );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        Math.random();
    }

    public void intStringConv() {
        Object a = 5;
        Object b = "string";
        int w = Integer.parseInt((String)a);
        int x = Integer.parseInt( (String)a); // violation ''(' is followed by whitespace.'
        double y = Double.parseDouble((String)a ); // violation '')' is preceded with whitespace.'
        float z = Float.parseFloat( (String)a );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        String d = ((String)b);
    }

    public int something( Object o ) {
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        if ( o == null || !( o instanceof Float ) ) {
            // 4 violations above:
            //           ''(' is followed by whitespace.'
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
            //           '')' is preceded with whitespace.'
            return -1;
        }
        return Integer.valueOf( 22 ).compareTo( (Integer) o );
            // 4 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
    }

    private void launch(Integer number ) { // violation '')' is preceded with whitespace.'
        String myInt = ( number.toString() + '\0' );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        boolean result = false;
        if (number == 123)
            result = true;
    }
}
