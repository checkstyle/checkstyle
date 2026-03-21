/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.HashMap;

import org.w3c.dom.Node;

public class InputParenPadCheckRecords {
    record MyRecord1( ) {
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'

        MyRecord1( int x ){this();}
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'

        public MyRecord1{
            bar( 1);  // violation, ''(' is followed by whitespace.'
        }

        static int n;

        public void fun() {
            bar( 1);  // violation, ''(' is followed by whitespace.'
        }

        public void bar(int k ) {  // violation, '')' is preceded with whitespace.'
            while (k > 0) {
            }
        }

        public void fun2() {
            switch( n) {  // violation, ''(' is followed by whitespace.'
                case 2:
                    bar(n);
                default:
                    break;
            }
        }

    }

    //record components
    record MyRecord2( String s){} // violation, ''(' is followed by whitespace.'
    record MyRecord4( String s, String ...varargs ){}
        // 2 violations above:
        //           ''(' is followed by whitespace.'
        //           '')' is preceded with whitespace.'
    record MyRecord6( String[] strArr){} // violation, ''(' is followed by whitespace.'
    record MyRecord7(HashMap<String, Node> hashMap ){}
    // violation above, '')' is preceded with whitespace.'
}
