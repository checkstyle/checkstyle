/*
JavadocMethod
allowedAnnotations = MyAnnotation, Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Common superclass for {@link MultisetSetCountUnconditionallyTester} and
 * {@link MultisetSetCountConditionallyTester}. It is used by those testers to
 * test calls to the unconditional {@code setCount()} method and calls to the
 * conditional {@code setCount()} method when the expected present count is
 * correct.
 *
 * @author Chris Povirk
 */
@SuppressWarnings(value = "unchecked")
public abstract class InputJavadocMethodExtendAnnotation<E>
  {

  private static final String SUPPORTS_ADD = "";
  private static final String SUPPORTS_REMOVE = null;

@CollectionFeature.Require
  public void testSetCount_zeroToZero_unsupported() {
    try {
      assertZeroToZero();
    } catch (UnsupportedOperationException tolerated) {
    }
  }

  private void assertZeroToZero() {

  }

  @interface CollectionFeature {
      @interface Require {
      }
  }
}
