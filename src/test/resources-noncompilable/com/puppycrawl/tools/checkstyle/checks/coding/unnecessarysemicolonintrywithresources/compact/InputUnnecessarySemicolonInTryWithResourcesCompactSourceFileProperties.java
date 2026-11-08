/*
UnnecessarySemicolonInTryWithResources
allowWhenNoBraceAfterSemicolon = false


*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;
import java.io.PipedReader;
import java.io.Reader;

void main() throws IOException {
    try (Reader reader = new PipedReader(); // violation 'Unnecessary semicolon.'
        ) {
    }

    try (Reader other = new PipedReader();) { // violation 'Unnecessary semicolon.'
    }
}
