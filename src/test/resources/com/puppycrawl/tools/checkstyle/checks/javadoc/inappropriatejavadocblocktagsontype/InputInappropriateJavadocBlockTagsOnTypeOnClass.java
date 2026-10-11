/*
InappropriateJavadocBlockTagsOnType
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsontype;

/**
 * Some class.
 * @return Something that doesn't exist.
 * @throws Exception This doesn't exist either.
 */
public class InputInappropriateJavadocBlockTagsOnTypeOnClass {}
// 2 violations above:
// 'Invalid '@return' tag for 'InputInappropriateJavadocBlockTagsOnTypeOnClass''
// 'Invalid '@throws' tag for 'InputInappropriateJavadocBlockTagsOnTypeOnClass''

/** Valid Javadoc with no inappropriate tags. */
class NoViolationClass {}

/**
 * Valid class javadoc.
 *
 * @param <T> type parameter is valid on a class
 * @see Object
 * @since 1.0
 * @deprecated use something else
 * @author Somebody
 */
class NoViolationGenericClass<T> {}

/**
 * Some interface.
 * @return Something that doesn't exist.
 * @throws RuntimeException This doesn't exist.
 */
interface MyInterface {}
// 2 violations above:
// 'Invalid '@return' tag for 'MyInterface'.'
// 'Invalid '@throws' tag for 'MyInterface'.'

/**
 * Valid interface javadoc.
 *
 * @param <T> type parameter is valid on a interface.
 */
interface MyValidInterface<T> {
}

/**
 * Some enum.
 * @param <T> type parameter not valid on a enum.
 */
enum MyEnum {
    // violation above 'Invalid '@param' tag for 'MyEnum'.'
    A, B, C
}

/** Valid Javadoc Comment on enum */
enum MyValidEnum {
    A, B, C
}

/**
 * Some annotation.
 * @param <T> type parameter is not valid on an annotation.
 * @throws UnsupportedOperationException This doesn't exist.
 */
@interface MyAnnotation {}
// 2 violations above:
// 'Invalid '@param' tag for 'MyAnnotation'.'
// 'Invalid '@throws' tag for 'MyAnnotation'.'


@interface MyValidAnnotation {
    String value();
}

/**
 * Some record.
 * @exception Exception This doesn't exist either.
 * @param <T> type parameter is not valid on a record.
 */
record MyRecord(String name, int age) {}
// violation above 'Invalid '@exception' tag for 'MyRecord'.'

/** Valid Javadoc on a record with no inappropriate tags. */
record ValidRecord(String value) {}
