/*
ClassTypeParameterName
format = ^[a-z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class Custom<t> {
    t value;
}

class CustomBad<T> { // violation 'Name 'T' must match pattern'
    T value;
}

class Multi<abc, Def> { // violation 'Name 'Def' must match pattern'
    abc first;
    Def second;
}
