/*
JavadocThrowsOrder
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

// violation 5 lines below """@throws tag for 'AlphaException' should be
// placed alphabetically before 'ZebraException'."""
/**
 * Compact source Javadoc.
 * @throws ZebraException if zebra fails.
 * @throws AlphaException if alpha fails.
 */
void main() throws AlphaException, ZebraException {
}

class AlphaException extends Exception {
}

class ZebraException extends Exception {
}
