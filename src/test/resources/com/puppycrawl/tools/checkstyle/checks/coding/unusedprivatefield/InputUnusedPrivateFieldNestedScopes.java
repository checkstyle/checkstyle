/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;


public final class InputUnusedPrivateFieldNestedScopes {
    public final class Nested {

        private final Object source; // ok, private field is used

        private final String fileName; // ok, private field is used

        public Nested(Object source) {
        this(source, null);
    }

    public Nested(Object src, String fileName) {
            if (src == null) {
                throw new IllegalArgumentException("null source");
            }

            source = src;
            this.fileName = fileName;
        }


        public Object getSource() {
        return source;
    }
    }
    public  class Check{

    }
    void outerMethod(String message, String testCase) {

        class Inner {

            private String message; // ok, private field is used
            private String testCase; // ok, private field is used

            Inner(String aMessage, String aTestCase) {
                message = aMessage;
                testCase = aTestCase;
            }

            public void run() {
                throw new RuntimeException(message);
            }

            public String getName() {
                return testCase;
            }
        }

        Inner inner = new Inner(message, testCase);
        inner.run();
    }
}
