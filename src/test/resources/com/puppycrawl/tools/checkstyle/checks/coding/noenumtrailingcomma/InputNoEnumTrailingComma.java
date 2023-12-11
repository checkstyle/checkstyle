/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingComma {

    enum Foo1 {
        FOO,
        BAR;
    }

    enum Foo2 {
        FOO,
        BAR
    }

    enum Foo3 {
        FOO,
        BAR, // violation
    }

    enum Foo4 {
        FOO,
        BAR, // violation
        ;
    }

    enum Foo5 {
        FOO,
        BAR,; // violation
    }

    enum Foo6 { FOO, BAR,; } // violation

    enum Foo7 { FOO, BAR, } // violation

    enum Foo8 {
        FOO,
        BAR
        ;
    }

    enum Foo9 { FOO, BAR; }

    enum Foo10 { FOO, BAR }

    enum Foo11 {}

    enum Foo12 { FOO, } // violation

    enum Foo13 { FOO }

    enum Foo14 {
        FOO, // violation
    }

    enum Foo15 {
        FOO
    }

    enum Foo16 {
    }

    enum Foo17 { FOO; }

    enum Foo18 {
        FOO;
    }

    enum Foo19 {
        FOO
        , // violation
    }

    enum Foo20 {
        FOO
        ;
    }

    public enum Foo21
    {
        FIRST_CONSTANT,
        SECOND_CONSTANT;

        public void someMethod() {
        }
    }

    enum Foo22 {
        A,B()
    }

    enum Foo23 {
        A,B{}
    }

    enum Foo24 {
        A,B(){ public String toString() { return "";}}
    }

    enum Foo25 {
        A,B(){ public String toString() { return "";}}, // violation
    }

    enum Foo26 {
        A,B(), // violation
    }

    enum Foo27 {
        A,B{}, // violation
    }

    enum Foo28 {
        A,B();
    }

    enum Foo29 {
        A,B{};
    }

    enum Foo30 {
        A,B(){ public String toString() { return "";}};
    }

    enum Foo31 {
        A,B(),; // violation
    }

    enum Foo32 {
        A,B{},; // violation
    }

    enum Foo33 {
        A,B(){ public String toString() { return "";}},; // violation
    }

    enum Foo34 {
        A, B, C;
        enum NestedFoo1 {
            First, Second, Third, // violation
        }
    }

    enum Foo35 {
        A, B, C;
        enum NestedFoo1 {
            First, Second, Third,; // violation
        }
    }

    enum Foo36 {
        A, B, C;
        enum NestedFoo1 {
            First, Second, Third;
        }
    }

    enum Foo37 {
        A, B;
        Foo37() {}
    }

    enum Foo38 {
        A, B,; // violation
        Foo38() {};
    }

    public enum Foo39 {
        ONE_ONE(1,1),
        ONE_TWO(1,2);

        int major, minor;

        Foo39(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }

    public enum Foo40 {
        ONE_ONE(1,1),
        ONE_TWO(1,2),; // violation

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
        },; // violation

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
        A,B(){ public String toString() { return "";}},; // violation
        interface SomeInterface {}
    }

    enum Foo45 {
        , // violation
    }
    enum Foo46 {
        ,; // violation
    }
}
