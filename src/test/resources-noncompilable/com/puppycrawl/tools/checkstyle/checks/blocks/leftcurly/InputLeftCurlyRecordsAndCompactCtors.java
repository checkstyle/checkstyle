//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

import org.w3c.dom.Node;

/* Config:
 * option = same
 * tokens = {RECORD_DEF, COMPACT_CTOR_DEF, ANNOTATION_DEF , CLASS_DEF , CTOR_DEF ,
 *  ENUM_CONSTANT_DEF , ENUM_DEF , INTERFACE_DEF , LAMBDA , LITERAL_CASE ,
 *  LITERAL_CATCH , LITERAL_DEFAULT , LITERAL_DO , LITERAL_ELSE , LITERAL_FINALLY ,
 *  LITERAL_FOR , LITERAL_IF , LITERAL_SWITCH , LITERAL_SYNCHRONIZED , LITERAL_TRY ,
 *  LITERAL_WHILE , METHOD_DEF , OBJBLOCK , STATIC_INIT}
 *
 */
public class InputLeftCurlyRecordsAndCompactCtors {

    record MyTestRecord(String string, Record rec)
    { // violation
        private boolean inRecord(Object obj)
        { // violation
            int value = 0;
            if (obj instanceof Integer i) {
                value = i;
            }
            return value > 10;
        }
    }

    record MyTestRecord2()
    { // violation
        MyTestRecord2(String one, String two, String three)
    { // violation
            this();
        }
    }

    record MyTestRecord3(Integer i, Node node) {
        public MyTestRecord3
        { // violation
            int x = 5;
        }

        public static void main(String... args) {
            System.out.println("works!");
        }
    }

    record MyTestRecord4() {
    }

    record MyTestRecord5()
    { // violation
        static MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());
    }

    class MyTestClass {
        private MyTestRecord mtr =
                new MyTestRecord("my string", new MyTestRecord4());
    }
}
