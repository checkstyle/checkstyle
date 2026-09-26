/*
JavadocVariable
accessModifiers = package
considerEnclosingScope = true
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

// non-compiled with javac: Compilable with Java25

int topLevel; // violation 'Missing a Javadoc comment'

private static class Hidden {

    int inHidden;
}

void main() {
}
