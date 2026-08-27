/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false

*/
// non-compiled with javac: Compilable with Java9

// violation 4 lines below 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
/**
 * Javadoc without the deprecated tag.
 */
@Deprecated
module com.example.app {
}
