/*
MethodTypeParameterName
format = (default)^[A-Z]$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

<T> void goodMethod() {
}

<t> void badMethod() { // violation 'Name 't' must match pattern'
}

<TT> void otherBadMethod() { // violation 'Name 'TT' must match pattern'
}
