/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE

*/

// non-compiled with javac: Compilable with Java25

void checkBraces(boolean flag) {
    if (flag) {
        System.out.println("if");
    }
    // violation above ''}' at column 5 should be on the same line as .*/else'
    else {
        System.out.println("else");
    }
    try {
        System.out.println("try");
    } catch (Exception exception) {
        System.out.println(exception);
    }
    // violation above ''}' at column 5 should be on the same line as .*/finally'
    finally {
        System.out.println("finally");
    }
    if (flag) {
        System.out.println("standalone");
    }
    if (flag) {
        System.out.println("break before"); }
    // violation above ''}' at column 45 should have line break before'
    if (flag) {
        System.out.println("after");
    } System.out.println("same line");
    // violation above ''}' at column 5 should be alone on a line'
    if (flag) { System.out.println("single line"); }
}

void main() {}
