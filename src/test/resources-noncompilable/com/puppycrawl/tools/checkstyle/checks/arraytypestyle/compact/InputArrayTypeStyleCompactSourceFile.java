/*
ArrayTypeStyle
javaStyle = (default)true


*/

// non-compiled with javac: Compilable with Java25

int[] javaStyleField = new int[1];

int cStyleField[] = new int[1]; // violation 'Array brackets at illegal position.'

int[] javaStyleMethod() {
    return javaStyleField;
}

int cStyleMethod()[] { // violation 'Array brackets at illegal position.'
    return cStyleField;
}

void main() {
    System.out.println(javaStyleMethod().length + cStyleMethod().length);
}
