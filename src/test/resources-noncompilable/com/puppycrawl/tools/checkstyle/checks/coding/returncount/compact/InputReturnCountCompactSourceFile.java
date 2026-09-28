/*
ReturnCount
max = (default)2
maxForVoid = (default)1
format = (default)^equals$
tokens = (default)CTOR_DEF, METHOD_DEF, LAMBDA, LITERAL_RETURN


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

int classify(int value) { // violation 'Return count is 3'
    if (value < 0) {
        return -1;
    }
    if (value == 0) {
        return 0;
    }
    return 1;
}

void execute(boolean active) { // violation 'Return count is 2'
    if (active) {
        return;
    }
    return;
}

boolean equals(int value) {
    if (value < 0) {
        return false;
    }
    if (value == 0) {
        return true;
    }
    return false;
}

int atLimit(boolean active) {
    if (active) {
        return 1;
    }
    return 0;
}

void voidAtLimit() {
    return;
}

int withLambda(int value) {
    java.util.function.IntUnaryOperator operator = number -> {
        if (number < 0) {
            return -1;
        }
        return 1;
    };
    return operator.applyAsInt(value);
}
