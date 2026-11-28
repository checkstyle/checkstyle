/*
RightCurlyOfExpression
option = on_same_line

*/

// non-compiled with javac: Compilable with Java25

String[] multiLineArrayOk = new String[] {"One",
    "Two", };

String[] multiLineArrayViolation = new String[] {"One", "Two",
};
// violation above ''}' at column 1 should be on the previous line'

void main() {
    Runnable action = () -> { doSomething(); };
    action.run();
}

void doSomething() {
}
