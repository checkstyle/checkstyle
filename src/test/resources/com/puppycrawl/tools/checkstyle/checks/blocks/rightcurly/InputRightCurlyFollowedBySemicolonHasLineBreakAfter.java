/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY , LITERAL_IF , LITERAL_ELSE , CLASS_DEF , \
         METHOD_DEF , CTOR_DEF , LITERAL_FOR , LITERAL_WHILE , LITERAL_DO , STATIC_INIT , \
         INSTANCE_INIT , ANNOTATION_DEF , ENUM_DEF , INTERFACE_DEF , RECORD_DEF , COMPACT_CTOR_DEF \
         , OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;
import java.util.*;

public class InputRightCurlyFollowedBySemicolonHasLineBreakAfter {
        Object obj_anon = new Object() {
        }; int i = 0; // violation '';' at column 10 should have line break after'

        void method1() {
        } int j = 10; // violation ''}' at column 9 should be alone on a line'

        class SomeClass {
        } int k = 4; // violation ''}' at column 9 should be alone on a line'

        Object obj_anon2 = new Object() {
        void anonInnerMethod(){
                System.out.println("Hello world");
        }
        }; int i2 = 0; // violation '';' at column 10 should have line break after'

        Runnable r = new Runnable() {
        @Override
        public void run() {
        System.out.println("Hello from anonymous inner class!");
        }
        }; void methodOnSameLine() { // violation '';' at column 10 should have line break after'
                // code here
        }

        Thread anotherT = new Thread() {
        @Override
        public void run() {
                System.out.println("Hello from anonymous inner class!");
        }
        }; Thread t = new Thread() { // violation '';' at column 10 should have line break after'
        @Override
        public void run() {
                System.out.println("Hello from anonymous inner class!");
        }
        }; int value = 99; // violation '';' at column 10 should have line break after'

        public enum SomeEnum {

        ENUM1 () {

        },

        ENUM2 () {

        },
        }

        private static final Comparator<String> STRING_COMPARATOR = new Comparator<>() {
          @Override
          public int compare(String str1, String str2) {
            return str1.compareTo(str2);
                }
        };

}
