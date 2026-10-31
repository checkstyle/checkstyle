/*
MethodName
format = ^[a-zA-Z][a-zA-Z0-9_]*$
allowClassName = true
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

void main() { }

void Bad_Method() { }

class Helper {
    public void BadName() {
    }

    int Helper() {
        return 0;
    }
}
