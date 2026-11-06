/*
InterfaceTypeParameterName
format = ^[A-Z][a-z]+$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

interface MyInterface<Type> {
}

interface BadInterface<T> { // violation 'Name 'T' must match pattern'
}

interface OtherBadInterface<lower> { // violation 'Name 'lower' must match pattern'
}
