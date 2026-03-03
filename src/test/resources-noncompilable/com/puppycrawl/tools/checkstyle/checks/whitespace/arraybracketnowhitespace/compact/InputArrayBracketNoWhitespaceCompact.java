/*
ArrayBracketNoWhitespace


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int[] arr2 = new int [10]; // violation ''\[' is preceded with whitespace.'
    int[] arr3 = new int[ 10]; // violation ''\[' is followed by whitespace.'

    int a = arr2[0] ; // violation ''\]' is followed by whitespace.'
    int b = arr2 [0]; // violation ''\[' is preceded with whitespace.'
}
