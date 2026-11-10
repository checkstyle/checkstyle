/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)VARIABLE_DEF,ENUM_CONSTANT_DEF

*/

// non-compiled with javac: Compilable with Java25

// violation 2 lines below 'Missing a Javadoc comment.'
class Inner {
    public int shown;
}

void main() {
    System.out.println(new Inner().shown);
}
