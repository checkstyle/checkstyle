/*
ParameterName
accessModifiers = (default)public, protected, package, private
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false


*/

// non-compiled with javac: Compilable with Java25

void method(int good, int Bad) { // violation 'Name 'Bad' must match pattern'
}

interface Shape {
    void draw(int BadParam); // violation 'Name 'BadParam' must match pattern'
}

class Circle implements Shape {
    @Override
    public void draw(int BadParam) { // violation 'Name 'BadParam' must match pattern'
    }
}

void main() {
}
