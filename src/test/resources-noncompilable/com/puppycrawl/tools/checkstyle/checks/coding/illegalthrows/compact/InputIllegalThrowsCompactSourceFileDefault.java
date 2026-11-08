/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

// non-compiled with javac: Compilable with Java25

void main() throws RuntimeException { // violation 'Throwing 'RuntimeException' is not allowed'
}

void valid() throws Exception {
}

void error() throws java.lang.Error { // violation 'Throwing 'java.lang.Error' is not allowed'
}

protected void finalize() throws Throwable {
}

@Override
public String toString() throws RuntimeException {
    return "";
}
