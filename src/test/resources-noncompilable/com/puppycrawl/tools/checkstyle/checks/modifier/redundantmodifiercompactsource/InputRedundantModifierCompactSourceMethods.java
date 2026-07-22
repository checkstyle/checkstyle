/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF


*/

// non-compiled with javac: Compilable with Java25

void helper() {}
private void privateMethod() {}
static void staticMethod() {}
synchronized void synchronizedMethod() {}
native void nativeMethod();

public void publicMethod() {}
// violation above 'Redundant 'public' modifier on a direct member.'
protected void protectedMethod() {}
// violation above 'Redundant 'protected' modifier on a direct member.'
final void finalMethod() {}
// violation above 'Redundant 'final' modifier on a direct member.'
strictfp void strictfpMethod() {}
// violation above 'Redundant 'strictfp' modifier on a direct member.'
public final strictfp void combinedMethod() {}
// 3 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
// 'Redundant 'strictfp' modifier on a direct member.'

@Deprecated
final void annotatedFinal() {}
// violation above 'Redundant 'final' modifier on a direct member.'
static final void staticFinal() {}
// violation above 'Redundant 'final' modifier on a direct member.'
native final void nativeFinal();
// violation above 'Redundant 'final' modifier on a direct member.'

@SafeVarargs
final void requiredSafeVarargsFinal(String... values) {}
@SafeVarargs
static final void staticSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'final' modifier on a direct member.'
@SafeVarargs
private final void privateSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'final' modifier on a direct member.'
@SafeVarargs
public final void publicSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'public' modifier on a direct member.'
@java.lang.SafeVarargs
final void requiredFullyQualifiedSafeVarargsFinal(String... values) {}
final void nonSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'final' modifier on a direct member.'

void localDeclarations(final int parameter) {
    final int local = parameter;
    class Local {
        public final void method() {}
    }
}

class Nested {
    public void publicMethod() {}
    protected void protectedMethod() {}
    final void finalMethod() {}
    strictfp void strictfpMethod() {}
}

void main() {}
