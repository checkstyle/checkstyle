/*
IllegalSymbol
symbolCodes = (default)0x2190-0x27BF, 0x1F600-0x1F64F, 0x1F680-0x1F6FF, 0x1F700-0x10FFFF
tokens = (default)COMMENT_CONTENT


*/

// non-compiled with javac: Compilable with Java25

// ✅ before a top-level field // violation 'Illegal symbol detected: '✅''
int field = 0;

String text = "✅ in a string literal";

// violation below 'Illegal symbol detected: '.''
/* café → block comment */
void main() {
    // plain comment
    System.out.println(text + field);
}

class Nested {
    // 🚀 in a nested class // violation 'Illegal symbol detected: '🚀''
    int nestedField = 0;
}
