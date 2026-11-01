/*
SingleSpaceSeparator
validateComments = true


*/

// non-compiled with javac: Compilable with Java25

int  total = 1; // violation 'Use a single space to separate non-whitespace'
int single = 2;
String message = "multiple  spaces inside a string";
int /* comment */  afterComment = 3; // violation 'Use a single space to separate non-whitespace'
int beforeComment = 4;  /* comment */ // violation 'Use a single space to separate non-whitespace'

void method(int  parameter) { // violation 'Use a single space to separate non-whitespace'
    int value =  parameter; // violation 'Use a single space to separate non-whitespace'
    int /* comment */  local = value; // violation 'Use a single space to separate non-whitespace'
    total = local;  // violation 'Use a single space to separate non-whitespace'
    if (value > 0) {
        total =  value; // violation 'Use a single space to separate non-whitespace'
    }
}

void main() {
    method(1);
}
