/*
OverloadMethodsDeclarationOrder
modifierGroups = static
orderByIncreasingParameterCount = (default)false

*/

// non-compiled with javac: Compilable with Java25
void foo() { }

static void bar() { }

// violation below 'All overloaded methods should be placed next to each other.'
void foo(String s) { }

static void foo(int i) { }

void main() { }
