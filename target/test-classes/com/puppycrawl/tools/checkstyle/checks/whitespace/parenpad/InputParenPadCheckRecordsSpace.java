/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.HashMap;

import org.w3c.dom.Node;

public class InputParenPadCheckRecordsSpace {
    record MyRecord1( ) {

        MyRecord1( int x ){this();}

        public MyRecord1{
            bar( 1);  // violation, '')' is not preceded with whitespace.'
        }

        static int n;

        public void fun() {
            bar( 1);  // violation, '')' is not preceded with whitespace.'
        }

        public void bar(int k ) {  // violation, ''(' is not followed by whitespace.'
            while (k > 0) {
        // 2 violations above:
        //           ''(' is not followed by whitespace.'
        //           '')' is not preceded with whitespace.'
            }
        }

        public void fun2() {
            switch( n) {  // violation, '')' is not preceded with whitespace.'
                case 2:
                    bar(n);
        // 2 violations above:
        //           ''(' is not followed by whitespace.'
        //           '')' is not preceded with whitespace.'
                default:
                    break;
            }
        }

    }

    //record components
    record MyRecord2( String s){} // violation, '')' is not preceded with whitespace.'
    record MyRecord4( String s, String ...varargs ){}
    record MyRecord6( String[] strArr){} // violation, '')' is not preceded with whitespace.'
    record MyRecord7(HashMap<String, Node> hashMap ){}
    // violation above, ''(' is not followed by whitespace.'
    record MyRecord8(int x // violation, ''(' is not followed by whitespace.'
        ){
    }
}
