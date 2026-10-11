/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

void main() { }

void doStuff() { }

void Bad_Method() { } // violation 'Name 'Bad_Method' must match pattern'

class Helper {
    // 2 violations 3 lines below:
    //  'Method Name 'Helper' must not equal the enclosing class name.'
    //  'Name 'Helper' must match pattern'
    int Helper() {
        return 0;
    }
}
