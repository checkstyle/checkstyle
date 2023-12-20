/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingCommaTwo {

    enum Foo23 {
        A,B{} // ok
    }

    enum Foo24 {
        A,B(){ public String toString() { return "";}} // ok
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
        A,B(); // ok
    }

    enum Foo29 {
        A,B{}; // ok
    }

    enum Foo30 {
        A,B(){ public String toString() { return "";}}; // ok
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
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third, // violation
        }
    }

    enum Foo35 {
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third,; // violation
        }
    }

    enum Foo36 {
        A, B, C; // ok
        enum NestedFoo1 {
            First, Second, Third; // ok
        }
    }

    enum Foo37 {
        A, B; // ok
        Foo37() {}
    }

    enum Foo38 {
        A, B,; // violation
        Foo38() {};
    }

    public enum Foo39 {
        ONE_ONE(1,1),
        ONE_TWO(1,2); // ok

        int major, minor;

        Foo39(int major, int minor) {
            this.major = major;
            this.minor = minor;
        }
    }
}
