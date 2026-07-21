/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = (default)false

*/

// non-compiled with javac: Compilable with Java25
void helper() { }
void process(int x) { }
void process(String s) { }
void helper(int x) { } // violation 'All overloaded methods should be placed next to each other.'
void main() { }
