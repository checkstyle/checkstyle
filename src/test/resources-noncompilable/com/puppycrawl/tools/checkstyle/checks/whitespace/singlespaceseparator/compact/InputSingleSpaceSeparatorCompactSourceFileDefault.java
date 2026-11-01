/*
SingleSpaceSeparator
validateComments = (default)false


*/

// non-compiled with javac: Compilable with Java25

int  total = 1; // violation 'Use a single space to separate non-whitespace'
int single = 2;
String message = "multiple  spaces inside a string";
int /* comment */  afterComment = 3;
int beforeComment = 4;  /* comment */

void method(int  parameter) { // violation 'Use a single space to separate non-whitespace'
    int value =  parameter; // violation 'Use a single space to separate non-whitespace'
    int /* comment */  local = value;
    total = local;  // comment
    if (value > 0) {
        total =  value; // violation 'Use a single space to separate non-whitespace'
    }
}

void main() {
    method(1);
}
