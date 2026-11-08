/*
IllegalThrows
illegalClassNames = NullPointerException
ignoredMethodNames = ignoredMethod
ignoreOverriddenMethods = false


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Throwing 'NullPointerException' is not allowed'
void main() throws NullPointerException {
}

void ignoredMethod() throws NullPointerException {
}

@Override
public String toString() throws NullPointerException {
    // violation above 'Throwing 'NullPointerException' is not allowed'
    return "";
}

void valid() throws RuntimeException {
}
