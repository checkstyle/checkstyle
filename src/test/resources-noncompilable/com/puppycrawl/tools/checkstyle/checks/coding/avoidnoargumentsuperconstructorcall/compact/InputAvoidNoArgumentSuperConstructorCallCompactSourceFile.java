/*
AvoidNoArgumentSuperConstructorCall


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class Parent {
}

class Child extends Parent {

    Child() {
        super(); // violation 'Unnecessary call to superclass constructor with no arguments'
    }

}
