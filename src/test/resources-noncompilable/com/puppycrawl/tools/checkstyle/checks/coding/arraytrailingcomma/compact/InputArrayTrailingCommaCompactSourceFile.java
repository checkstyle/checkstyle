/*
ArrayTrailingComma
alwaysDemandTrailingComma = (default)false


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int[] a1 = {
        1,
        2,
        3,
    };

    int[] a2 = {
        1,
        2,
        3 // violation 'Array should contain trailing comma.'
    };

    int[] b1 = {1, 2, 3,};
    int[] b2 = {1, 2, 3};
}
