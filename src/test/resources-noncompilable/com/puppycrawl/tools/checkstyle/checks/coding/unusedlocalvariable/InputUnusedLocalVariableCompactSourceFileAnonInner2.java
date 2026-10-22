/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1; // violation 'Unused named local variable 'a''
    new DeclaredLater() {
        void test() {
            System.out.println(a);
        }
    };

    int b = 2;
    new WithPrivateField() {
        void test() {
            System.out.println(b);
        }
    };

    int c = 3; // violation 'Unused named local variable 'c''
    new Constants() {
        void test() {
            System.out.println(c);
        }
    };

    int d = 4; // violation 'Unused named local variable 'd''
    new DeclaredLater() {
        void test() {
            new Object() {
                void inner() {
                    System.out.println(d);
                }
            };
        }
    };
}

Runnable lambda = () -> {
    int e = 5; // violation 'Unused named local variable 'e''
    new Base() {
        void test() {
            System.out.println(e);
        }
    };
};

class DeclaredLater {
    int a;
    int d;
}

class WithPrivateField {
    private int b;
}

interface Constants {
    int c = 0;
}

class Base {
    protected int e;
}
