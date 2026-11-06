/*
InterfaceTypeParameterName
format = (default)^[A-Z]$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

interface MyInterface<T> {
}

interface BadInterface<t> { // violation 'Name 't' must match pattern'
}

interface OtherBadInterface<TT> { // violation 'Name 'TT' must match pattern'
}
