/*
UnusedTryResourceShouldBeUnnamed


*/

// non-compiled with javac: Compilable with Java25

import java.io.StringReader;

StringReader reader;

void main() {
}

void ignore() throws Exception {
    // violation below 'Unused try resource 'unused' should be unnamed'
    try (AutoCloseable unused = () -> { }) {
        System.out.println("working");
    }
}

int firstChar(String text) throws Exception {
    try (StringReader input = new StringReader(text)) {
        return input.read();
    }
}

void unnamed() throws Exception {
    try (AutoCloseable _ = () -> { }) {
        System.out.println("working");
    }
}

void shadow() throws Exception {
    // violation below 'Unused try resource 'reader' should be unnamed'
    try (StringReader reader = new StringReader("text")) {
        System.out.println("reading");
    }
}
