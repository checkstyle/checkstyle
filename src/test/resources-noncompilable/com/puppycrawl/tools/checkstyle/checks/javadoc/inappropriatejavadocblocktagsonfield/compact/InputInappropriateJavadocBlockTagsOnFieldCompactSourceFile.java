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
// 3 violations above:
// 'Invalid '@param' tag for 'field'.'
// 'Invalid '@return' tag for 'field'.'
// 'Invalid '@throws' tag for 'field'.'

/**
 * Valid javadoc.
 */
int validField;

void main() {
}
