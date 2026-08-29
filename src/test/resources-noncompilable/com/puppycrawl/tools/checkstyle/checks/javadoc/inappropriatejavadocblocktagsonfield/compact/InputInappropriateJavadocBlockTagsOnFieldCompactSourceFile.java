/*
InappropriateJavadocBlockTagsOnField
violateExecutionOnNonTightHtml = (default)false

*/

// non-compiled with javac: Compilable with Java25

/**
 * @param bad bad
 * @return bad
 * @throws Exception bad
 */
int field;
// violation above 'Invalid '@param' tag for 'field'.'
// violation 2 lines above 'Invalid '@return' tag for 'field'.'
// violation 3 lines above 'Invalid '@throws' tag for 'field'.'

/**
 * Valid javadoc.
 */
int validField;

void main() {
}
