/*
RightCurlyOfExpression
option = on_new_line

*/

// non-compiled with javac: Compilable with Java25

String[] multiLineArrayOk = new String[] {"One", "Two",
};

String[] singleLineArrayViolation = new String[] {"One", "Two", };
// violation above ''}' at column 65 should be on a new line'

void main() {
    Runnable action = () -> {
        doSomething();
    };
    action.run();
}

void doSomething() {
}
