/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

// non-compiled with javac: contains intentionally invalid Java syntax

@SafeVarargs
final void fixedArity(String value) {}
// violation above 'Redundant 'final' modifier on a direct member.'

@SafeVarargs
public void missingEligibilityModifier(String... values) {}
// violation above 'Redundant 'public' modifier on a direct member.'

void main() {}
