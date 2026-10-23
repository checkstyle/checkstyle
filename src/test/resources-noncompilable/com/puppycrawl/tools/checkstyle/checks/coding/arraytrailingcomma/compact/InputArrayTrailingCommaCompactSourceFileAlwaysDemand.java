/*
ArrayTrailingComma
alwaysDemandTrailingComma = true


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int[] a = {1, 2, 3}; // violation 'Array should contain trailing comma.'
    int[] b = {1, 2, 3,};

    int[] c = {
        1,
        2,
        3 // violation 'Array should contain trailing comma.'
    };
}
