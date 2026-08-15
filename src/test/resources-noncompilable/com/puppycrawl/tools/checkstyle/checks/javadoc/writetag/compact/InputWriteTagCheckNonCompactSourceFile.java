/*
WriteTag
tag = @since
tagFormat = \\S
tokens = METHOD_DEF
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/*
 * File header block comment.
 */

/**
 * First method with @since.
 */
void first() { } // violation 'Javadoc comment is missing @since tag.'

/**
 * Second method also with @since.
 * @since 2.0
 */
void second() { }

void main() { }
