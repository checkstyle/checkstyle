/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = ignored
tokens = (default)VARIABLE_DEF,ENUM_CONSTANT_DEF

*/

// non-compiled with javac: Compilable with Java25

// violation 4 lines below 'Missing a Javadoc comment.'
class Inner {
    public int ignored;

    public int shown;
}

void main() {
    System.out.println(new Inner().shown);
}
