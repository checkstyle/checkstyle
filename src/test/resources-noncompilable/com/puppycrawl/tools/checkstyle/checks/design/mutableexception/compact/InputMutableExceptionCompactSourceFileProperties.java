/*
MutableException
format = ^.*Problem$
extendedClassNameFormat = ^.*Exception$


*/

// non-compiled with javac: Compilable with Java25

void main() {}

class MyProblem extends Exception {
    int errorCode; // violation 'The field 'errorCode' must be declared final'
}

class MyException extends Exception {
    int otherCode;
}
