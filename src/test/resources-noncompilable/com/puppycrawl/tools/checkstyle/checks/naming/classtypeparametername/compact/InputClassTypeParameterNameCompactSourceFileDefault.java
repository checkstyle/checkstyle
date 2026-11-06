/*
ClassTypeParameterName
format = (default)^[A-Z]$


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class Good<T> {
    T value;
}

class Bad<type> { // violation 'Name 'type' must match pattern'
    type value;
}

class AlsoBad<TT> { // violation 'Name 'TT' must match pattern'
    TT value;
}
