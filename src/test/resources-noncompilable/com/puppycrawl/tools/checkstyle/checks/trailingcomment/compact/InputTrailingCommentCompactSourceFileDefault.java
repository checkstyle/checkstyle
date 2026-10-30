/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = (default)(null)


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Don't use trailing comments.'
int first = 1; // trailing comment
// standalone comment
// violation below 'Don't use trailing comments.'
int second = 2; /* trailing block */
void checkTrailing() {
    // violation below 'Don't use trailing comments.'
    int local = 3; // local trailing
    System.out.println(local);
} // closing brace comment

void main() {
}
