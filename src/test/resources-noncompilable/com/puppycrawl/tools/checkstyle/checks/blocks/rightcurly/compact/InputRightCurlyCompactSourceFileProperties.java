/*
RightCurly
option = ALONE
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE

*/

// non-compiled with javac: Compilable with Java25

void checkBraces(boolean flag) {
    if (flag) {
        System.out.println("if");
    } else { // violation ''}' at column 5 should be alone on a line'
        System.out.println("else");
    }
    try {
        System.out.println("try");
    } catch (Exception exception) { // violation ''}' at column 5 should be alone on a line'
        System.out.println(exception);
    }
    finally {
        System.out.println("finally");
    }
    if (flag) { System.out.println("single line"); }
    // violation above ''}' at column 52 should be alone on a line'
    if (flag) {
        System.out.println("standalone");
    }
}

void main() {}
