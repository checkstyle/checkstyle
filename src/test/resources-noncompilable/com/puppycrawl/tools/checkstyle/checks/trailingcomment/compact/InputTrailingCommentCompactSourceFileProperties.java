/*
TrailingComment
format = ^[\\s]*$
legalComment = ^NOI18N$


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Don't use trailing comments.'
int first = 1; // trailing comment
String name = "Some Name"; //NOI18N
// violation below 'Don't use trailing comments.'
String other = "Other"; /* bad block */
String legal = "Legal"; /*NOI18N*/
void checkTrailing() {
    System.out.println(name);
} // method end comment
// violation above 'Don't use trailing comments.'

void main() {
}
