/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
ignoreEnhancedForColon = (default)true
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
// 2 violations above:
//  ''{' is not followed by whitespace.'
//  ''}' is not preceded with whitespace.'

class WithCtor {
    WithCtor() {}
    // 2 violations above:
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
}

void emptyMethod() {}
// 2 violations above:
//  ''{' is not followed by whitespace.'
//  ''}' is not preceded with whitespace.'

void checkOthers(boolean flag, String[] items) {
    int value=1;
    // 2 violations above:
    //  ''=' is not followed by whitespace.'
    //  ''=' is not preceded with whitespace.'
    while (flag) {}
    // 2 violations above:
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    for (int i = 0; i < 1; i++) {}
    // 2 violations above:
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    Runnable noop = () -> {};
    // 2 violations above:
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    try {
        System.out.println(value);
    } catch (Exception exception) {}
    // 2 violations above:
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    switch (value) {
        case 1 -> {}
        // 2 violations above:
        //  ''{' is not followed by whitespace.'
        //  ''}' is not preceded with whitespace.'
        default -> {
            System.out.println(value);
        }
    }
    for (String item:items) {
        System.out.println(item);
    }
}

void main() {
}
