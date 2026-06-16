/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public, protected
allowMissingParamTags = true
allowMissingReturnTag = true
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.util.HashMap;
import java.util.Map;

public class InputJavadocMethodMalformedJavadoc {

    private final Map<String, String> cache = new HashMap<>();

    // violation 3 lines below 'Javadoc comment at column 13 has parse error'
    /**
     * Don't remove.
     * @see {@link org.infinispan.cache.impl.CacheSupport#set(Object, Object)}
     */
    protected void set(String key, String value) {
        cache.put(key, value);
    }

    /**
     * Don't remove.
     * @see org.infinispan.cache.impl.CacheSupport#set(Object, Object)
     */
    protected void setCorrect(String key, String value) {
        cache.put(key, value);
    }

    // violation 7 lines below 'Javadoc comment at column 35 has parse error'
    /**
     * This is intentionally a non-public method meant as an integration point for bytecode
     * manipulation. Don't remove or alter the signature even if it might look like
     * unreachable code. Implementors should perform a put operation but optimizing it
     * as return values are not required.
     *
     * @author Sanne Grinovero <abc@infinispan.org> (C) 2011 Red Hat Inc.
     * @since 5.0
     */
    protected void set2(String key, String value) {
        cache.put(key, value);
    }

    /**
     * This is intentionally a non-public method meant as an integration point for bytecode
     * manipulation. Don't remove or alter the signature even if it might look like
     * unreachable code. Implementors should perform a put operation but optimizing it
     * as return values are not required.
     *
     * @author Sanne Grinovero {@literal <abc@infinispan.org>} (C) 2011 Red Hat Inc.
     * @since 5.0
     */
    protected void set2Correct(String key, String value) {
        cache.put(key, value);
    }
}
