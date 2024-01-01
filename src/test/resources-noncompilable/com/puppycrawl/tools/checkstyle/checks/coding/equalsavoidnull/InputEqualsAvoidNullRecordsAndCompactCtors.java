/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullRecordsAndCompactCtors {

    record TestRecord1(String str) {
        public TestRecord1 {
            str.equals("my string"); // violation 'left .* of .* equals'
            "my string".equals(str);
        }
    }

    record TestRecord2() {
        public boolean stringCheck1 (String toCheck){
            return toCheck.equals("my string"); // violation 'left .* of .* equals'
        }

        public boolean stringCheck2 (String toCheck){
            return "my string".equals(toCheck);
        }
    }

    record TestRecord3() {
        private static String str;
        TestRecord3 (String str){
            this();
            str.equalsIgnoreCase("my string"); // violation 'left .* of .* equalsIgnoreCase'
            "my string".equals(str);
        }
    }

    record TestRecord4(String str) {
        public TestRecord4 {
            str.equalsIgnoreCase("my string"); // violation 'left .* of .* equalsIgnoreCase'
            "my string".equals(str);
        }
    }

    record TestRecord5() {
        TestRecord5 (int num) {
            this();
            str.equalsIgnoreCase("my string"); // violation 'left .* of .* equalsIgnoreCase'
            "my string".equals(str);
        }
        private static String str;
    }
}
