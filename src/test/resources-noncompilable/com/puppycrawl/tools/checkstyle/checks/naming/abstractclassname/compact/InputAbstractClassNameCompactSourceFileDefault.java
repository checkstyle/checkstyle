/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

abstract class AbstractGood {
}

abstract class BadlyNamed { // violation 'Name 'BadlyNamed' must match pattern'
}

class AbstractPlain { // violation 'Class 'AbstractPlain' must be declared as 'abstract'.'
}

class PlainClass {
}
