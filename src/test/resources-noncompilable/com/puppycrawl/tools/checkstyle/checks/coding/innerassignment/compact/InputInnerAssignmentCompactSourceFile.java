/*
InnerAssignment


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int value;
    String result = Integer.toString(value = 2); // violation 'Inner assignments should be avoided'
}
