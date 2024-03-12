/*
UnusedLocalVariable


*/

// No package statement for testing purposes.
public class InputUnusedLocalVariableWithoutPackageStatement {
    int a = 12;

    void method() {
        int a = 1; // violation
        InputUnusedLocalVariableWithoutPackageStatement obj =
                new InputUnusedLocalVariableWithoutPackageStatement() {
                    void method() {
                        a += 1;
                    }
                };
        obj.getClass();
    }

    void anotherMethod() {
        int var1 = 12;
        int var2 = 13; // violation
        Foo obj = new Foo() {
            void method() {
                var2 += var1;
            }
        };
        obj.getClass();
    }
}

class SharkFoo {
    int var1 = 12;
}

class Foo {
    int var2 = 13;

    class SharkFoo {
        int var3 = 13;

        void method() {
            int var3 = 13; // violation
            int var1 = 12;
            SharkFoo obj = new SharkFoo() {
                void method() {
                    var3 += var1;
                }
            };
            obj.getClass();
        }
    }
}
