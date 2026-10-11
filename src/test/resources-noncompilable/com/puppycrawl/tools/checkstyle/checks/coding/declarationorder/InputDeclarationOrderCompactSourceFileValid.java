/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

// non-compiled with javac: Compilable with Java25

public static int PUB_STATIC = 1;

private static int privStatic = 2;

public int pubInstance = 3;

protected int protInstance = 4;

private int privInstance = 5;

void helper() {
}

void main() {
    System.out.println(pubInstance);
}
