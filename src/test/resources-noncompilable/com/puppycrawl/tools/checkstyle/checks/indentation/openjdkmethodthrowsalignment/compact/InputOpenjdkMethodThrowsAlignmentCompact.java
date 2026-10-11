/*
OpenjdkMethodThrowsAlignment

*/

// non-compiled with javac: Compilable with Java25

void met(int a,
        int b)
        throws Exception {}
// violation above """The 'throws' clause should be indented 8 spaces relative to the
// method declaration or the previous line and should not align with the previous line."""


void main() {
}
