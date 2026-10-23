/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1; // violation 'Unused named local variable 'a''
    new Object() {
        void test() {
            new WithField() {
                void inner() {
                    System.out.println(a);
                }
            };
        }
    };

    int b = 2;
    new Object() {
        void test() {
            new Object() {
                void inner() {
                    System.out.println(b);
                }
            };
        }
    };

    int c = 3;
    new WithField() {
        void test() {
            new Object() {
                void inner() {
                    System.out.println(c);
                }
            };
        }
    };

    int a2 = 4; // violation 'Unused named local variable 'a2''
    new WithField() {
        void test() {
            new WithField() {
                void inner() {
                    System.out.println(a2);
                }
            };
        }
    };
}

class WithField {
    int a;
    int a2;
}
