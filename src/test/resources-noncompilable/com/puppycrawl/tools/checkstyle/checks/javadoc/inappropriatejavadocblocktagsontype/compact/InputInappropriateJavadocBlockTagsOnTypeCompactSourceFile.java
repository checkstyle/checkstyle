/*
InappropriateJavadocBlockTagsOnType
violateExecutionOnNonTightHtml = (default)false

*/

// non-compiled with javac: Compilable with Java25

/**
 * Invalid Javadoc with inappropriate tags.
 * @return Something invalid.
 * @throws Exception This is also invalid.
 */
public class InputInappropriateJavadocBlockTagsOnTypeCompactSourceFile {}
// 2 violations above:
//    'Invalid '@return' tag for'
//    'Invalid '@throws' tag for'

/** Valid Javadoc with no inappropriate tags. */
class ValidType {}

void main() {
}
