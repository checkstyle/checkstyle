/*
LambdaBodyLength
max = (default)10


*/

// non-compiled with javac: Compilable with Java25

import java.util.function.IntSupplier;

void main() {
}

IntSupplier longSupplier = () -> { // violation 'Lambda body length is 12 lines'
    int total = 0;
    total += 1;
    total += 2;
    total += 3;
    total += 4;
    total += 5;
    total += 6;
    total += 7;
    total += 8;
    return total;
};

IntSupplier shortSupplier = () -> {
    return 1;
};
