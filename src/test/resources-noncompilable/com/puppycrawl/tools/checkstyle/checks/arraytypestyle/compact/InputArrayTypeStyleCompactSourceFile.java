/*
ArrayTypeStyle
javaStyle = (default)true


*/

// non-compiled with javac: Compilable with Java25

int[] javaStyleField = new int[1];

int cStyleMethod()[] { // violation 'Array brackets at illegal position.'
    return javaStyleField;
}

class Nested {
    int cStyleField[] = new int[1]; // violation 'Array brackets at illegal position.'
}

void main() {
    System.out.println(cStyleMethod().length + new Nested().cStyleField.length);
}
