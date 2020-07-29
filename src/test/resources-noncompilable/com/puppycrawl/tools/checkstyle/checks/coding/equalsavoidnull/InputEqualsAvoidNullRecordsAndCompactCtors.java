//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

/* Config:
 * ignoreEqualsIgnoreCase  = false
 */
public class InputEqualsAvoidNullRecordsAndCompactCtors {

    record TestRecord1(String str) {
        public TestRecord1 {
            str.equals("my string"); // violation
            "my string".equals(str); // ok
        }
    }

    record TestRecord2() {
        public boolean stringCheck1 (String toCheck){
            return toCheck.equals("my string"); // violation
        }

        public boolean stringCheck2 (String toCheck){
            return "my string".equals(toCheck); // ok
        }
    }

    record TestRecord3() {
        private static String str;
        TestRecord3 (String str){
            this();
            str.equalsIgnoreCase("my string"); // violation
            "my string".equals(str); // ok
        }
    }

    record TestRecord4(String str) {
        public TestRecord4 {
            str.equalsIgnoreCase("my string"); // violation
            "my string".equals(str); // ok
        }
    }

}
