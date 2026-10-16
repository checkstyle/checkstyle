/*
HexLiteralCase


*/

// non-compiled with javac: Compilable with Java25

void main() {
    // violation below 'Should use uppercase hexadecimal letters.'
    int x = 0xab;
    int y = 0xAB;
}

class Foo {
    // violation below 'Should use uppercase hexadecimal letters.'
    int bad = 0xcd;
    int good = 0xCD;
}
