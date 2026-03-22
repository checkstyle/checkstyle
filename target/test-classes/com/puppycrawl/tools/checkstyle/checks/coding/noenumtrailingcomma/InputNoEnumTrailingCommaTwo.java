/*
NoEnumTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noenumtrailingcomma;

public class InputNoEnumTrailingCommaTwo {

    enum Foo23 {
        A,B{}
    }

    enum Foo24 {
        A,B(){ public String toString() { return "";}}
    }

    enum Foo25 {
        // violation below 'Enum should not contain trailing comma.'
        A,B(){ public String toString() { return "";}},
    }

    enum Foo26 {
        A,B(), // violation 'Enum should not contain trailing comma.'
    }

    enum Foo27 {
        A,B{}, // violation 'Enum should not contain trailing comma.'
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
        A,B(),; // violation 'Enum should not contain trailing comma.'
    }

    enum Foo32 {
        A,B{},; // violation 'Enum should not contain trailing comma.'
    }

    enum Foo33 {
        // violation below 'Enum should not contain trailing comma.'
        A,B(){ public String toString() { return "";}},;
    }

    enum Foo34 {
        A, B, C;
        enum NestedFoo1 {
            First, Second, Third, // violation 'Enum should not contain trailing comma.'
        }
    }

    enum Foo35 {
        A, B, C;
        enum NestedFoo1 {
            First, Second, Third,; // violation 'Enum should not contain trailing comma.'
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
        A, B,; // violation 'Enum should not contain trailing comma.'
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
}
