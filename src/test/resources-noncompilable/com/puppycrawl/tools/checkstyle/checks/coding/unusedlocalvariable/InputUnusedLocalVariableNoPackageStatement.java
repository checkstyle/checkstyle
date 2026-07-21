/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with javac: missing package. Used for Testing purpose.
public class InputUnusedLocalVariableNoPackageStatement {
    int a = 12;

    void method() {
        int a = 1; // violation, 'Unused local variable'
        InputUnusedLocalVariableNoPackageStatement obj =
                new InputUnusedLocalVariableNoPackageStatement() {
                    void method() {
                        a += 1;
                    }
                };
        obj.getClass();
    }

    void anotherMethod() {
        int var1 = 12;
        int var2 = 13; // violation, 'Unused local variable'
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
            int var3 = 13; // violation, 'Unused local variable'
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
