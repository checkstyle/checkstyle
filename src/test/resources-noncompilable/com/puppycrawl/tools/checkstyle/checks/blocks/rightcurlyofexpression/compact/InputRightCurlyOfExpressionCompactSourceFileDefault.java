/*
RightCurlyOfExpression
option = (default)on_new_line_or_singleline

*/

// non-compiled with javac: Compilable with Java25

String[] singleLineArray = new String[] {"One", "Two", };

String[] multiLineArrayViolation = new String[] {"One",
    "Two", };
// violation above ''}' at column 12 should be on a new line'

void main() {
    Runnable action = () -> { doSomething(); };
    action.run();
}

void doSomething() {
}
