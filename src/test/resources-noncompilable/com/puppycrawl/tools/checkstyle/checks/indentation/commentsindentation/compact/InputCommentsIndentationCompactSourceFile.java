/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;

    // violation '.* incorrect .* level 4, expected is 0, .* same .* as line 13.'
List<String> names = List.of();

// comment before a top-level member
int field1 = 0;

    // violation '.* incorrect .* level 4, expected is 0, .* same .* as line 19.'
int field2 = 0;

/* block comment */
int field3 = 0;

  /* // violation '.* incorrect .* level 2, expected is 0, .* same .* as line 26.'
   */
int field4 = 0;

void main() {
    // comment in a method
    int local = 0;
        // violation '.* incorrect .* level 8, expected is 4, .* same .* as line 32.'
    System.out.println(local);
  /* // violation '.* incorrect .* level 2, expected is 4, .* same .* as line 35.'
   */
    System.out.println(field1);
}

int method() {
    return 0;
  // violation '.* incorrect .* level 2, expected is 4, .* same .* as line 39.'
}

    // violation '.* incorrect .* level 4, expected is 0, .* same .* as line 44.'
class Nested {
    // comment in a nested class
    int nestedField = 0;
// violation '.* incorrect .* level 0, expected is 4, .* same .* as line 48.'
    int otherField = 0;
}

// comment between top-level members
record Point(int x) {
}
