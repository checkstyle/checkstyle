/*
WhitespaceAround
allowEmptyConstructors = true
allowEmptyMethods = true
allowEmptyTypes = true
allowEmptyLoops = true
allowEmptyLambdas = true
allowEmptyCatches = true
allowEmptySwitchBlockStatements = true
ignoreEnhancedForColon = false
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, \
         LAMBDA, LAND, LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TR \
          Y, LITERAL_WHILE, LOR, \
         LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, NOT_EQUAL, PLUS, PLUS_ASSIGN, \
         QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, SR_ASSIGN, STAR, \
         STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND, LITERAL_WHEN

*/

// non-compiled with javac: Compilable with Java25

class EmptyType {}

class WithCtor {
    WithCtor() {}
}

void emptyMethod() {}

void checkOthers(boolean flag, String[] items) {
    int value=1;
    // 2 violations above:
    //  ''=' is not followed by whitespace.'
    //  ''=' is not preceded with whitespace.'
    while (flag) {}
    for (int i = 0; i < 1; i++) {}
    Runnable noop = () -> {};
    try {
        System.out.println(value);
    } catch (Exception exception) {}
    switch (value) {
        case 1 -> {}
        default -> {
            System.out.println(value);
        }
    }
    for (String item:items) {
    // 2 violations above:
    //  '':' is not followed by whitespace.'
    //  '':' is not preceded with whitespace.'
        System.out.println(item);
    }
}

void main() {
}
