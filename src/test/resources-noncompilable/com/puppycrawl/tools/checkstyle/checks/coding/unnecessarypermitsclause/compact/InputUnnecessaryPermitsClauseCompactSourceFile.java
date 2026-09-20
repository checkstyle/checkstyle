/*
UnnecessaryPermitsClause

*/

// non-compiled with javac: Compilable with Java25

sealed class CompactSealedOuter permits CompactInnerA, CompactInnerB {
    // violation above 'Unnecessary 'permits' clause for classes in the same compilation unit.'
}

final class CompactInnerA extends CompactSealedOuter {
}

final class CompactInnerB extends CompactSealedOuter {
}

void main() {
}
