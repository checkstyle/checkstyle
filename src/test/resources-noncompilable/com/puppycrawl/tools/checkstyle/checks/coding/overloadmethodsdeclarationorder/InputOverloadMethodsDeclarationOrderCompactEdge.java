/*
OverloadMethodsDeclarationOrder

*/
void helper() { }
void process(int x) { }
void process(String s) { }
void helper(int x) { } // violation 'All overloaded methods should be placed next to each other.'
void main() { }
