/*
ParameterName
accessModifiers = public
format = ^[a-zA-Z][a-zA-Z0-9]*$
ignoreOverridden = true


*/

// non-compiled with javac: Compilable with Java25

void method(int Bad_Name) {
}

interface Shape {
    void draw(int BadParam);
}

class Circle implements Shape {
    @Override
    public void draw(int BadParam) {
    }
}

class Other {
    public void draw2(int Bad_Param) { // violation 'Name 'Bad_Param' must match pattern'
    }

    public void draw3(int Good) {
    }
}

void main() {
}
