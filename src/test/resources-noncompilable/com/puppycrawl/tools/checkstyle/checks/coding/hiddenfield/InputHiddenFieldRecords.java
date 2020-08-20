//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.Locale;

/* Config:
 * ignoreFormat = null
 * ignoreConstructorParameter = false
 * ignoreRecordComponents = false
 * ignoreSetter = false
 * setterCanReturnItsClass = false
 * ignoreAbstractMethods = false
 * tokens = {VARIABLE_DEF , PARAMETER_DEF , LAMBDA, PATTERN_VARIABLE_DEF}
 */
public class InputHiddenFieldRecords {
    public record MyRecord1() {
        private static int myHiddenInt = 2;

        public MyRecord1 {
            int myHiddenInt = 5; // violation
        }

        MyRecord1(String string) {
            this();
            int myHiddenInt = 6; // violation
        }
    }

    static class MyClass {
        private static int hiddenField = 5;

        MyClass(String string) {
            int hiddenField = 10; // violation
        }

        static final Object OBJ = "";
        static String hiddenStaticField = "hiddenStaticField";

        static {

            if (OBJ instanceof String hiddenStaticField) { //violation
                System.out.println(hiddenStaticField
                        .toLowerCase(Locale.forLanguageTag(hiddenStaticField)));
                boolean stringCheck = "test".equals(hiddenStaticField);
            }
        }

    }

    public record Keyboard() {
        private static String model = null;
        private static int price = 2;

        public boolean doStuff(Float f) {
            return f instanceof Float price && // violation
                    price.floatValue() > 0 &&
                    model != null &&
                    price.intValue() == 5;
        }
    }

    record MyRecord13(String string, Integer x) {
        void foo () {
            Integer x = 8; // violation
        }

        void foo2() {
            String string = "string"; // violation
        }

    }

    class MyClass13 {
        Integer x = 7;
        String string = "string";

        void foo() {
            Integer x = 8; // violation
        }

        void foo2() {
            String string = "string"; // violation
        }
    }

}
