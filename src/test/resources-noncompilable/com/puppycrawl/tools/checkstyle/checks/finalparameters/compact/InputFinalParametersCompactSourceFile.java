/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void primitive(int number) { // violation 'Parameter number should be final.'
}

void reference(String text) { // violation 'Parameter text should be final.'
}

void finalParameters(final int number, final String text) {
}

void array(int[] numbers) { // violation 'Parameter numbers should be final.'
}

void varargs(String... values) { // violation 'Parameter values should be final.'
}

void blocks() {
    try {
        throw new Exception();
    }
    catch (Exception exception) {
        // Catch parameters are not checked with the default tokens.
    }
    for (String value : new String[0]) {
        // Enhanced-for parameters are not checked with the default tokens.
    }
}

class Nested {
    Nested(String text) { // violation 'Parameter text should be final.'
    }

    Nested(final int number) {
    }
}
