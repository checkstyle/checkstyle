//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

/* Config:
 * allowNoEmptyLineBetweenFields = false
 * allowMultipleEmptyLines = true
 * allowMultipleEmptyLinesInsideClassMembers = false
 * tokens = { STATIC_INIT , INSTANCE_INIT , METHOD_DEF , CTOR_DEF , COMPACT_CTOR_DEF}
 *
 */
public class InputEmptyLineSeparatorTextBlocks {

    record TextBlockRecord() {
        public TextBlockRecord{ // ok
            String block = """






"""; // ok
        }

        static{
            int a = 1; // violation 'There is more than 1 empty line after this line.'


            int b = 2;
        }
    }

    class MyClass1 {
        public MyClass1(){
            String bb = """


"""; // ok
        }

        void method(String foo){
            String b = """


"""; // violation 'There is more than 1 empty line after this line.'


            String b = "";
        }

        void method2(String foo){ // violation 'There is more than 1 empty line after this line.'
            /* block comment with empty lines is ignored



             */

            String b = "";
        }
    }

}
