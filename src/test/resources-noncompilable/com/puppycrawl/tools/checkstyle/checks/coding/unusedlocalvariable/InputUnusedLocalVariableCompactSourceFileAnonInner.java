/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22

*/

// non-compiled with javac: Compilable with Java25

class Super {
    int a;
}

void main() {
    int a = 1; // violation 'Unused named local variable 'a''
    int b = 2;
    new Super() {
        void test() {
            System.out.println(a);
            System.out.println(b);
        }
    };
}

void other() {
    int a = 1;
    new Object() {
        void test() {
            System.out.println(a);
        }
    };
}

class Holder {
    void method() {
        int a = 1; // violation 'Unused named local variable 'a''
        new Super() {
            void test() {
                System.out.println(a);
            }
        };
    }
}
