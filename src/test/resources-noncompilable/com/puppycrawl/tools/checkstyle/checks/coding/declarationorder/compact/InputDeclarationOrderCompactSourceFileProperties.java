/*
DeclarationOrder
ignoreConstructors = true
ignoreModifiers = true


*/

// non-compiled with javac: Compilable with Java25

private int privInstance = 1;

public int pubInstance = 2;

class Inner {

    void innerMethod() {
    }

    Inner() {
    }
}

void method1() {
}

int afterMethod = 3; // violation 'Instance variable definition in wrong order'
