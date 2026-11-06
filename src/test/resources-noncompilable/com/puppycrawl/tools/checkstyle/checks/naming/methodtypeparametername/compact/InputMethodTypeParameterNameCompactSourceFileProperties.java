/*
MethodTypeParameterName
format = ^[A-Z][a-z]+$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

<Type> void goodMethod() {
}

<T> void badMethod() { // violation 'Name 'T' must match pattern'
}

<lower> void otherBadMethod() { // violation 'Name 'lower' must match pattern'
}
