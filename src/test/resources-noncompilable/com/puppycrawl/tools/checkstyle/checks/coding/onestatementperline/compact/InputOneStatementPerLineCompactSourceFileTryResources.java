/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

// non-compiled with javac: Compilable with Java25

import java.io.StringReader;

void main() throws Exception {
    // violation below 'Only one statement per line allowed.'
    try (StringReader a = new StringReader(""); StringReader b = new StringReader("")) {
        a.read(); b.read(); // violation 'Only one statement per line allowed.'
    }
    try (StringReader c = new StringReader("");
         StringReader d = new StringReader("")) {
        c.read();
        d.read();
    }
    // violation below 'Only one statement per line allowed.'
    try (AutoCloseable e = () -> { }; AutoCloseable f = () -> { }) {
        e.close();
        f.close();
    }
}
