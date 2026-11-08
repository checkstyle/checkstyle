/*
MutableException
format = (default)^.*Exception$|^.*Error$|^.*Throwable$
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

// non-compiled with javac: Compilable with Java25

void main() {}

class MyException extends Exception {
    int errorCode; // violation 'The field 'errorCode' must be declared final'

    final int okCode = 0;
}
