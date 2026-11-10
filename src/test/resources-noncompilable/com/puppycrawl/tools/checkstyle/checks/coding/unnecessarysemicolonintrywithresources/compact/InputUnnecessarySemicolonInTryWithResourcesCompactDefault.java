/*
UnnecessarySemicolonInTryWithResources
allowWhenNoBraceAfterSemicolon = (default)true


*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;
import java.io.PipedReader;
import java.io.Reader;

void main() throws IOException {
    try (Reader r1 = new PipedReader();) { } // violation 'Unnecessary semicolon'
    // violation below 'Unnecessary semicolon'
    try (Reader r1 = new PipedReader(); Reader r2 = new PipedReader();) { }
    try (Reader r2 = new PipedReader(); Reader r3 = new PipedReader()) { }
    try (Reader r4 = new PipedReader();
        Reader r5 = new PipedReader();
        ) { }
    try (Reader r6 = new PipedReader();
        Reader r7
            = new PipedReader();
        ) { }
}
