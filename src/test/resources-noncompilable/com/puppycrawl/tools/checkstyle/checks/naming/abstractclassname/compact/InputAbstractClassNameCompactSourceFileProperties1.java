/*
AbstractClassName
format = ^Custom.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

abstract class CustomGood {
}

abstract class AbstractNamed { // violation 'Name 'AbstractNamed' must match pattern'
}

class CustomNotAbstract { // violation 'Class 'CustomNotAbstract' must be declared as 'abstract'.'
}
