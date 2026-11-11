/*
NonEmptyAtclauseDescription
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)PARAM_BLOCK_TAG, RETURN_BLOCK_TAG, THROWS_BLOCK_TAG, \
         EXCEPTION_BLOCK_TAG, DEPRECATED_BLOCK_TAG, SINCE_BLOCK_TAG


*/

// non-compiled with javac: Compilable with Java25

// violation 4 lines below 'At-clause should have a non-empty description.'
/**
 * Summary.
 *
 * @param input
 * @return the input
 */
String greet(String input) {
    return input;
}

void main() {
    System.out.println(greet("hi"));
}
