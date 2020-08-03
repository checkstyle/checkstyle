//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.HashMap;

import org.w3c.dom.Node;
/* Config:
 * option = space
 * tokens = {ANNOTATION , ANNOTATION_FIELD_DEF , CTOR_CALL , CTOR_DEF , DOT ,
 *  ENUM_CONSTANT_DEF , EXPR , LITERAL_CATCH , LITERAL_DO , LITERAL_FOR ,
 *  LITERAL_IF , LITERAL_NEW , LITERAL_SWITCH , LITERAL_SYNCHRONIZED ,
 *  LITERAL_WHILE , METHOD_CALL , METHOD_DEF , QUESTION , RESOURCE_SPECIFICATION ,
 *  SUPER_CTOR_CALL , LAMBDA, RECORD_DEF}
 *
 */
public class InputParenPadCheckRecordsSpace {
    record MyRecord1( ) { // ok

        MyRecord1( int x ){this();} // ok

        public MyRecord1{
            bar( 1);  // violation
        }

        static int n;

        public void fun() {  // ok
            bar( 1);  // violation
        }

        public void bar(int k ) {  // violation
            while (k > 0) {  // violation x2
            }
        }

        public void fun2() {  // ok
            switch( n) {  // violation
                case 2:
                    bar(n);  // violation x2
                default:
                    break;
            }
        }

    }

    //record components
    record MyRecord2( String s){} // violation
    record MyRecord4( String s, String ...varargs ){} // ok
    record MyRecord6( String[] strArr){} // violation
    record MyRecord7(HashMap<String, Node> hashMap ){} // violation
    record MyRecord8(int x // violation
        ){
    }
}
