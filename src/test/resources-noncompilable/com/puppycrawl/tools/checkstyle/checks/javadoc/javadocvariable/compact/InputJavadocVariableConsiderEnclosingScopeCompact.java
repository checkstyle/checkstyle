/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
considerEnclosingScope = true
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

// non-compiled with javac: Compilable with Java25

int topLevel;

class Named {

    int inNamedClass; // violation 'Missing a Javadoc comment'
}

void main() {
}
