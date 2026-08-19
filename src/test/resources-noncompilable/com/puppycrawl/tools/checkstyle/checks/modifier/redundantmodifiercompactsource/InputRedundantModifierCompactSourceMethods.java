/*
RedundantModifierCompactSource
tokens = (default)METHOD_DEF, VARIABLE_DEF


*/

// non-compiled with javac: Compilable with Java25

int packageField;
final int finalField = 1;
volatile int volatileField;
transient int transientField;
public int publicField; // violation 'Redundant 'public' modifier on a direct member.'
protected int protectedField; // violation 'Redundant 'protected' modifier on a direct member.'
private int privateField; // violation 'Redundant 'private' modifier on a direct member.'
static int staticField; // violation 'Redundant 'static' modifier on a direct member.'
public static int publicStaticField;
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'
private static int privateStaticField;
// 2 violations above:
// 'Redundant 'private' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'
static final int staticFinal = 1; // violation 'Redundant 'static' modifier on a direct member.'
public final int publicFinal = 1; // violation 'Redundant 'public' modifier on a direct member.'
// Compact source fields cannot declare multiple variables in one declaration.

void packageMethod() {}
synchronized void synchronizedMethod() {}
native void nativeMethod();

public void publicMethod() {} // violation 'Redundant 'public' modifier on a direct member.'
protected void protectedM() {} // violation 'Redundant 'protected' modifier on a direct member.'
private void privateMethod() {} // violation 'Redundant 'private' modifier on a direct member.'
static void staticMethod() {} // violation 'Redundant 'static' modifier on a direct member.'
final void finalMethod() {} // violation 'Redundant 'final' modifier on a direct member.'
strictfp void strictfpMethod() {} // violation 'Redundant 'strictfp' modifier on a direct member.'
public static void publicStaticMethod() {}
// 2 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'
private static void privateStaticMethod() {}
// 2 violations above:
// 'Redundant 'private' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'
public final strictfp void combinedMethod() {}
// 3 violations above:
// 'Redundant 'public' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
// 'Redundant 'strictfp' modifier on a direct member.'

@Deprecated
final void annotatedFinal() {} // violation 'Redundant 'final' modifier on a direct member.'

@SafeVarargs
final void requiredSafeVarargsFinal(String... values) {}
@java.lang.SafeVarargs
final void requiredFullyQualifiedSafeVarargsFinal(String... values) {}
@SafeVarargs
public final void publicSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'public' modifier on a direct member.'
@java.lang.SafeVarargs
static void staticSafeVarargs(String... values) {}
@SafeVarargs
private void privateSafeVarargs(String... values) {}
@SafeVarargs
static final void staticSafeVarargsFinal(String... values) {}
// 2 violations above:
// 'Redundant 'static' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
@SafeVarargs
private final void privateSafeVarargsFinal(String... values) {}
// 2 violations above:
// 'Redundant 'private' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
@SafeVarargs
private static final void privateStaticSafeVarargsFinal(String... values) {}
// 3 violations above:
// 'Redundant 'private' modifier on a direct member.'
// 'Redundant 'static' modifier on a direct member.'
// 'Redundant 'final' modifier on a direct member.'
final void nonSafeVarargsFinal(String... values) {}
// violation above 'Redundant 'final' modifier on a direct member.'

void main() {}
