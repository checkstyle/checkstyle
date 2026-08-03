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
 * Some enum.
 * @return Something that doesn't exist.
 * @exception RuntimeException This doesn't exist.
 */
enum MyEnum {
    // 2 violations above:
    // 'Invalid '@exception' tag for 'MyEnum'.'
    // 'Invalid '@return' tag for 'MyEnum'.'
    A, B, C
}

/**
 * Some annotation.
 * @return Something that doesn't exist.
 * @throws UnsupportedOperationException This doesn't exist.
 */
@interface MyAnnotation {}
// 2 violations above:
// 'Invalid '@return' tag for 'MyAnnotation'.'
// 'Invalid '@throws' tag for 'MyAnnotation'.'

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
