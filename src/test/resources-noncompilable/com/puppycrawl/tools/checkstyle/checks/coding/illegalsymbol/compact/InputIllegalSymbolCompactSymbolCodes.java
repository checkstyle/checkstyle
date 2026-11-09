/*
IllegalSymbol
symbolCodes = 0x0080-0x10FFFF
tokens = COMMENT_CONTENT, STRING_LITERAL, CHAR_LITERAL, TEXT_BLOCK_CONTENT, IDENT


*/

// non-compiled with javac: Compilable with Java25

// café before a top-level field // violation 'Illegal symbol detected: 'é''
int field = 0;

// violation below 'Illegal symbol detected: '.''
String text = "naïve";

// violation below 'Illegal symbol detected: '.''
char sign = '€';

// violation below 'Illegal symbol detected: '.''
int größe = 1;

// violation below 'Illegal symbol detected: '.''
String block = """
    left → right
    """;

void main() {
    // plain comment
    // violation below 'Illegal symbol detected: '.''
    System.out.println(text + field + sign + größe + block);
}

record Point(int x) {
    // ascii only
    static String label = "point";
}
