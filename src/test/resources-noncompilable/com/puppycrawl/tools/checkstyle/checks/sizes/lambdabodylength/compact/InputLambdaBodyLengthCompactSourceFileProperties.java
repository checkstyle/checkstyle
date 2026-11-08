/*
LambdaBodyLength
max = 3


*/

// non-compiled with javac: Compilable with Java25

import java.util.function.IntSupplier;

void main() {
}

int compute() {
    IntSupplier longer = () -> { // violation 'Lambda body length is 5 lines'
        int value = 1;
        value += 2;
        return value;
    };
    IntSupplier shorter = () -> {
        return 2;
    };
    return longer.getAsInt() + shorter.getAsInt();
}
