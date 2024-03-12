/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.HashMap;

import org.w3c.dom.Node;

public class InputParenPadCheckRecordsSpace {
    record MyRecord1( ) {

        MyRecord1( int x ){this();}

        public MyRecord1{
            bar( 1);  // violation
        }

        static int n;

        public void fun() {
            bar( 1);  // violation
        }

        public void bar(int k ) {  // violation
            while (k > 0) {  // 2 violations
            }
        }

        public void fun2() {
            switch( n) {  // violation
                case 2:
                    bar(n);  // 2 violations
                default:
                    break;
            }
        }

    }

    //record components
    record MyRecord2( String s){} // violation
    record MyRecord4( String s, String ...varargs ){}
    record MyRecord6( String[] strArr){} // violation
    record MyRecord7(HashMap<String, Node> hashMap ){} // violation
    record MyRecord8(int x // violation
        ){
    }
}
