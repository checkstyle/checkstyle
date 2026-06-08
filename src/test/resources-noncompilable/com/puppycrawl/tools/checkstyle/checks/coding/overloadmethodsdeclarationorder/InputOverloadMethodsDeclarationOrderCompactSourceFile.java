/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = (default)false

*/

// non-compiled with javac: Compilable with Java25
void process(int x) { }

void helper() { }

// violation below 'All overloaded methods should be placed next to each other.'
void process(String s) { }

void main() { }
