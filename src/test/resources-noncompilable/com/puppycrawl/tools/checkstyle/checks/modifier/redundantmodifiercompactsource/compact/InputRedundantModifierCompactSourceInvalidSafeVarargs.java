/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SafeVarargs
final void fixedArity(String value) {}
// violation above 'Redundant 'final' modifier on a direct member.'

@SafeVarargs
public void missingEligibilityModifier(String... values) {}
// violation above 'Redundant 'public' modifier on a direct member.'

@Deprecated
public static void m3(String... a) {}
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'

void main() {}
