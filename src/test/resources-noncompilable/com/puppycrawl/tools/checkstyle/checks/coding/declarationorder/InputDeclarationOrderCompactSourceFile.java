/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

// non-compiled with javac: Compilable with Java25

// Every member below belongs to the implicit class synthesized for this JEP 512
// compact source file. The check must enforce the same declaration order it
// applies inside an ordinary class body.

static int staticVar = 1;

public int pubInstance = 2;

private int privInstance = 3;

public int pubInstance2 = 4; // violation 'Variable access definition in wrong order'

void method1() {
}

int afterMethod = 5; // violation 'Instance variable definition in wrong order'

static int afterMethodStatic = 6; // violation 'Static variable definition in wrong order'

class Inner {

    void innerMethod() {
    }

    int innerField = 7; // violation 'Instance variable definition in wrong order'
}

void main() {
}
