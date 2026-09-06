/*
UpperEll


*/

// non-compiled with javac: Compilable with Java25

long field = 666l + 666L; // violation 'Should use uppercase 'L'.'

void main() {
    long local = 1l; // violation 'Should use uppercase 'L'.'
    long upper = 1L;
    System.out.println(field + local + upper);
}
