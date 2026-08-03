/*
InappropriateJavadocBlockTagsOnType
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsontype;

public class InputInappropriateJavadocBlockTagsOnTypeOnRecord {
}
/**
 * Some record.
 * @return Something that doesn't exist.
 * @throws Exception This doesn't exist either.
 */
record MyRecord(String name, int age) {}
// 2 violations above:
// 'Invalid '@return' tag for 'MyRecord'.'
// 'Invalid '@throws' tag for 'MyRecord'.'

/** Valid Javadoc on a record with no inappropriate tags. */
record ValidRecord(String value) {}
