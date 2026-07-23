/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF


*/

// non-compiled with javac: Compilable with Java25
// Java 25 compact source file.

int value;
final int constant = 1;
public static int other;

void helper() {}
public static void another() {}

final void finalized() {} // violation 'Redundant 'final' modifier on a direct member.'

// violation below 'Redundant 'strictfp' modifier on a direct member.'
strictfp double calculate() { return 1.0; }

public final strictfp double combined() { return 2.0; }
// 2 violations above:
// 'Redundant 'final' modifier on a direct member.'
// 'Redundant 'strictfp' modifier on a direct member.'

class Nested {
    final void finalized() {}
    strictfp double calculate() { return 3.0; }
}

void main() {}
