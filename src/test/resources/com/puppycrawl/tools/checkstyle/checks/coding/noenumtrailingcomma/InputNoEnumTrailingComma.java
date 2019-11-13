package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingComma {

    enum Foo1 {
        FOO,
        BAR; //OK
    }

    enum Foo2 {
        FOO,
        BAR //OK
    }

    enum Foo3 {
        FOO,
        BAR, //violation
    }

    enum Foo4 {
        FOO,
        BAR, //violation
        ;
    }

    enum Foo5 {
        FOO,
        BAR,; //violation
    }

    enum Foo6 { FOO, BAR,; } //violation

    enum Foo7 { FOO, BAR, } //violation

    enum Foo8 {
        FOO,
        BAR //OK
        ;
    }

    enum Foo9 { FOO, BAR; } //OK

    enum Foo10 { FOO, BAR } //OK

    enum Foo11 {} //OK

    enum Foo12 { FOO, } //violation

    enum Foo13 { FOO } //OK

    enum Foo14 {
        FOO, //violation
    }

    enum Foo15 {
        FOO //OK
    }

    enum Foo16 {
    } //OK

    enum Foo17 { FOO; } //OK

    enum Foo18 {
        FOO; //OK
    }

    enum Foo19 {
        FOO
        , //violation
    }

    enum Foo20 {
        FOO
        ; //OK
    }

    public enum Foo21
    {
        FIRST_CONSTANT,
        SECOND_CONSTANT; //OK

        public void someMethod() {
        }
    }

    enum Foo22 {
        A,B() //OK
    }

    enum Foo23 {
        A,B{} //OK
    }

    enum Foo24 {
        A,B(){ public String toString() { return "";}} //OK
    }

    enum Foo25 {
        A,B(){ public String toString() { return "";}}, //violation
    }

    enum Foo26 {
        A,B(), //violation
    }

    enum Foo27 {
        A,B{}, //violation
    }

    enum Foo28 {
        A,B(); //OK
    }

    enum Foo29 {
        A,B{}; //OK
    }

    enum Foo30 {
        A,B(){ public String toString() { return "";}}; //OK
    }

    enum Foo31 {
        A,B(),; //violation
    }

    enum Foo32 {
        A,B{},; //violation
    }

    enum Foo33 {
        A,B(){ public String toString() { return "";}},; //violation
    }

    enum Foo34 {
        A, B, C; //OK
        enum NestedFoo1 {
            First, Second, Third, //violation
        }
    }

    enum Foo35 {
        A, B, C; //OK
        enum NestedFoo1 {
            First, Second, Third,; //violation
        }
    }

    enum Foo36 {
        A, B, C; //OK
        enum NestedFoo1 {
            First, Second, Third; //OK
        }
    }

    enum Foo37 {
        A, B; //OK
        Foo37() {}
    }

    enum Foo38 {
        A, B,; //violation
        Foo38() {};
    }

    public enum Foo39 {
        ONE_ONE(1,1),
        ONE_TWO(1,2); //OK

        int major, minor;

        Foo39(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }

    public enum Foo40 {
        ONE_ONE(1,1),
        ONE_TWO(1,2),; //violation

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
        },; //violation

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
        }; //OK

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
        A,B(){ public String toString() { return "";}}; //OK
        interface SomeInterface {}
    }

    enum Foo44 {
        A,B(){ public String toString() { return "";}},; //violation
        interface SomeInterface {}
    }
}
