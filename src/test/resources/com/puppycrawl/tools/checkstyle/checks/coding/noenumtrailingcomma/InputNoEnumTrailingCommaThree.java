/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingCommaThree {

    public enum Foo40 {
        ONE_ONE(1,1),
        ONE_TWO(1,2),; // violation 'Enum should not contain trailing comma.'

        int major, minor;

        Foo40(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }

    public enum Foo41 {
        ONE_ONE (1, 1) {
            @Override
            public void someFunction() {
            }
        },
        ONE_TWO (1, 2) {
            @Override
            public void someFunction(){
            }
        },; // violation 'Enum should not contain trailing comma.'

        int major, minor;

        Foo41 (int major, int minor) {
            this.major = major;
            this.minor = minor;
        }

        public void someFunction(){
            major = 1;
        }
    }

    public enum Foo42 {
        ONE_ONE (1, 1) {
            @Override
            public void someFunction() {
            }
        },
        ONE_TWO (1, 2) {
            @Override
            public void someFunction(){
            }
        };

        int major, minor;

        Foo42 (int major, int minor) {
            this.major = major;
            this.minor = minor;
        }

        public void someFunction(){
            major = 1;
        }
    }

    enum Foo43 {
        A,B(){ public String toString() { return "";}};
        interface SomeInterface {}
    }

    enum Foo44 {
        // violation below 'Enum should not contain trailing comma.'
        A,B(){ public String toString() { return "";}},;
        interface SomeInterface {}
    }

    enum Foo45 {
        , // violation 'Enum should not contain trailing comma.'
    }
    enum Foo46 {
        ,; // violation 'Enum should not contain trailing comma.'
    }
}
