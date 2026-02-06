/*
JavadocMethod
validateThrows = true
allowedAnnotations = (default)Override
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.util.Collection;

public interface InputJavadocMethodInInterface {
        /**
     * Returns a single principal assignable from the specified type, or <tt>null</tt>
     * if there are none of the specified type.
     *
     * <p>Note that this would return <code>null</code> List always if the corresponding
     * subject has not logged in.</p>
     *
     * @param type the type of the principal that should be returned.
     * @return a principal of the specified type or <tt>null</tt> if there isn't one of
     * the specified type.
     */
     // violation below 'Expected @param tag for '<T>''
    <T> T oneByType(Class<T> type);

    /**
     * Returns all principals assignable from the specified type, or an empty Collection
     * if no principals of that type are contained.
     *
     * <p>Note that this would return an empty Collection always if the corresponding
     * subject has not logged in.</p>
     *
     * @param type the type of the principals that should be returned.
     * @return a Collection of principals that are assignable from the specified type, or
     *         an empty Collection if no principals of this type are associated.
     */
    // violation below 'Expected @param tag for '<T>''
    <T> Collection<T> byType(Class<T> type);
}
