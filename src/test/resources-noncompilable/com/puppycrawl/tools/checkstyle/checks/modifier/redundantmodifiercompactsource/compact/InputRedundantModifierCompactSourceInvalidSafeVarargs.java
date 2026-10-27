/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

// non-compiled with javac: Compilable with Java25

@SafeVarargs
final void fixedArity(String... value) {}

@SafeVarargs
private void missingEligibilityModifier(String... values) {}

@Deprecated
public static void m3(String... a) {}
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'

void main() {}
