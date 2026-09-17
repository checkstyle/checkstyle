/*
ArrayTypeStyle
javaStyle = false


*/

// non-compiled with javac: Compilable with Java25

int[] javaStyleField = new int[1]; // violation 'Array brackets at illegal position.'

class Nested {
    int cStyleField[] = new int[1];
}

void main() {
    System.out.println(javaStyleField.length + new Nested().cStyleField.length);
}
