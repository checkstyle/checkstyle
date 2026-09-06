/*
MultipleVariableDeclarations


*/

// non-compiled with javac: Compilable with Java25

int first, second; // violation 'Each variable declaration must be in its own statement.'

int third;

void main() {
    int local1, local2; // violation 'Each variable declaration must be in its own statement.'
    int local3 = 0; int local4 = 0; // violation 'Only one variable definition per line allowed.'
    System.out.println(first + second + third + local1 + local2 + local3 + local4);
}
