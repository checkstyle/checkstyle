/*
OverloadMethodsDeclarationOrder
orderByIncreasingParameterCount = true

*/

// non-compiled with javac: Compilable with Java25

void process(int x, int y) { }

// violation below 'Overloaded methods should be ordered by increasing parameter count.'
void process(int x) { }

void main() { }
