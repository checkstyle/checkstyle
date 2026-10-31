/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = false
applyToPackage = false
applyToPrivate = false


*/

// non-compiled with javac: Compilable with Java25

void main() { }

void Bad_Method() { }

class Helper {
    private void Bad_Method2() {
    }

    protected void Bad_Method3() {
    }

    public void Bad_Method4() { // violation 'Name 'Bad_Method4' must match pattern'
    }
}
